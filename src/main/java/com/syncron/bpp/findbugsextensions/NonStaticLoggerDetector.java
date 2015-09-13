package com.syncron.bpp.findbugsextensions;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.generic.Type;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.OpcodeStack;
import edu.umd.cs.findbugs.OpcodeStack.Item;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.ba.XClass;

public class NonStaticLoggerDetector extends BytecodeScanningDetector {

	private static final String LOGGER_CLASS_NAME = "org/slf4j/Logger";

	private static final String JVM_LOGGER_CLASS_NAME = "L" + LOGGER_CLASS_NAME + ";";

	static final String BUG_NAME = "SYNC_NON_STATIC_LOGGER";

	private BugReporter bugReporter;

	private OpcodeStack stack;

	public NonStaticLoggerDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void visitClassContext(ClassContext classContext) {
		try {
			stack = new OpcodeStack();
			super.visitClassContext(classContext);
		} finally {
			stack = null;
		}
	}

	@Override
	public void visitCode(Code obj) {
		stack.resetForMethodEntry(this);
		super.visitCode(obj);
	}

	@Override
	public void sawOpcode(int seen) {
		try {
			stack.mergeJumps(this);
			if (seen == PUTFIELD && affectsLoggerField() && wrongAssignment() && !inInnerNonStaticClass())
				bugReporter.reportBug(new BugInstance(this, BUG_NAME, NORMAL_PRIORITY).addClassAndMethod(this)
						.addSourceLine(this));
		} finally {
			stack.sawOpcode(this, seen);
		}
	}

	private boolean inInnerNonStaticClass() {
		XClass clazz = getClassContext().getXClass();
		return (clazz.getImmediateEnclosingClass() != null || isAnonymusInnerClass(clazz))
				&& !clazz.isStatic();
	}

	private boolean isAnonymusInnerClass(XClass clazz) {
		String className = clazz.getClassDescriptor().getClassName();
		return className.contains("$");
	}

	private boolean wrongAssignment() {
		String methodName = getMethod().getName();
		if (!methodName.equals("<init>"))
			return true;
		return !isAloadOnStack();
	}

	/**
	 * This method ensures that logger fields are assigned only from construction parameters. That is why it looks for
	 * ALOAD instructions that are located before PUTFIELD instruction.
	 * 
	 * @return true if and only if there is one of ALOAD instructions before PUTFIELD instruction
	 */
	private boolean isAloadOnStack() {
		int numberOfArgs = Type.getArgumentTypes(getMethodSig()).length;
		for (int i = 0; i < stack.getStackDepth(); ++i) {
			Item item = stack.getStackItem(i);
			String signature = item.getSignature();
			int registerNumber = item.getRegisterNumber();
			if (signature.equals(JVM_LOGGER_CLASS_NAME) && registerNumber >= 0 && registerNumber <= numberOfArgs)
				return true;
		}
		return false;
	}

	private boolean affectsLoggerField() {
		return getSigConstantOperand().equals(JVM_LOGGER_CLASS_NAME);
	}

}
