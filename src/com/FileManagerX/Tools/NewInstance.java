package com.FileManagerX.Tools;

public class NewInstance {

	public final static Object createInstance(java.lang.String name) {
		try {
			Object o = Class.forName(name).newInstance();
			return o;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	
	public final static Object createInstance(Class<?> c) {
		try {
			Object o = c.newInstance();
			return o;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public final static Class<?> getRawType(java.lang.reflect.Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof java.lang.reflect.ParameterizedType) {
        	java.lang.reflect.ParameterizedType parameterizedType = (java.lang.reflect.ParameterizedType) type;
        	java.lang.reflect.Type rawType = parameterizedType.getRawType();
            return (Class) rawType;
        } else if (type instanceof java.lang.reflect.GenericArrayType) {
        	java.lang.reflect.Type componentType = ((java.lang.reflect.GenericArrayType) type).getGenericComponentType();
            return java.lang.reflect.Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof java.lang.reflect.TypeVariable) {
            return Object.class;
        } else if (type instanceof java.lang.reflect.WildcardType) {
            return getRawType(((java.lang.reflect.WildcardType) type).getUpperBounds()[0]);
        } else {
            java.lang.String className = type == null ? "null" : type.getClass().getName();
            com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(
            		"Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className
            		);
            return null;
        }
    }
}
