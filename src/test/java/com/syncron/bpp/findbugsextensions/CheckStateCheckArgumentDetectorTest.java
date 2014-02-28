package com.syncron.bpp.findbugsextensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.CheckStateCheckArgumentDetector;
import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugPattern;
import edu.umd.cs.findbugs.Detector;

public class CheckStateCheckArgumentDetectorTest extends BaseDetectorTestCase {

	@Test
	public void shouldDetectCheckMethodsWithSingleArg() {
		// given
		Class<?> classWithProblem = CheckStateAndCheckArgumentWithSingleArg.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 2, "There should be 2 SYNC_CHECK_STATE_CHECK_ARGUMENT_NO_MESSAGE bugs in "
				+ classWithProblem);
	}

	@Test
	public void shouldNotDetectAnyFaults() {
		// given
		Class<?> okClass = CheckStateAndCheckArgumentWithNoSingleArg.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertEquals(bugs.size(), 0, "There should be no SYNC_CHECK_STATE_CHECK_ARGUMENT_NO_MESSAGE bugs in "
				+ okClass);
	}

	@Test
	public void shouldNotDetectAnyFaultsInCustomPreconditions() {
		// given
		Class<?> okClass = UnrelatedCheckStateCheckArgument.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertEquals(bugs.size(), 0, "There should be no SYNC_CHECK_STATE_CHECK_ARGUMENT_NO_MESSAGE bugs in "
				+ okClass);
	}

	private List<BugInstance> runDetector(Class<?> testedClass) {
		BugPattern bugPattern = new BugPattern("SYNC_CHECK_STATE_CHECK_ARGUMENT_NO_MESSAGE", "SCSCANM", "CORRECTNESS",
				true, "", "", "");
		Detector finalAssertionDetector = new CheckStateCheckArgumentDetector(getBugReporter());
		Collection<BugInstance> bugs = runDetector(finalAssertionDetector, testedClass, bugPattern);
		return new ArrayList<BugInstance>(bugs);
	}
}
