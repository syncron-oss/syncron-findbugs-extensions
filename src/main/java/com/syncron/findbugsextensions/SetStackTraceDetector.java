package com.syncron.findbugsextensions;

import org.apache.bcel.Repository;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;

public class SetStackTraceDetector extends BytecodeScanningDetector {

	private static final String BUG_NAME = "SYNC_SET_STACKTRACE";

	private BugReporter bugReporter;

	public SetStackTraceDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void sawOpcode(int seen) {
		if (seen == INVOKEVIRTUAL && isThrowableInstance() && isSetStackTrace())
			bugReporter.reportBug(createSetStackTraceBug());
	}

	private BugInstance createSetStackTraceBug() {
		return new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this);
	}

	private boolean isSetStackTrace() {
		return getNameConstantOperand().equals("setStackTrace")
				&& getSigConstantOperand().equals("([Ljava/lang/StackTraceElement;)V");
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
