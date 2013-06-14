package com.syncron.findbugsextensions;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.annotations.Test;

import com.syncron.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugPattern;
import edu.umd.cs.findbugs.Detector;

public class PrintStackTraceDetectorTest extends BaseDetectorTestCase {

	@Test
	public void shouldNotFindPrintStacktraceInCorrectClass() {
		// given
		Class<?> okClass = PrintStackTraceDetectorTest.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		assertEquals(bugs.size(), 0, "There should be no SYNC_PRINT_STACKTRACE bugs in " + okClass);
	}

	@Test
	public void shouldFindPrintStacktrace() {
		// given
		Class<?> classWithProblem = PrintStackTraceBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be 1 SYNC_PRINT_STACKTRACE bug in " + classWithProblem);
	}

	private List<BugInstance> runDetector(Class<?> testedClass) {
		BugPattern bugPattern = new BugPattern("SYNC_PRINT_STACKTRACE", "PST", "CORRECTNESS", true, "", "", "");
		Detector printStackTraceDetector = new PrintStackTraceDetector(getBugReporter());
		Collection<BugInstance> bugs = runDetector(printStackTraceDetector, testedClass, bugPattern);
		return new ArrayList<BugInstance>(bugs);
	}
}
