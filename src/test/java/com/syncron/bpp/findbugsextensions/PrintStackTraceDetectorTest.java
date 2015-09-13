package com.syncron.bpp.findbugsextensions;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class PrintStackTraceDetectorTest extends BaseDetectorTestCase<PrintStackTraceDetector> {

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
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(PrintStackTraceDetector.BUG_NAME), "found bugs' types");
	}
}
