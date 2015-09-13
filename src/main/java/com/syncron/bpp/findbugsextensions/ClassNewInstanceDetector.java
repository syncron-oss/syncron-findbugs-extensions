package com.syncron.bpp.findbugsextensions;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;

/**
 * @author piofin <piotr.findeisen@syncron.com>
 * @since Sep 13, 2015
 */
public class ClassNewInstanceDetector extends BytecodeScanningDetector {

	static final String BUG_NAME = "SYNC_CLASS_NEW_INSTANCE";

	private BugReporter bugReporter;

	public ClassNewInstanceDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void sawOpcode(int seen) {
		if (seen == INVOKEVIRTUAL && isClassNewInstanceCall()) {
			// report
			BugInstance bug = new BugInstance(this, BUG_NAME, NORMAL_PRIORITY)
					.addClassAndMethod(this).addSourceLine(this);
			bugReporter.reportBug(bug);
		}
	}

	private boolean isClassNewInstanceCall() {
		return Class.class.getName().equals(getDottedClassConstantOperand())
				&& "newInstance".equals(getNameConstantOperand())
				&& getSigConstantOperand() != null && getSigConstantOperand().startsWith("()");
	}
}
