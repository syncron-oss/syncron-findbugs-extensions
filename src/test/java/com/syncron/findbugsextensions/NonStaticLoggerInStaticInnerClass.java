package com.syncron.findbugsextensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonStaticLoggerInStaticInnerClass {

	static class InnerClassWithNonStaticLogger {

		public final Logger log = LoggerFactory.getLogger(InnerClassWithNonStaticLogger.class);
	}
}
