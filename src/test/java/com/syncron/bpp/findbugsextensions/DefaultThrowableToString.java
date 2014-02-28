package com.syncron.bpp.findbugsextensions;

public class DefaultThrowableToString extends RuntimeException {
	private static final long serialVersionUID = -566192084666763690L;

	@Override
	public String getMessage() {
		return "I'm just overriding getMessage()";
	}
}
