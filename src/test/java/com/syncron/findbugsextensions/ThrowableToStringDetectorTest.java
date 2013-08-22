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

public class ThrowableToStringDetectorTest extends BaseDetectorTestCase {

	@Test
	public void shouldDetectThrowableToStringBug() {
		// given
		Class<?> classWithProblem = ThrowableToStringBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There is 1 SYNC_THROWABLE_TOSTRING bug in " + classWithProblem);
	}

	@Test
	public void shouldNotDetectThrowableToStringBug() {
		// given
		Class<?> okClass = DefaultThrowableToString.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertEquals(bugs.size(), 0, "There should be no SYNC_THROWABLE_TOSTRING bugs in " + okClass);
	}

	private List<BugInstance> runDetector(Class<?> testedClass) {
		BugPattern bugPattern =
				new BugPattern("SYNC_THROWABLE_TOSTRING", "STTS", "CORRECTNESS", true, "", "", "");
		Detector throwableToStringDetector = new ThrowableToStringDetector(getBugReporter());
		Collection<BugInstance> bugs = runDetector(throwableToStringDetector, testedClass, bugPattern);
		return new ArrayList<BugInstance>(bugs);
	}
}
