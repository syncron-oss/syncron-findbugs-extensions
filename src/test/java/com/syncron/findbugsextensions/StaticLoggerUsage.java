package com.syncron.findbugsextensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticLoggerUsage {

	private static final Logger log = LoggerFactory.getLogger(StaticLoggerUsage.class);

	public void logSomething(String msg) {
		log.info(msg);
	}
}
