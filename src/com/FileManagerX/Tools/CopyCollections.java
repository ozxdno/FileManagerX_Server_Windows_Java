package com.FileManagerX.Tools;

public class CopyCollections {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public final static<T extends com.FileManagerX.Interfaces.IPublic> void copyReference(
			Object sour,
			com.FileManagerX.Interfaces.ICollection<T> dest) {
		dest.clear();
		if(sour == null || dest == null) { return; }
		if(sour instanceof com.FileManagerX.Interfaces.ICollection<?>) {
			try {
				com.FileManagerX.Interfaces.ICollection<T> s =
						(com.FileManagerX.Interfaces.ICollection<T>)sour;
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
				java.util.Map<?, T> s = (java.util.Map<?, T>)sour;
				for(java.util.Map.Entry<?, T> e : s.entrySet()) {
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
	public final static<T extends com.FileManagerX.Interfaces.IPublic> void copyValue(
			Object sour,
			com.FileManagerX.Interfaces.ICollection<T> dest) {
		dest.clear();
		if(sour == null || dest == null) { return; }
		if(sour instanceof com.FileManagerX.Interfaces.ICollection<?>) {
			try {
				com.FileManagerX.Interfaces.ICollection<T> s =
						(com.FileManagerX.Interfaces.ICollection<T>)sour;
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
				java.util.Map<?, T> s = (java.util.Map<?, T>)sour;
				for(java.util.Map.Entry<?, T> e : s.entrySet()) {
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
