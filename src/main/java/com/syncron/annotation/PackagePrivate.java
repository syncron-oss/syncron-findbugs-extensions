package com.syncron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a method or a class is annotated with {@code PackagePrivate}, treat it as if no {@code public} or
 * {@code protected} keyword was applied.
 * 
 * @author piofin <piotr.findeisen@syncron.com>
 * @since Feb 14, 2012
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE })
public @interface PackagePrivate {
	String publicBecause();
}
