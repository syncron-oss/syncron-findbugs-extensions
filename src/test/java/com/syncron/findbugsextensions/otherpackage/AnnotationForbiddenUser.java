package com.syncron.findbugsextensions.otherpackage;

import com.syncron.findbugsextensions.AnnotationPackagePrivate;

public class AnnotationForbiddenUser {

	@AnnotationPackagePrivate
	public void method() {

	}
}
