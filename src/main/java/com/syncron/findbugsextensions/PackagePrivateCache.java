package com.syncron.findbugsextensions;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class PackagePrivateCache {

	private Map<String, CacheMetaInfo> cache = new TreeMap<String, CacheMetaInfo>();

	public void add(String className, String methodName, String signature) {
		Set<String> methods = getClassMetaInfo(className).getMethods();
		methods.add(joinMethodNameAndSignature(methodName, signature));
	}

	private String joinMethodNameAndSignature(String methodName, String signature) {
		return String.format("%s%s", methodName, signature);
	}

	private CacheMetaInfo getClassMetaInfo(String className) {
		CacheMetaInfo info = cache.get(className);
		if (info == null) {
			info = new CacheMetaInfo();
			cache.put(className, info);
		}
		return info;
	}

	public boolean contains(String className, String methodName, String signature) {
		if (cache.containsKey(className))
			return cache.get(className).contains(joinMethodNameAndSignature(methodName, signature));
		else
			return false;
	}

	public boolean isClassAnnotated(String className) {
		if (cache.containsKey(className))
			return cache.get(className).isClassAnnotated();
		else
			return false;
	}

	public int numberOfCachedClasses() {
		return cache.size();
	}

	public void add(String className) {
		getClassMetaInfo(className).markAsAnnotated();
	}

	private static class CacheMetaInfo {

		private Set<String> methods = new TreeSet<String>();

		private boolean isClassAnnotated;

		public boolean contains(String methodNameAndSignature) {
			return methods.contains(methodNameAndSignature);
		}

		public void markAsAnnotated() {
			isClassAnnotated = true;
		}

		public Set<String> getMethods() {
			return methods;
		}

		public boolean isClassAnnotated() {
			return isClassAnnotated;
		}
	}
}
