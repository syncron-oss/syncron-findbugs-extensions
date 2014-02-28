package com.syncron.bpp.findbugsextensions;

import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.ba.ch.Subtypes2;

public class ThrowableToStringDetector extends BytecodeScanningDetector {

	private static final String BUG_NAME = "SYNC_THROWABLE_TOSTRING";

	private BugReporter bugReporter;

	public ThrowableToStringDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void visit(Method obj) {
		if (isThrowable() && isToStringMethod(obj))
			bugReporter.reportBug(createThrowableToStringBug());
	}

	private boolean isToStringMethod(Method method) {
		return method.getName().equals("toString");
	}

	private boolean isThrowable() {
		return Subtypes2.instanceOf(getClassName().replaceAll("/", "\\."), "java.lang.Throwable");
	}

	private BugInstance createThrowableToStringBug() {
		return new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this);
	}
}
