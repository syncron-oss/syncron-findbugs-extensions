package com.syncron.findbugsextensions.otherpackage;

import com.syncron.findbugsextensions.PackagePrivateAnnotatedMethodAndConstructor;

public class PackagePrivateForbiddenUser {

	public void shouldNotUsePackagePrivate() {
		new PackagePrivateAnnotatedMethodAndConstructor("nondefault constructor").packagePrivate();
	}

	public void shouldNotUsePackagePrivateConstructor() {
		new PackagePrivateAnnotatedMethodAndConstructor().hashCode();
	}
}
