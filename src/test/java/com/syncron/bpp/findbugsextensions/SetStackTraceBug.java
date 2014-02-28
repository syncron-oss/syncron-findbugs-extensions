package com.syncron.bpp.findbugsextensions;

public class SetStackTraceBug {

	public void bug() {
		new RuntimeException("bug").setStackTrace(new StackTraceElement[]{});
	}
}
