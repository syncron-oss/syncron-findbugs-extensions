package com.syncron.bpp.findbugsextensions;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class FinalOverrideDetectorTest extends BaseDetectorTestCase<FinalOverrideDetector> {

	@Test
	public void shouldFindFinalAnnotatedClassExtension() {
		// given
		Class<?> classWithProblem = ExtendsFinalAnnotatedClassBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(FinalOverrideDetector.BUG_NAME), "found bugs' types");
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
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(FinalOverrideDetector.BUG_NAME), "found bugs' types");
	}

	@Test
	public void shouldFindFinalAnnotatedMethodIndirectExtension() {
		// given
		Class<?> classWithProblem = OverridesFinalMethodIndirectlyBug.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(FinalOverrideDetector.BUG_NAME), "found bugs' types");
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
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(FinalOverrideDetector.BUG_NAME), "found bugs' types");
	}
}
