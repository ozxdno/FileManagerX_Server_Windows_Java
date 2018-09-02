package com.FileManagerX.Tools;

public class SetElements {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static void set(boolean[] array, boolean e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void set(byte[] array, byte e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void set(char[] array, char e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void set(int[] array, int e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void set(long[] array, long e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void set(double[] array, double e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static <T> void set(T[] array, T e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static void set(boolean[] array, boolean e, int bg, int ed) {
		if(array == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= array.length) { ed = array.length-1; }
		
		for(int i=bg; i<=ed; i++) {
			array[i] = e;
		}
	}
	public final static void set(byte[] array, byte e, int bg, int ed) {
		if(array == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= array.length) { ed = array.length-1; }
		
		for(int i=bg; i<=ed; i++) {
			array[i] = e;
		}
	}
	public final static void set(char[] array, char e, int bg, int ed) {
		if(array == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= array.length) { ed = array.length-1; }
		
		for(int i=bg; i<=ed; i++) {
			array[i] = e;
		}
	}
	public final static void set(int[] array, int e, int bg, int ed) {
		if(array == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= array.length) { ed = array.length-1; }
		
		for(int i=bg; i<=ed; i++) {
			array[i] = e;
		}
	}
	public final static void set(long[] array, long e, int bg, int ed) {
		if(array == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= array.length) { ed = array.length-1; }
		
		for(int i=bg; i<=ed; i++) {
			array[i] = e;
		}
	}
	public final static void set(double[] array, double e, int bg, int ed) {
		if(array == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= array.length) { ed = array.length-1; }
		
		for(int i=bg; i<=ed; i++) {
			array[i] = e;
		}
	}
	public final static <T> void set(T[] array, T e, int bg, int ed) {
		if(array == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= array.length) { ed = array.length-1; }
		
		for(int i=bg; i<=ed; i++) {
			array[i] = e;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static <T> void set(java.util.List<T> list, T e) {
		if(list == null) { return; }
		for(int i=0; i<list.size(); i++) {
			list.set(i, e);
		}
	}
	public final static <T> void set(java.util.List<T> list, T e, int bg, int ed) {
		if(list == null) { return; }
		if(bg < 0) { bg = 0; }
		if(ed >= list.size()) { ed = list.size()-1; }
		
		for(int i=bg; i<=ed; i++) {
			list.set(i, e);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
