package com.syncron.bpp.findbugsextensions;

import com.google.common.base.Preconditions;

public class CheckStateAndCheckArgumentWithSingleArg {

	public void checkStateWithSingleArg(String arg) {
		Preconditions.checkState(arg != null);
	}

	public void checkArgumentWithSingleArg(String arg) {
		Preconditions.checkArgument(arg != null);
	}
}
