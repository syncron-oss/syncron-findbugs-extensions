package com.syncron.bpp.findbugsextensions;

import org.apache.bcel.Repository;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;

public class PrintStackTraceDetector extends BytecodeScanningDetector {

	private static final String BUG_NAME = "SYNC_PRINT_STACKTRACE";

	private BugReporter bugReporter;

	public PrintStackTraceDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void sawOpcode(int seen) {
		if (seen == INVOKEVIRTUAL && isThrowableInstance() && isPrintStacktrace()) {
			bugReporter.reportBug(createPrintStacktraceBug());
		}
	}

	private BugInstance createPrintStacktraceBug() {
		return new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this);
	}

	private boolean isPrintStacktrace() {
		return getNameConstantOperand().equals("printStackTrace") && getSigConstantOperand().equals("()V");
	}

	private boolean isThrowableInstance() {
		try {
			return Repository.instanceOf(getClassConstantOperand(), "java.lang.Throwable");
		} catch (ClassNotFoundException e) {
			bugReporter.reportMissingClass(e);
			return false;
		}
	}
}
