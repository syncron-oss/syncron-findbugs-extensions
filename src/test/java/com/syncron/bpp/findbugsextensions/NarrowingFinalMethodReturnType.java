package com.syncron.bpp.findbugsextensions;

public class NarrowingFinalMethodReturnType extends MethodAnnotatedFinal {

	@Override
	public String finalMethod() {
		return "foo";
	}
}
