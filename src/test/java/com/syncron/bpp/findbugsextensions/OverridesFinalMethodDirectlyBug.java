package com.syncron.bpp.findbugsextensions;

public class OverridesFinalMethodDirectlyBug extends MethodAnnotatedFinal {

	@Override
	public Object finalMethod() {
		return super.finalMethod();
	}
}
