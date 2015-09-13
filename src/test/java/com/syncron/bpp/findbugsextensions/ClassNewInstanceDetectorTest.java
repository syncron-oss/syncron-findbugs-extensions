package com.syncron.bpp.findbugsextensions;

import org.testng.annotations.DataProvider;

import com.syncron.bpp.findbugsextensions.utils.DetectorTestCaseTemplate;

public class ClassNewInstanceDetectorTest extends DetectorTestCaseTemplate<ClassNewInstanceDetector> {

	private static class CorrectClass {
	}

	@Override
	@DataProvider
	public Object[][] correctClasses() {
		return new Object[][] {
				{ CorrectClass.class },
		};
	}

	private static class ViolatingClass {
		@SuppressWarnings("unused")
		public static void create(Class<?> clazz) throws InstantiationException, IllegalAccessException {
			Object s = clazz.newInstance();
		}
	}

	@Override
	@DataProvider
	public Object[][] incorrectClassesWithExactlyOneViolation() {
		return new Object[][] {
				{ ViolatingClass.class, ClassNewInstanceDetector.BUG_NAME },
		};
	}
}
