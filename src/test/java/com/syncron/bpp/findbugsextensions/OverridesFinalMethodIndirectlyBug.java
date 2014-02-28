package com.syncron.bpp.findbugsextensions;

public class OverridesFinalMethodIndirectlyBug extends DoesNotOverrideFinalMethod {

	@Override
	public Object finalMethod() {
		return super.finalMethod();
	}
}
