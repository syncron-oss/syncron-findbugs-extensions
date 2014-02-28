package com.syncron.bpp.findbugsextensions;

import com.syncron.bpp.annotation.Final;

public class MethodAnnotatedFinal {

	@Final
	public Object finalMethod() {
		System.out.println("I'm final");
		return this;
	}
}
