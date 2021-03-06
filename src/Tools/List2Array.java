package Tools;

import java.util.*;

public class List2Array {

	public final static java.lang.String[] toStringArray(ArrayList<java.lang.String> list) {
		if(list == null || list.size() == 0) {
			return new java.lang.String[0];
		}
		java.lang.String[] res = new java.lang.String[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static boolean[] toBooleanArray(ArrayList<Boolean> list) {
		if(list == null || list.size() == 0) {
			return new boolean[0];
		}
		boolean[] res = new boolean[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static int[] toIntArray(ArrayList<Integer> list) {
		if(list == null || list.size() == 0) {
			return new int[0];
		}
		int[] res = new int[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static long[] toLongArray(ArrayList<Long> list) {
		if(list == null || list.size() == 0) {
			return new long[0];
		}
		long[] res = new long[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	public final static double[] toDoubleArray(ArrayList<Double> list) {
		if(list == null || list.size() == 0) {
			return new double[0];
		}
		double[] res = new double[list.size()];
		for(int i=0; i<list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
}
