package com.FileManagerX.Test;

public class ReflectClone {

	public static void main(String[] args) {
		
		java.util.List<Integer> list = new java.util.ArrayList<>();
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		
		java.lang.reflect.Method m = com.FileManagerX.Tools.Reflect.getMethod("add", list, Object.class);
		com.FileManagerX.Tools.Reflect.executeMethod(m, list, 2);
		System.out.println(list);
	}

}
