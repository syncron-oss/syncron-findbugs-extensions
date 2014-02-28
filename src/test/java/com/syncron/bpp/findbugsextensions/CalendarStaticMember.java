package com.syncron.bpp.findbugsextensions;

import java.util.Calendar;

public class CalendarStaticMember {

	@SuppressWarnings("unused")
	private final static Calendar calendar = Calendar.getInstance();
}
