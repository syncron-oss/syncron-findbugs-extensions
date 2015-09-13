package com.syncron.bpp.findbugsextensions;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class CalendarStaticMemberTest extends BaseDetectorTestCase<StaticDateCalendarDetector> {
	
	@Test
	public void shouldDetectCalendarStaticField() {
		// given
		Class<?> classWithProblem = CalendarStaticMember.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(StaticDateCalendarDetector.CALENDAR_BUG_NAME), "found bugs' types");
	}

	@Test
	public void shouldAllowNonStaticCalendarField() {
		// given
		Class<?> okClass = CalendarAndSimpleDateFormatMember.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertTrue(bugs.isEmpty(), "There should be no SYNC_STATIC_CALENDAR_INSTANCE bugs in " + okClass);
	}

	/**
	 * BPP-9691
	 */
	@Test
	public void shouldNotFailWithStaticPrimitive() {
		// given
		Class<?> okClass = StaticPrimitiveField.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertTrue(bugs.isEmpty(), "There should be no SYNC_STATIC_CALENDAR_INSTANCE bugs in " + okClass);
	}
}
