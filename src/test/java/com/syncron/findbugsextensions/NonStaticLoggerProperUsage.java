package com.syncron.findbugsextensions;

import org.slf4j.Logger;

public class NonStaticLoggerProperUsage {

	private Logger log;

	public NonStaticLoggerProperUsage(Logger outerLogger) {
		log = outerLogger;
	}

	public void logSomething(String msg) {
		log.info(msg);
	}
}
