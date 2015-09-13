package com.syncron.bpp.findbugsextensions;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class CheckStateCheckArgumentDetectorTest extends BaseDetectorTestCase<CheckStateCheckArgumentDetector> {

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
}
