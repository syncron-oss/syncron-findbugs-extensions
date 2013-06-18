package com.syncron.findbugsextensions;

public class OverloadFinalMethod extends MethodAnnotatedFinal {

	public void finalMethod(Object o) {
		System.out.println(String.valueOf(o));
	}
}
