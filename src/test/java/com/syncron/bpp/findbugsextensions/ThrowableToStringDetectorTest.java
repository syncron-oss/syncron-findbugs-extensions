package com.syncron.bpp.findbugsextensions;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class ThrowableToStringDetectorTest extends BaseDetectorTestCase<ThrowableToStringDetector> {

	@Test
	public void shouldDetectThrowableToStringBug() {
		// given
		Class<?> classWithProblem = ThrowableToStringBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(ThrowableToStringDetector.BUG_NAME), "found bugs' types");
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
}
