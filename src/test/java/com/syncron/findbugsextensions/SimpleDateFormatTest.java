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

public class SimpleDateFormatTest extends BaseDetectorTestCase {

	@Test
	public void shouldDetectStaticSimpleDateFormat() {
		// given
		Class<?> classWithProblem = SimpleDateFormatStaticMember.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_STATIC_SIMPLE_DATE_FORMAT_INSTANCE bug in "
				+ classWithProblem);
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

	private List<BugInstance> runDetector(Class<?> testedClass) {
		BugPattern bugPattern = new BugPattern("SYNC_STATIC_SIMPLE_DATE_FORMAT_INSTANCE", "SSSDFI", "CORRECTNESS",
				true, "", "", "");
		Detector staticDateCalendarDetector = new StaticDateCalendarDetector(getBugReporter());
		Collection<BugInstance> bugs = runDetector(staticDateCalendarDetector, testedClass, bugPattern);
		return new ArrayList<BugInstance>(bugs);
	}
}
