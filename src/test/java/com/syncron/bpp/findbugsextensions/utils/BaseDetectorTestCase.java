package com.syncron.bpp.findbugsextensions.utils;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.List;

import org.testng.annotations.BeforeClass;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;

public abstract class BaseDetectorTestCase<DetectorType extends Detector> {

	protected final Class<DetectorType> detectorClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDetectorTestCase() {
		TypeToken<?> detectorClassToken = TypeToken.of(getClass())
				.resolveType(getOnlyElement(asList(BaseDetectorTestCase.class.getTypeParameters())));
		checkState(detectorClassToken.getType() instanceof Class<?>,
				"Could not determine DetectorType from this.getClass()");

		this.detectorClass = (Class) checkNotNull(detectorClassToken.getRawType(), "detectorClassToken.getRawType()");
	}

	public BaseDetectorTestCase(Class<DetectorType> detectorClass) {
		super();
		this.detectorClass = checkNotNull(detectorClass, "detectorClass");
	}

	@BeforeClass
	public void forceInitializationOfFindbugsMetaData() {
		DetectorRunner.instance();
	}

	protected List<BugInstance> runDetector(Class<?> testExample) {

		PrintStreamBugReporter bugReporter = DetectorRunner.STATIC_BUG_REPORTER;
		DetectorType detector = instantiateDetector(bugReporter);
		DetectorRunner.runDetectorOnClass(detector, testExample, bugReporter);
		Collection<BugInstance> bugCollection = bugReporter.getBugCollection().getCollection();

		bugReporter.reset();

		return newArrayList(bugCollection);
	}

	protected static List<String> getTypes(Iterable<BugInstance> bugs) {
		ImmutableList<String> types = FluentIterable.from(bugs)
				.transform(new Function<BugInstance, String>() {
					@Override
					public String apply(BugInstance input) {
						return input.getType();
					}
				})
				.toList();
		
		return types;
	}

	private DetectorType instantiateDetector(BugReporter bugReporter) {
		try {
			return detectorClass
					.getConstructor(BugReporter.class)
					.newInstance(bugReporter);

		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}
}
