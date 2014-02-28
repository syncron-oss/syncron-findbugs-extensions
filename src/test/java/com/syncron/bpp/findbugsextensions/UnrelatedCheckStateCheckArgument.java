package com.syncron.bpp.findbugsextensions;

public class UnrelatedCheckStateCheckArgument {

	private static class Preconditions {
		public static void checkState(boolean b) {

		}

		public static void checkArgument(boolean b) {

		}
	}

	public void check() {
		Preconditions.checkArgument(true);
		Preconditions.checkState(true);
	}
}
