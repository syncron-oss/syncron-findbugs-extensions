package com.syncron.findbugsextensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonStaticLoggerInNonStaticInnerClass {

	class InnerClassWithLogger {

		public final Logger log = LoggerFactory.getLogger(InnerClassWithLogger.class);
	}

}
