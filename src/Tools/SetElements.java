package Tools;

public class SetElements {
	
	public final static void setBooleanArrayElements(boolean[] array, boolean e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void setIntArrayElements(int[] array, int e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void setLongArrayElements(long[] array, long e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void setDoubleArrayElements(double[] array, double e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = e;
		}
	}
	public final static void setStringArrayElements(java.lang.String[] array, java.lang.String e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i] = new java.lang.String(e);
		}
	}
	public final static <T extends Interfaces.IPublic> void setArrayElements(T[] array, T e) {
		if(array == null) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			array[i].copyReference(e);
		}
	}

	public final static void setBooleanListElements(java.util.List<Boolean> list, boolean e) {
		if(list == null) {
			return;
		}
		for(int i=0; i<list.size(); i++) {
			list.set(i, e);
		}
	}
	public final static void setIntListElements(java.util.List<Integer> list, int e) {
		if(list == null) {
			return;
		}
		for(int i=0; i<list.size(); i++) {
			list.set(i, e);
		}
	}
	public final static void setLongListElements(java.util.List<Long> list, long e) {
		if(list == null) {
			return;
		}
		for(int i=0; i<list.size(); i++) {
			list.set(i, e);
		}
	}
	public final static void setDoubleListElements(java.util.List<Double> list, double e) {
		if(list == null) {
			return;
		}
		for(int i=0; i<list.size(); i++) {
			list.set(i, e);
		}
	}
	public final static void setStringListElements(java.util.List<java.lang.String> list, java.lang.String e) {
		if(list == null) {
			return;
		}
		for(int i=0; i<list.size(); i++) {
			list.set(i, new java.lang.String(e));
		}
	}
	public final static <T extends Interfaces.IPublic> void setListElements(java.util.List<T> list, T e) {
		if(list == null) {
			return;
		}
		for(int i=0; i<list.size(); i++) {
			list.get(i).copyReference(e);
		}
	}
}
