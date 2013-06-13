package com.syncron.findbugsextensions.utils;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BugReporterObserver;
import edu.umd.cs.findbugs.ProjectStats;
import edu.umd.cs.findbugs.SortedBugCollection;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;

public class PrintStreamBugReporter implements BugReporter {

	private ProjectStats stats;

	private PrintStream stream;

	private List<BugReporterObserver> observers;

	private boolean ignoreMissingClasses;

	private BugCollection bugCollection;

	public PrintStreamBugReporter(PrintStream stream) {
		this.stream = stream;
		reset();
	}

	public void setIngnoreMissingClasses(boolean ignoreMissingClasses) {
		this.ignoreMissingClasses = ignoreMissingClasses;
	}

	@Override
	public void reportMissingClass(ClassNotFoundException ex) {
		if (ignoreMissingClasses)
			return;
		ex.printStackTrace(stream);
	}

	@Override
	public void reportMissingClass(ClassDescriptor classDescriptor) {
		if (ignoreMissingClasses)
			return;
		stream.println(String.format("Class not found: %s", String.valueOf(classDescriptor)));
	}

	@Override
	public void logError(String message) {
		stream.println(message);
	}

	@Override
	public void logError(String message, Throwable e) {
		stream.println(message);
		e.printStackTrace(stream);
	}

	@Override
	public void reportSkippedAnalysis(MethodDescriptor method) {
		stream.println(String.format("Skipped method: %s", String.valueOf(method)));
	}

	@Override
	public void observeClass(ClassDescriptor classDescriptor) {
	}

	@Override
	public void setErrorVerbosity(int level) {
	}

	@Override
	public void setPriorityThreshold(int threshold) {
	}

	@Override
	public void reportBug(BugInstance bugInstance) {
		stats.addBug(bugInstance);
		bugCollection.add(bugInstance);
		for (BugReporterObserver observer : observers)
			observer.reportBug(bugInstance);
		stream.println(bugInstance.getAbridgedMessage());
	}

	@Override
	public void finish() {
	}

	@Override
	public void reportQueuedErrors() {
	}

	@Override
	public void addObserver(BugReporterObserver observer) {
		observers.add(observer);
	}

	@Override
	public ProjectStats getProjectStats() {
		return stats;
	}

	@Override
	public BugCollection getBugCollection() {
		return bugCollection;
	}

	public void reset() {
		ignoreMissingClasses = true;
		bugCollection = new SortedBugCollection();
		observers = new LinkedList<BugReporterObserver>();
		stats = new ProjectStats();
	}

}
