package com.syncron.findbugsextensions;

public class PackagePrivateUser {

	public void usePackagePrivate() {
		new PackagePrivateAnnotatedMethodAndConstructor().packagePrivate();
	}
}
