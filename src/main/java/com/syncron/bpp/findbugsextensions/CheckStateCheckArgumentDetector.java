package com.syncron.bpp.findbugsextensions;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

public class CheckStateCheckArgumentDetector extends OpcodeStackDetector {

	private static final String BUG_NAME = "SYNC_CHECK_STATE_CHECK_ARGUMENT_NO_MESSAGE";

	private static final String PRECONDITIONS = "com/google/common/base/Preconditions";

	private static final String CHECK_STATE = "checkState";

	private static final String CHECK_ARGUMENT = "checkArgument";

	private static final String SINGNATURE = "(Z)V";

	private BugReporter bugReporter;

	public CheckStateCheckArgumentDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void sawOpcode(int seen) {
		if (seen == INVOKESTATIC && isPreconditions() && isSingleArgCheckStateOrCheckArgument())
			bugReporter.reportBug(createBug());
	}

	private boolean isPreconditions() {
		return getClassConstantOperand().equals(PRECONDITIONS);
	}

	private boolean isSingleArgCheckStateOrCheckArgument() {
		return isCheck(CHECK_STATE) || isCheck(CHECK_ARGUMENT);
	}

	private boolean isCheck(String functionName) {
		return getNameConstantOperand().equals(functionName) && getSigConstantOperand().equals(SINGNATURE);
	}

	private BugInstance createBug() {
		return new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this);
	}

}
