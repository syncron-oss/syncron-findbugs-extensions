package com.syncron.bpp.findbugsextensions;

public class ThrowableToStringBug extends RuntimeException {
	private static final long serialVersionUID = -3278861422593013642L;

	@Override
	public String toString() {
		return "This is a bug";
	}
}
