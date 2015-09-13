package com.syncron.bpp.findbugsextensions;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class SetStackTraceDetectorTest extends BaseDetectorTestCase<SetStackTraceDetector> {

	@Test
	public void shouldDetectSetStackTraceBug() {
		// given
		Class<?> classWithProblem = SetStackTraceBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(SetStackTraceDetector.BUG_NAME), "found bugs' types");
	}

	@Test
	public void shouldNotDetectSetStackTraceBug() {
		// given
		Class<?> okClass = PrintStackTraceBug.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertEquals(bugs.size(), 0, "There should no SYNC_SET_STACKTRACE bugs in " + okClass);
	}
}
