package com.FileManagerX.Tools;

public class Parse {

	public final static boolean parseBoolean(String value) {
		try {
			return Integer.parseInt(value) != 0;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
	}
	
	public final static byte parseByte(String value) {
		try {
			return (byte)Integer.parseInt(value);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	
	public final static char parseChar(String value) {
		try {
			return value.charAt(0);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	
	public final static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	
	public final static long parseLong(String value) {
		try {
			return Long.parseLong(value);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	
	public final static double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	
	public final static Object parseEnum(String value, Class<?> enumClass) {
		try {
			java.lang.reflect.Method method = enumClass.getMethod("values");
			java.lang.Enum<?>[] enums = (java.lang.Enum<?>[])method.invoke(null);
			for(int i=0; i<enums.length; i++) {
				if(enums[i].toString().equals(value)) {
					return enums[i];
				}
			}
			return null;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	
	public final static Object parseAny(String value, com.FileManagerX.BasicEnums.DataType type,
			Class<?> targetClass) {
		if(com.FileManagerX.BasicEnums.DataType.BOOLEAN.equals(type)) {
			return parseBoolean(value);
		}
		if(com.FileManagerX.BasicEnums.DataType.BYTE.equals(type)) {
			return parseByte(value);
		}
		if(com.FileManagerX.BasicEnums.DataType.CHAR.equals(type)) {
			return parseChar(value);
		}
		if(com.FileManagerX.BasicEnums.DataType.INTEGER.equals(type)) {
			return parseInt(value);
		}
		if(com.FileManagerX.BasicEnums.DataType.LONG.equals(type)) {
			return parseLong(value);
		}
		if(com.FileManagerX.BasicEnums.DataType.DOUBLE.equals(type)) {
			return parseDouble(value);
		}
		if(com.FileManagerX.BasicEnums.DataType.STRING.equals(type)) {
			return value;
		}
		if(com.FileManagerX.BasicEnums.DataType.ENUM.equals(type)) {
			return parseEnum(value, targetClass);
		}
		return null;
	}
	
}
