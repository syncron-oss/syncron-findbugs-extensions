package com.syncron.findbugsextensions;

import com.google.common.base.Preconditions;

public class CheckStateAndCheckArgumentWithNoSingleArg {

	public void checkStateWithManyArgs(String arg) {
		Preconditions.checkState(arg != null, "arg must not be null");
	}

	public void checkArgumentWithManyArgs(String arg) {
		Preconditions.checkArgument(arg != null, "arg must not be null");
	}
}
