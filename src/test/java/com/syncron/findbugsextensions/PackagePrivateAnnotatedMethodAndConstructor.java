package com.syncron.findbugsextensions;

import com.syncron.annotation.PackagePrivate;

public class PackagePrivateAnnotatedMethodAndConstructor {

	@PackagePrivate
	public void packagePrivate() {

	}

	@PackagePrivate
	public PackagePrivateAnnotatedMethodAndConstructor() {

	}

	public PackagePrivateAnnotatedMethodAndConstructor(String string) {

	}

}
