package com.syncron.findbugsextensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonStaticLoggerCreatedInExpliciteConstructor {

	private Logger log;

	public NonStaticLoggerCreatedInExpliciteConstructor() {
		log = LoggerFactory.getLogger(getClass());
	}

	public void logSomething(String msg) {
		log.info(msg);
	}
}
