package com.syncron.bpp.findbugsextensions;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;

public class NonStaticLoggerDetectorTest extends BaseDetectorTestCase<NonStaticLoggerDetector> {

	@Test
	public void shouldNotDetectAnyFaultsInNonStaticUsage() {
		// given
		Class<?> okClass = NonStaticLoggerProperUsage.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertTrue(bugs.isEmpty(), "There should be no SYNC_NON_STATIC_LOGGER bugs in " + okClass);
	}

	@Test
	public void shouldNotDetectAnyBugsInStaticUsage() {
		// given
		Class<?> okClass = StaticLoggerUsage.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertTrue(bugs.isEmpty(), "There should be no SYNC_NON_STATIC_LOGGER bugs in " + okClass);
	}

	@Test
	public void shouldDetectLoggerSetter() {
		// given
		Class<?> classWithProblem = NonStaticLoggerInvalidUsage.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(NonStaticLoggerDetector.BUG_NAME), "found bugs' types");
	}

	@Test
	public void shouldDetectLoggerInImpliciteConstructor() {
		// given
		Class<?> classWithProblem = NonStaticLoggerCreatedInImpliciteConstructor.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(NonStaticLoggerDetector.BUG_NAME), "found bugs' types");
	}

	@Test
	public void shouldDetectLoggerInExpliciteConstructor() {
		// given
		Class<?> classWithProblem = NonStaticLoggerCreatedInExpliciteConstructor.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(NonStaticLoggerDetector.BUG_NAME), "found bugs' types");
	}

	@Test
	public void shouldAllowNonStaticLoggerInNonStaticInnerClass() {
		// given
		Class<?> okClass = NonStaticLoggerInNonStaticInnerClass.InnerClassWithLogger.class;

		// when
		List<BugInstance> bugs = runDetector(okClass);

		// then
		Assert.assertEquals(bugs.size(), 0, "There should be no SYNC_NON_STATIC_LOGGER bug in " + okClass);
	}

	@Test
	public void shouldDetectNonStaticLoggerInStaticInnerClass() {
		// given
		Class<?> classWithProblem = NonStaticLoggerInStaticInnerClass.InnerClassWithNonStaticLogger.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		assertEquals(bugs.size(), 1, "There should be bug");
		assertEquals(getTypes(bugs), asList(NonStaticLoggerDetector.BUG_NAME), "found bugs' types");
	}
}
