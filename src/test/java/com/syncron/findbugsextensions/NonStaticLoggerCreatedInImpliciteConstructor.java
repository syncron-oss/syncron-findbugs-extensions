package com.syncron.findbugsextensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonStaticLoggerCreatedInImpliciteConstructor {

	private Logger log = LoggerFactory.getLogger(getClass());

	public void logSomething(String msg) {
		log.info(msg);
	}
}
