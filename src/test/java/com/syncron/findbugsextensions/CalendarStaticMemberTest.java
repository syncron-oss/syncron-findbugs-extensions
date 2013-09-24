package com.syncron.findbugsextensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugPattern;
import edu.umd.cs.findbugs.Detector;

public class CalendarStaticMemberTest extends BaseDetectorTestCase {

	@Test
	public void shouldDetectCalendarStaticField() {
		// given
		Class<?> classWithProblem = CalendarStaticMember.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_STATIC_CALENDAR_INSTANCE bug in "
				+ classWithProblem);
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

	private List<BugInstance> runDetector(Class<?> testedClass) {
		BugPattern bugPattern =
				new BugPattern("SYNC_STATIC_CALENDAR_INSTANCE", "SSCI", "CORRECTNESS", true, "", "", "");
		Detector staticDateCalendarDetector = new StaticDateCalendarDetector(getBugReporter());
		Collection<BugInstance> bugs = runDetector(staticDateCalendarDetector, testedClass, bugPattern);
		return new ArrayList<BugInstance>(bugs);
	}
}
