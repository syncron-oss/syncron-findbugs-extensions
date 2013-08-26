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

public class SetStackTraceDetectorTest extends BaseDetectorTestCase {

	@Test
	public void shouldDetectSetStackTraceBug() {
		// given
		Class<?> classWithProblem = SetStackTraceBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_SET_STACKTRACE bug in " + classWithProblem);
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

	private List<BugInstance> runDetector(Class<?> testedClass) {
		BugPattern bugPattern = new BugPattern("SYNC_SET_STACKTRACE", "SST", "CORRECTNESS", true, "", "", "");
		Detector printStackTraceDetector = new SetStackTraceDetector(getBugReporter());
		Collection<BugInstance> bugs = runDetector(printStackTraceDetector, testedClass, bugPattern);
		return new ArrayList<BugInstance>(bugs);
	}
}
