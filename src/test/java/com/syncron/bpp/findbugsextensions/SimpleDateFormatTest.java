package com.syncron.bpp.findbugsextensions;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class SimpleDateFormatTest extends BaseDetectorTestCase<StaticDateCalendarDetector> {

	@Test
	public void shouldDetectStaticSimpleDateFormat() {
		// given
		Class<?> classWithProblem = SimpleDateFormatStaticMember.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(StaticDateCalendarDetector.SIMPLE_DATE_FORMAT_BUG_NAME),
				"found bugs' types");
	}

	@Test
	public void shouldAllowNonStaticSimpleDateFormatField() {
		// given
		Class<?> okClass = CalendarAndSimpleDateFormatMember.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertTrue(bugs.isEmpty(), "There should be no SYNC_STATIC_SIMPLE_DATE_FORMAT_INSTANCE bugs in "
				+ okClass);
	}
}
