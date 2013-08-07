package com.syncron.findbugsextensions.utils;

import java.util.Collection;

import org.testng.annotations.BeforeClass;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugPattern;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.DetectorFactoryCollection;

public class BaseDetectorTestCase {

	@BeforeClass
	public void forceInitializationOfFindbugsMetaData() {
		DetectorRunner.instance();
	}

	protected BugReporter getBugReporter() {
		return DetectorRunner.STATIC_BUG_REPORTER;
	}

	protected Collection<BugInstance> runDetector(Detector detector, Class<?> testExample, BugPattern bugPattern) {
		DetectorFactoryCollection.instance().registerBugPattern(bugPattern);

		PrintStreamBugReporter bugReporter = DetectorRunner.STATIC_BUG_REPORTER;
		DetectorRunner.runDetectorOnClass(detector, testExample, bugReporter);
		Collection<BugInstance> bugCollection = bugReporter.getBugCollection().getCollection();

		bugReporter.reset();

		return bugCollection;
	}
}
