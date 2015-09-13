package com.syncron.bpp.findbugsextensions;

import java.util.LinkedList;
import java.util.List;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import com.syncron.bpp.annotation.Final;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.AnnotationDetector;

/**
 * This detector ensures that classes and methods annotated with {@code @Final} are neither extended nor overridden.
 * 
 * @author piogla piotr.glazar@syncron.com
 * @since 24.06.2013
 */
public class FinalOverrideDetector extends AnnotationDetector {

	private static final String FINAL_ANNOTATION_BYTECODE_NAME = "L" + Final.class.getName().replace('.', '/') + ";";

	static final String BUG_NAME = "SYNC_FINAL_OVERRIDDEN";

	private BugReporter bugReporter;

	public FinalOverrideDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void visit(JavaClass javaClass) {
		super.visit(javaClass);
		try {
			if (hasFinalAssertionInSuperclasses(javaClass.getSuperClasses()))
				registerFinalClassExtensionBug(javaClass);
		} catch (ClassNotFoundException e) {
			bugReporter.reportMissingClass(e);
		}
	}

	private boolean hasFinalAssertionInSuperclasses(JavaClass[] superClasses) {
		for (JavaClass superClass : superClasses)
			if (hasFinalAnnotation(superClass.getAnnotationEntries()))
				return true;

		return false;
	}

	private void registerFinalClassExtensionBug(JavaClass javaClass) {
		bugReporter.reportBug(new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClass(javaClass));
	}

	private boolean hasFinalAnnotation(AnnotationEntry[] annotationEntries) {
		if (annotationEntries == null)
			return false;

		for (AnnotationEntry annotation : annotationEntries)
			if (annotation.getAnnotationType().equals(FINAL_ANNOTATION_BYTECODE_NAME))
				return true;

		return false;
	}

	@Override
	public void visitMethod(Method obj) {
		super.visitMethod(obj);

		try {
			for (Method superMethod : findSuperImplementations(obj))
				if (hasFinalAnnotation(superMethod.getAnnotationEntries()))
					registerFinalMethodExtensionBug(obj);

		} catch (ClassNotFoundException e) {
			bugReporter.reportMissingClass(e);
		}
	}

	private void registerFinalMethodExtensionBug(Method obj) {
		bugReporter.reportBug(new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClassAndMethod(this));
	}

	private List<Method> findSuperImplementations(Method obj) throws ClassNotFoundException {
		List<Method> methods = new LinkedList<Method>();
		for (JavaClass superClass : getClassContext().getJavaClass().getSuperClasses())
			addImplementationIfNotNull(findImplementations(obj, superClass), methods);

		return methods;
	}

	private void addImplementationIfNotNull(Method findImplementations,
			List<Method> methods) {
		if (findImplementations != null)
			methods.add(findImplementations);
	}

	private Method findImplementations(Method obj, JavaClass superClass) {
		for (Method method : superClass.getMethods())
			if (method.getName().equals(obj.getName()) && method.getSignature().equals(obj.getSignature()))
				return method;

		return null;
	}
}
