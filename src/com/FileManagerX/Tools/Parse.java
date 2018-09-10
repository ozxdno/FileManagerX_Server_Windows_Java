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
	
	public final static Object parseAny(String value, com.FileManagerX.BasicEnums.DataType type, Class<?> clazz) {
		return null;
	}
	
}
