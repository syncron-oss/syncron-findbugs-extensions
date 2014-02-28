package com.syncron.bpp.findbugsextensions.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.DetectorToDetector2Adapter;
import edu.umd.cs.findbugs.NoOpFindBugsProgress;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.ba.AnalysisCacheToAnalysisContextAdapter;
import edu.umd.cs.findbugs.ba.AnalysisContext;
import edu.umd.cs.findbugs.ba.FieldSummary;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.DescriptorFactory;
import edu.umd.cs.findbugs.classfile.Global;
import edu.umd.cs.findbugs.classfile.IAnalysisCache;
import edu.umd.cs.findbugs.classfile.IClassFactory;
import edu.umd.cs.findbugs.classfile.IClassPathBuilder;
import edu.umd.cs.findbugs.classfile.IClassPathBuilderProgress;
import edu.umd.cs.findbugs.classfile.ICodeBase;
import edu.umd.cs.findbugs.classfile.ICodeBaseLocator;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import edu.umd.cs.findbugs.classfile.engine.bcel.ClassContextClassAnalysisEngine;
import edu.umd.cs.findbugs.classfile.impl.ClassFactory;
import edu.umd.cs.findbugs.classfile.impl.ClassPathImpl;
import edu.umd.cs.findbugs.classfile.impl.DirectoryCodeBase;
import edu.umd.cs.findbugs.classfile.impl.FilesystemCodeBaseLocator;

public class DetectorRunner {

	private static final String CODEBASE_DIRECTORY = ".";

	public static final PrintStreamBugReporter STATIC_BUG_REPORTER = new PrintStreamBugReporter(System.out);

	private static final DetectorRunner detectorRunner = new DetectorRunner();

	private final AuxCodeBaseLocatorProvider auxCodeBaseLocatorProvider = new AuxCodeBaseLocatorProvider();

	private DetectorRunner() {
		try {
			setUpStaticDependenciesWithinFindBugs(STATIC_BUG_REPORTER);
		} catch (Exception e) {
			throw new RuntimeException("Failed to setup FindBugs dependencies for testing.", e);
		}
	}

	public static DetectorRunner instance() {
		return detectorRunner;
	}

	private void setUpStaticDependenciesWithinFindBugs(PrintStreamBugReporter bugReporter)
			throws CheckedAnalysisException, IOException, InterruptedException {
		bugReporter.setIngnoreMissingClasses(true);
		bugReporter.setPriorityThreshold(Priorities.LOW_PRIORITY);
		ClassPathImpl classPath = new ClassPathImpl();

		IAnalysisCache analysisCache = ClassFactory.instance().createAnalysisCache(classPath, bugReporter);
		new ClassContextClassAnalysisEngine().registerWith(analysisCache);
		new edu.umd.cs.findbugs.classfile.engine.asm.EngineRegistrar().registerAnalysisEngines(analysisCache);
		new edu.umd.cs.findbugs.classfile.engine.bcel.EngineRegistrar().registerAnalysisEngines(analysisCache);
		new edu.umd.cs.findbugs.classfile.engine.EngineRegistrar().registerAnalysisEngines(analysisCache);

		Global.setAnalysisCacheForCurrentThread(analysisCache);

		ICodeBaseLocator codeBaseLocator = new FilesystemCodeBaseLocator(".");
		ICodeBase codeBase = new DirectoryCodeBase(codeBaseLocator, new File(CODEBASE_DIRECTORY));
		codeBase.setApplicationCodeBase(true);
		classPath.addCodeBase(codeBase);

		addAuxCodeBasesFromClassPath(classPath);

		IClassFactory classFactory = ClassFactory.instance();
		IClassPathBuilder builder = classFactory.createClassPathBuilder(bugReporter);
		builder.addCodeBase(codeBaseLocator, true);
		builder.scanNestedArchives(true);
		IClassPathBuilderProgress progress = new NoOpFindBugsProgress();
		builder.build(classPath, progress);
		List<ClassDescriptor> appClassList = builder.getAppClassList();

		AnalysisCacheToAnalysisContextAdapter analysisContext = new AnalysisCacheToAnalysisContextAdapter();
		AnalysisContext.setCurrentAnalysisContext(analysisContext);
		analysisContext.setAppClassList(appClassList);
		analysisContext.setFieldSummary(new FieldSummary());
		bugReporter.setIngnoreMissingClasses(false);
	}

	private void addAuxCodeBasesFromClassPath(ClassPathImpl classPath) throws IOException, ResourceNotFoundException {
		Iterable<ICodeBaseLocator> codeBaseLocators = auxCodeBaseLocatorProvider.get(classPathEntries());
		for (ICodeBaseLocator auxCodeBaseLocator : codeBaseLocators) {
			ICodeBase auxCodeBase = auxCodeBaseLocator.openCodeBase();
			classPath.addCodeBase(auxCodeBase);
		}
	}

	private Iterable<String> classPathEntries() {
		return Arrays.asList(System.getProperty("java.class.path").split(File.pathSeparator));
	}

	private void doRunDetectorOnClass(Detector pluginDetector, Class<?> classToTest, BugReporter bugReporter)
			throws CheckedAnalysisException, IOException, InterruptedException {

		DetectorToDetector2Adapter adapter = new DetectorToDetector2Adapter(
				pluginDetector);

		String dottedClassName = classToTest.getName();
		ClassDescriptor classDescriptor = DescriptorFactory.createClassDescriptorFromDottedClassName(dottedClassName);
		adapter.visitClass(classDescriptor);
	}

	public static void runDetectorOnClass(Detector pluginToTest, Class<?> classWithBug, BugReporter bugReporter) {
		try {
			detectorRunner.doRunDetectorOnClass(pluginToTest, classWithBug, bugReporter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
