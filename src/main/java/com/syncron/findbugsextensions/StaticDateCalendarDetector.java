package com.syncron.findbugsextensions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.bcel.classfile.Field;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.ba.AnalysisContext;
import edu.umd.cs.findbugs.ba.ch.Subtypes2;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.DescriptorFactory;

/**
 * 18.07.2013
 * 
 * @author piogla piotr.glazar@syncron.com
 * 
 */
public class StaticDateCalendarDetector extends BytecodeScanningDetector {

	private static final String CALENDAR_BUG_NAME = "SYNC_STATIC_CALENDAR_INSTANCE";

	private static final String SIMPLE_DATE_FORMAT_BUG_NAME = "SYNC_STATIC_SIMPLE_DATE_FORMAT_INSTANCE";

	private final ClassDescriptor calendarType = DescriptorFactory.createClassDescriptor(Calendar.class);

	private final ClassDescriptor simpleDateType = DescriptorFactory.createClassDescriptor(SimpleDateFormat.class);

	private Subtypes2 subtypes2 = AnalysisContext.currentAnalysisContext().getSubtypes2();

	private BugReporter bugReporter;

	public StaticDateCalendarDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	@Override
	public void visit(Field obj) {
		ClassDescriptor classOfField = DescriptorFactory.createClassDescriptorFromFieldSignature(obj.getSignature());
		if (classOfField == null) {
			// no class descriptor and hence nothing to do
			return;
		}
		if (obj.isStatic()) {
			if (isCalendar(classOfField)) {
				bugReporter.reportBug(staticCalendarInstance());
			} else if (isSimpleDate(classOfField)) {
				bugReporter.reportBug(staticSimpleDateFormatInstance());
			}
		}
		super.visit(obj);
	}

	private BugInstance staticSimpleDateFormatInstance() {
		return new BugInstance(this, SIMPLE_DATE_FORMAT_BUG_NAME, NORMAL_PRIORITY).addClass(this).addField(this);
	}

	private BugInstance staticCalendarInstance() {
		return new BugInstance(this, CALENDAR_BUG_NAME, NORMAL_PRIORITY).addClass(this).addField(this);
	}

	private boolean isSimpleDate(ClassDescriptor classOfField) {
		return isSubtype(classOfField, simpleDateType);
	}

	private boolean isCalendar(ClassDescriptor classOfField) {
		return isSubtype(classOfField, calendarType);
	}

	private boolean isSubtype(ClassDescriptor subClass, ClassDescriptor superClass) {
		try {
			return subtypes2.isSubtype(subClass, superClass);
		} catch (ClassNotFoundException e) {
			bugReporter.reportMissingClass(e);
		}
		return false;
	}
}
