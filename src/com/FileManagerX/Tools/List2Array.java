package com.FileManagerX.Tools;

import java.util.*;

public class List2Array {

	public final static java.lang.String[] toStringArray(List<java.lang.String> list) {
		if(list == null || list.size() == 0) {
			return new java.lang.String[0];
		}
		java.lang.String[] res = new java.lang.String[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static boolean[] toBooleanArray(List<Boolean> list) {
		if(list == null || list.size() == 0) {
			return new boolean[0];
		}
		boolean[] res = new boolean[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static byte[] toByteArray(List<Byte> list) {
		if(list == null || list.size() == 0) {
			return new byte[0];
		}
		byte[] res = new byte[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static int[] toIntArray(List<Integer> list) {
		if(list == null || list.size() == 0) {
			return new int[0];
		}
		int[] res = new int[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static long[] toLongArray(List<Long> list) {
		if(list == null || list.size() == 0) {
			return new long[0];
		}
		long[] res = new long[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static double[] toDoubleArray(List<Double> list) {
		if(list == null || list.size() == 0) {
			return new double[0];
		}
		double[] res = new double[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static <T> boolean toAnyArray(List<T> list, T[] result) {
		if(list == null || list.size() == 0) {
			return false;
		}
		if(list.size() != result.length) {
			return false;
		}
		for(int i=0; i<list.size(); i++) {
			result[i] = list.get(i);
		}
		return true;
	}
}
