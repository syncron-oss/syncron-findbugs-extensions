package com.syncron.bpp.findbugsextensions;

public class PackagePrivateUser {

	public void usePackagePrivate() {
		new PackagePrivateAnnotatedMethodAndConstructor().packagePrivate();
	}
}
