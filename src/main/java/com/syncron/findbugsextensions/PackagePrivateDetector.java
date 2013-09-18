package com.syncron.findbugsextensions;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.classfile.Global;

public class PackagePrivateDetector extends BytecodeScanningDetector {

	private static final String BUG_NAME = "PACKAGE_PRIVATE_USAGE";

	private BugReporter bugReporter;

	public PackagePrivateDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void sawOpcode(int seen) {
		if (seen != INVOKEVIRTUAL && seen != INVOKESPECIAL)
			return;

		if (isForbiddenPackagePrivateCall())
			bugReporter.reportBug(createPackagePrivateUsageInMethodBug());
	}

	@Override
	public void visit(Method obj) {
		if (isForbiddenPackagePrivateAnnotation(obj.getAnnotationEntries())) {
			bugReporter.reportBug(createPackagePrivateUsageInMethodBug());
		}
	}

	private boolean isForbiddenPackagePrivateAnnotation(AnnotationEntry[] annotationEntries) {
		PackagePrivateCache cache = Global.getAnalysisCache().getDatabase(PackagePrivateCache.class);

		for (AnnotationEntry annotation : annotationEntries) {
			String annotationClass = annotation.getAnnotationType();
			annotationClass = annotationClass.replaceAll("\\.", "/").substring(1, annotationClass.length() - 1);
			if (cache.isClassAnnotated(annotationClass))
				return !doClassesShareTheSamePackage(annotationClass, getClassName());
		}
		return false;
	}

	@Override
	public void visit(JavaClass javaClass) {
		reportPackagePrivateClassExtension(javaClass);
		reportPackatePrivateAnnotationUsage(javaClass);
	}

	private void reportPackatePrivateAnnotationUsage(JavaClass javaClass) {
		if (isForbiddenPackagePrivateAnnotation(javaClass.getAnnotationEntries()))
			bugReporter.reportBug(createPackagePrivateUsageInClassBug());
	}

	private void reportPackagePrivateClassExtension(JavaClass javaClass) {
		PackagePrivateCache cache = Global.getAnalysisCache().getDatabase(PackagePrivateCache.class);
		String className = javaClass.getClassName().replaceAll("\\.", "/");
		try {
			if (isForbiddenPackagePrivateExtension(cache, className, javaClass.getSuperClass()))
				bugReporter.reportBug(createPackagePrivateUsageInClassBug());
		} catch (ClassNotFoundException e) {
			bugReporter.reportMissingClass(e);
		}
	}

	private BugInstance createPackagePrivateUsageInClassBug() {
		return new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClass(this);
	}

	private boolean isForbiddenPackagePrivateExtension(PackagePrivateCache cache, String className, JavaClass superClass) {
		String superClassName = superClass.getClassName().replaceAll("\\.", "/");
		if (cache.isClassAnnotated(superClassName))
			return !doClassesShareTheSamePackage(superClassName, className);
		else
			return false;
	}

	private BugInstance createPackagePrivateUsageInMethodBug() {
		return new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this);
	}

	private boolean isForbiddenPackagePrivateCall() {
		String className = getClassConstantOperand();
		String methodName = getNameConstantOperand();
		String signature = getSigConstantOperand();
		PackagePrivateCache cache = Global.getAnalysisCache().getDatabase(PackagePrivateCache.class);

		if (cache.contains(className, methodName, signature))
			return !doClassesShareTheSamePackage(className, getClassName());
		else
			return false;
	}

	private boolean doClassesShareTheSamePackage(String className, String className2) {
		String firstPackage = className.substring(0, className.lastIndexOf('/'));
		String secondPackage = className2.substring(0, className2.lastIndexOf('/'));
		return firstPackage.equals(secondPackage);
	}
}
