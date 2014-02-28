package com.syncron.bpp.findbugsextensions;

import com.syncron.bpp.annotation.PackagePrivate;

public class PackagePrivateAnnotatedMethodAndConstructor {

	@PackagePrivate(publicBecause = "this is a test case")
	public void packagePrivate() {

	}

	@PackagePrivate(publicBecause = "this is a test case")
	public PackagePrivateAnnotatedMethodAndConstructor() {

	}

	public PackagePrivateAnnotatedMethodAndConstructor(String string) {

	}

}
