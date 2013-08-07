package com.syncron.findbugsextensions;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import com.syncron.annotation.PackagePrivate;

import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.AnnotationDetector;
import edu.umd.cs.findbugs.classfile.Global;

public class PackagePrivateGatherer extends AnnotationDetector {

	private static final String packagePrivateAnnotationInByteCode = "L"
			+ PackagePrivate.class.getCanonicalName().replaceAll("\\.", "/") + ";";

	private PackagePrivateCache cache;

	public PackagePrivateGatherer(BugReporter bugReporter) {

	}

	@Override
	public void visit(Method method) {
		if (hasPackagePrivateAnnotation(method.getAnnotationEntries()))
			addToCache(getClassName(), getMethodName(), method.getSignature());
	}

	@Override
	public void visit(JavaClass obj) {
		if (hasPackagePrivateAnnotation(obj.getAnnotationEntries()))
			addToCache(getClassName());
	}

	private void addToCache(String className) {
		lazilyInitializeCache();
		cache.add(className);
	}

	private void addToCache(String className, String methodName, String signature) {
		lazilyInitializeCache();
		cache.add(className, methodName, signature);
	}

	private void lazilyInitializeCache() {
		if (cache != null)
			return;

		cache = new PackagePrivateCache();
		Global.getAnalysisCache().eagerlyPutDatabase(PackagePrivateCache.class, cache);
	}

	private boolean hasPackagePrivateAnnotation(AnnotationEntry[] annotationEntries) {
		if (annotationEntries == null)
			return false;

		for (AnnotationEntry annotationEntry : annotationEntries) {
			if (annotationEntry.getAnnotationType().equals(packagePrivateAnnotationInByteCode)) {
				return true;
			}
		}

		return false;
	}
}
