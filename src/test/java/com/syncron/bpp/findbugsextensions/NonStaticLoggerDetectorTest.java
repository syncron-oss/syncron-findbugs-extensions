package com.syncron.bpp.findbugsextensions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.syncron.bpp.findbugsextensions.NonStaticLoggerDetector;
import com.syncron.bpp.findbugsextensions.utils.BaseDetectorTestCase;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugPattern;

public class NonStaticLoggerDetectorTest extends BaseDetectorTestCase {

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
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_NON_STATIC_LOGGER bug in " + classWithProblem);
	}

	@Test
	public void shouldDetectLoggerInImpliciteConstructor() {
		// given
		Class<?> classWithProblem = NonStaticLoggerCreatedInImpliciteConstructor.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_NON_STATIC_LOGGER bug in " + classWithProblem);
	}

	@Test
	public void shouldDetectLoggerInExpliciteConstructor() {
		// given
		Class<?> classWithProblem = NonStaticLoggerCreatedInExpliciteConstructor.class;

		// when
		List<BugInstance> bugs = runDetector(classWithProblem);

		// then
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_NON_STATIC_LOGGER bug in " + classWithProblem);
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
		Assert.assertEquals(bugs.size(), 1, "There should be 1 SYNC_NON_STATIC_LOGGER bug in " + classWithProblem);
	}

	private List<BugInstance> runDetector(Class<?> offendingClass) {
		BugPattern bugPattern = new BugPattern("SYNC_NON_STATIC_LOGGER", "SNSL", "CORRECTNESS", true, "", "", "");

		Collection<BugInstance> bugs = runDetector(new NonStaticLoggerDetector(getBugReporter()), offendingClass,
				bugPattern);
		return new LinkedList<BugInstance>(bugs);
	}
}
