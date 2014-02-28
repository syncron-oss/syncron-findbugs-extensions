package com.syncron.bpp.findbugsextensions.otherpackage;

import com.syncron.bpp.findbugsextensions.PackagePrivateAnnotatedMethodAndConstructor;

public class PackagePrivateForbiddenUser {

	public void shouldNotUsePackagePrivate() {
		new PackagePrivateAnnotatedMethodAndConstructor("nondefault constructor").packagePrivate();
	}

	public void shouldNotUsePackagePrivateConstructor() {
		new PackagePrivateAnnotatedMethodAndConstructor().hashCode();
	}
}
