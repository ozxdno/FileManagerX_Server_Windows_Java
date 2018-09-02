package com.FileManagerX.Tools;

public class CopyCollections {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public final static<T extends com.FileManagerX.Interfaces.IPublic, K> void copyReference(
			Object sour,
			com.FileManagerX.Interfaces.ICollection<T, K> dest) {
		dest.clear();
		if(sour == null || dest == null) { return; }
		if(sour instanceof com.FileManagerX.Interfaces.ICollection<?, ?>) {
			try {
				com.FileManagerX.Interfaces.ICollection<T, K> s =
						(com.FileManagerX.Interfaces.ICollection<T, K>)sour;
				com.FileManagerX.Interfaces.IIterator<T> its = s.getIterator();
				while(its.hasNext()) {
					dest.add(its.getNext());
				}
				return;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return;
			}
		}
		if(sour instanceof java.util.Collection<?>) {
			try {
				java.util.Collection<T> s = (java.util.Collection<T>)sour;
				for(T item : s) {
					dest.add(item);
				}
				return;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return;
			}
		}
		if(sour instanceof java.util.Map<?, ?>) {
			try {
				java.util.Map<K, T> s = (java.util.Map<K, T>)sour;
				for(java.util.Map.Entry<K, T> e : s.entrySet()) {
					dest.add(e.getValue());
				}
				return;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return;
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public final static<T extends com.FileManagerX.Interfaces.IPublic, K> void copyValue(
			Object sour,
			com.FileManagerX.Interfaces.ICollection<T, K> dest) {
		dest.clear();
		if(sour == null || dest == null) { return; }
		if(sour instanceof com.FileManagerX.Interfaces.ICollection<?, ?>) {
			try {
				com.FileManagerX.Interfaces.ICollection<T, K> s =
						(com.FileManagerX.Interfaces.ICollection<T, K>)sour;
				com.FileManagerX.Interfaces.IIterator<T> its = s.getIterator();
				while(its.hasNext()) {
					T t = (T)com.FileManagerX.Tools.Reflect.executeMethod("createT", dest);
					t.copyValue(its.getNext());
					dest.add(t);
				}
				return;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return;
			}
		}
		if(sour instanceof java.util.Collection<?>) {
			try {
				java.util.Collection<T> s = (java.util.Collection<T>)sour;
				for(T item : s) {
					T t = (T)com.FileManagerX.Tools.Reflect.executeMethod("createT", dest);
					t.copyValue(item);
					dest.add(t);
				}
				return;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return;
			}
		}
		if(sour instanceof java.util.Map<?, ?>) {
			try {
				java.util.Map<K, T> s = (java.util.Map<K, T>)sour;
				for(java.util.Map.Entry<K, T> e : s.entrySet()) {
					T t = (T)com.FileManagerX.Tools.Reflect.executeMethod("createT", dest);
					t.copyValue(e.getValue());
					dest.add(t);
				}
				return;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return;
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
