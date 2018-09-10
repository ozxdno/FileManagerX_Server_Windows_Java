package com.FileManagerX.Test;

public class LHM {

	public static void main(String[] args) {
		
		java.util.LinkedHashMap<Integer, String> lhm = new java.util.LinkedHashMap<>(16, 0.75f, true);
		
		lhm.put(1, "a");
		lhm.put(2, "b");
		lhm.put(3, "c");
		lhm.put(4, "d");
		lhm.put(5, "e");
		
		System.out.println(lhm);
		
		lhm.get(3);
		
		System.out.println(lhm);
	}
	
}
