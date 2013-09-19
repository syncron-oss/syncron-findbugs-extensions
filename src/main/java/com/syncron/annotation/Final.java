package com.syncron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a method or a class is annotated with {@code @Final}, treat it as if the {@code final} keyword has been used:
 * <ul>
 * <li>for methods: do not override the annotated method
 * <li>for classes: do not extend the annotated class
 * </ul>
 * <p>
 * 
 * @author piofin <piotr.findeisen@syncron.com>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Final {

}
