package com.syncron.findbugsextensions;

import com.syncron.annotation.PackagePrivate;

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
