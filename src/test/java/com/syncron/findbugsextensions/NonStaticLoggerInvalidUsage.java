package com.syncron.findbugsextensions;

import org.slf4j.Logger;

public class NonStaticLoggerInvalidUsage {

	private Logger firstLog;

	private Logger secondLog;

	public NonStaticLoggerInvalidUsage(Logger forFirst) {
		firstLog = forFirst;
	}

	public void setSecondLog(Logger forSecond) {
		secondLog = forSecond;
	}

	public void logSomething(String msg) {
		if (firstLog != null)
			firstLog.info(msg);
		if (secondLog != null)
			secondLog.info(msg);
	}
}
