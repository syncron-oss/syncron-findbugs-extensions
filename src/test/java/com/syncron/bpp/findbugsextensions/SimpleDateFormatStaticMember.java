package com.syncron.bpp.findbugsextensions;

import java.text.SimpleDateFormat;

public class SimpleDateFormatStaticMember {

	@SuppressWarnings("unused")
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat();
}
