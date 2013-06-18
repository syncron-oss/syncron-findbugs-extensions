package com.syncron.findbugsextensions;

import com.syncron.annotation.Final;

public class MethodAnnotatedFinal {

	@Final
	public Object finalMethod() {
		System.out.println("I'm final");
		return this;
	}
}
