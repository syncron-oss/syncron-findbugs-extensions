package com.syncron.findbugsextensions;

import java.util.Collection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.findbugsextensions.PrintStackTraceDetector;
import com.syncron.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugPattern;
import edu.umd.cs.findbugs.Detector;

public class PrintStackTraceDetectorTest extends BaseDetectorTestCase {

	@Test
	public void shouldFindPrintStacktrace() {
		// given
		BugPattern bugPattern = new BugPattern("SYNC_PRINT_STACKTRACE", "PST", "CORRECTNESS", true, "", "", "");
		Detector printStackTraceDetector = new PrintStackTraceDetector(getBugReporter());

		// when
		Collection<BugInstance> bugs = runDetector(printStackTraceDetector, PrintStackTraceBug.class, bugPattern);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_PRINT_STACKTRACE bug");
	}
}
