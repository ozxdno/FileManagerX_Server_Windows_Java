package Tools;

import java.util.*;

public class Array2List {
	
	public final static ArrayList<java.lang.String> toStringList(java.lang.String[] a) {
		ArrayList<java.lang.String> res = new ArrayList<java.lang.String>();
		if(a == null || a.length == 0) {
			return res;
		}
		for(int i=0; i<a.length; i++) {
			res.add(a[i]);
		}
		return res;
	}
	public final static ArrayList<Boolean> toBooleanList(boolean[] array) {
		ArrayList<Boolean> res = new ArrayList<Boolean>();
		if(array == null || array.length == 0) {
			return res;
		}
		for(int i=0; i<array.length; i++) {
			res.add(array[i]);
		}
		return res;
	}
	public final static ArrayList<Integer> toIntList(int[] array) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if(array == null || array.length == 0) {
			return res;
		}
		for(int i=0; i<array.length; i++) {
			res.add(array[i]);
		}
		return res;
	}
	public final static ArrayList<Long> toLongList(long[] array) {
		ArrayList<Long> res = new ArrayList<Long>();
		if(array == null || array.length == 0) {
			return res;
		}
		for(int i=0; i<array.length; i++) {
			res.add(array[i]);
		}
		return res;
	}
	public final static ArrayList<Double> toDoubleList(double[] array) {
		ArrayList<Double> res = new ArrayList<Double>();
		if(array == null || array.length == 0) {
			return res;
		}
		for(int i=0; i<array.length; i++) {
			res.add(array[i]);
		}
		return res;
	}
}
