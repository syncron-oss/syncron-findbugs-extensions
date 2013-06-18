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

public class FinalOverrideDetectorTest extends BaseDetectorTestCase {

	@Test
	public void shouldFindFinalAnnotatedClassExtension() {
		// given
		Class<?> classWithProblem = ExtendsFinalAnnotatedClassBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_FINAL_OVERRIDDEN bug in " + classWithProblem);
	}

	@Test
	public void shouldNotFindFinalAnnotatedClassExtensionInCorrectClass() {
		// given
		Class<?> okClass = FinalOverrideDetectorTest.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertEquals(bugs.size(), 0, "There should be no SYNC_FINAL_OVERRIDDEN bugs in " + okClass);
	}

	@Test
	public void shouldFindFinalAnnotatedMethodDirectExtension() {
		// given
		Class<?> classWithProblem = OverridesFinalMethodDirectlyBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_FINAL_OVERRIDDEN bug in " + classWithProblem);
	}

	@Test
	public void shouldFindFinalAnnotatedMethodIndirectExtension() {
		// given
		Class<?> classWithProblem = OverridesFinalMethodIndirectlyBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_FINAL_OVERRIDDEN bug in " + classWithProblem);
	}

	@Test
	public void shouldAllowFinalMethodOverload() {
		// given
		Class<?> okClass = OverloadFinalMethod.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertEquals(bugs.size(), 0, "There should be 0 SYNC_FINAL_OVERRIDDEN bugs in " + okClass);
	}

	@Test
	public void shouldNarrowedReturnTypeBeDetected() {
		// given
		Class<?> classWithProblem = NarrowingFinalMethodReturnType.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_FINAL_OVERRIDDEN bug in " + classWithProblem);
	}

	private List<BugInstance> runDetector(Class<?> testedClass) {
		BugPattern bugPattern = new BugPattern("SYNC_FINAL_OVERRIDDEN", "AF", "CORRECTNESS", true, "", "", "");
		Detector finalAssertionDetector = new FinalOverrideDetector(getBugReporter());
		Collection<BugInstance> bugs = runDetector(finalAssertionDetector, testedClass, bugPattern);
		return new ArrayList<BugInstance>(bugs);
	}
}
