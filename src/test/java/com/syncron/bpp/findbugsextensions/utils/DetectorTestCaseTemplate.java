package com.syncron.bpp.findbugsextensions.utils;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.Detector;

/**
 * @author piofin <piotr.findeisen@syncron.com>
 * @since Jan 20, 2016
 */
public abstract class DetectorTestCaseTemplate<DetectorType extends Detector>
		extends BaseDetectorTestCase<DetectorType> {

	@Test(dataProvider = "correctClasses")
	public void shouldNotReportBugInCorrectClass(Class<?> correctClass) {
		// when
		List<BugInstance> bugs = runDetector(correctClass);

		// then
		assertEquals(bugs.size(), 0, "There should be no bugs");
	}

	@DataProvider
	public abstract Object[][] correctClasses();

	@Test(dataProvider = "incorrectClassesWithExactlyOneViolation")
	public void shouldReportBugInViolatingClass(Class<?> violatingClass, String expectedBugName) {
		// when
		List<BugInstance> bugs = runDetector(violatingClass);

		// then
		assertEquals(bugs.size(), 1, "There should be bugs");
		assertEquals(getTypes(bugs), asList(expectedBugName), "found bugs' types");
	}

	@DataProvider
	public abstract Object[][] incorrectClassesWithExactlyOneViolation();

}
