package com.FileManagerX.Tools;

public class Reflect {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static Object getField(String name, Object target) {
		Class<?> clazz = target.getClass();
		while(clazz != null && clazz.getName().contains("com.FileManagerX")) {
			try {
				java.lang.reflect.Field f = clazz.getDeclaredField(name);
				f.setAccessible(true);
				return f.get(target);
			} catch(Exception e) {
				clazz = clazz.getSuperclass();
			}
		}
		return null;
		
	}
	public final static boolean setFeild(String name, Object value, Object target) {
		Class<?> clazz = target.getClass();
		while(clazz != null  && clazz.getName().contains("com.FileManagerX")) {
			try {
				java.lang.reflect.Field f = clazz.getDeclaredField(name);
				f.setAccessible(true);
				f.set(target, value);
				return true;
			} catch(Exception e) {
				clazz = clazz.getSuperclass();
			}
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.lang.reflect.Method getMethod(String name, Object target, Class<?>...types) {
		Class<?> clazz = target.getClass();
		while(clazz != null) {
			try {
				java.lang.reflect.Method m = target.getClass().getMethod(name, types);
				m.setAccessible(true);
				return m;
			} catch(Exception e) {
				clazz = clazz.getSuperclass();
			}
		}
		return null;
	}
	public final static Object executeMethod(String name, Object target, Object... args) {
		Class<?>[] types = new Class<?>[args.length];
		for(int i=0; i<args.length; i++) {
			types[i] = args[i].getClass();
		}
		
		Class<?> clazz = target.getClass();
		while(clazz != null) {
			try {
				java.lang.reflect.Method m = target.getClass().getMethod(name, types);
				m.setAccessible(true);
				return m.invoke(target, args);
			} catch(Exception e) {
				clazz = clazz.getSuperclass();
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.lang.reflect.Field[] getFields(Object target) {
		if(target == null) { return new java.lang.reflect.Field[0]; }
		
		java.util.ArrayList<java.lang.reflect.Field> fields = new java.util.ArrayList<>();
		Class<?> clazz = target.getClass();
		while(clazz != null  && clazz.getName().contains("com.FileManagerX")) {
			java.lang.reflect.Field[] fs = clazz.getDeclaredFields();
			for(int i=0; i<fs.length; i++) {
				fields.add(fs[i]);
			}
			clazz = clazz.getSuperclass();
		}
		
		java.lang.reflect.Field[] results = new java.lang.reflect.Field[fields.size()];
		com.FileManagerX.Tools.List2Array.toAnyArray(fields, results);
		return results;
	}
	public final static java.lang.reflect.Method[] getMethods(Object target) {
		if(target == null) { return new java.lang.reflect.Method[0]; }
		
		java.util.ArrayList<java.lang.reflect.Method> methods = new java.util.ArrayList<>();
		Class<?> clazz = target.getClass();
		while(clazz != null  && clazz.getName().contains("com.FileManagerX")) {
			java.lang.reflect.Method[] ms = clazz.getDeclaredMethods();
			for(int i=0; i<ms.length; i++) {
				methods.add(ms[i]);
			}
			clazz = clazz.getSuperclass();
		}
		
		java.lang.reflect.Method[] results = new java.lang.reflect.Method[methods.size()];
		com.FileManagerX.Tools.List2Array.toAnyArray(methods, results);
		return results;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.BasicEnums.DataType getFieldValueType(String name, Object target) {
		Class<?> clazz = target.getClass();
		while(clazz != null && clazz.getName().contains("com.FileManagerX")) {
			try {
				java.lang.reflect.Field f = clazz.getDeclaredField(name);
				return getFieldValueType(f, target);
			} catch(Exception e) {
				clazz = clazz.getSuperclass();
			}
		}
		return null;
	}
	public final static com.FileManagerX.BasicEnums.DataType getFieldValueType
			(java.lang.reflect.Field field, Object target) {
		try {
			field.setAccessible(true);
			String type = field.getType().getName();
			int modify = field.getModifiers();
			if(java.lang.reflect.Modifier.isStatic(modify)) {
				return com.FileManagerX.BasicEnums.DataType.STATIC;
			}
			if(type.equals("boolean") || type.equals("Boolean")) {
				return com.FileManagerX.BasicEnums.DataType.BOOLEAN;
			}
			if(type.equals("int") || type.equals("Integer")) {
				return com.FileManagerX.BasicEnums.DataType.INTEGER;
			}
			if(type.equals("long") || type.equals("Long")) {
				return com.FileManagerX.BasicEnums.DataType.LONG;
			}
			if(type.equals("double") || type.equals("Double")) {
				return com.FileManagerX.BasicEnums.DataType.DOUBLE;
			}
			if(type.equals("java.lang.String")) {
				return com.FileManagerX.BasicEnums.DataType.STRING;
			}
			if(field.get(target) instanceof java.lang.Enum<?>) {
				return com.FileManagerX.BasicEnums.DataType.ENUM;
			}
			return com.FileManagerX.BasicEnums.DataType.OTHERS;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
