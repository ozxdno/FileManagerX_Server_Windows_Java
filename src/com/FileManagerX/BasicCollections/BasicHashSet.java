package com.FileManagerX.BasicCollections;

public class BasicHashSet <T extends com.FileManagerX.Interfaces.IPublic, K>
	implements com.FileManagerX.Interfaces.ICollection<T, K>,
			   com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private java.util.HashSet<T> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IIterator<T> getIterator() {
		return new IteratorImpl();
	}
	public K getKey(T e) {
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicHashSet() {
		initThis();
	}
	private void initThis() {
		this.content = new java.util.HashSet<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T createT() {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) 
		        		((java.lang.reflect.ParameterizedType)
		        				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		        return entityClass.newInstance();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int size() {
		return this.content.size();
	}
	public void clear() {
		initThis();
	}
	public boolean add(T item) {
		if(item == null) {
			return false;
		}
		return this.content.add(item);
	}
	public boolean sort(java.util.Comparator<T> c) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toString() {
		if(content == null || content.size() == 0) {
			return "Empty";
		}
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		String res = it.getNext().toString();
		while(it.hasNext()) {
			res += ", " + it.getNext().toString();
		}
		return res;
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.content.size());
		for(T item : this.content) {
			c.addToBottom(item.toConfig());
		}
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
		if(in == null) { return null; }
		if(in.length() == 0) { this.clear(); return c; }
		return this.input(c);
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		int amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		for(int i=0; i<amount; i++) {
			T t = createT();
			c = t.input(c);
			if(!c.getIsOK()) { return c; }
			this.content.add(t);
		}
		
		return c;
	}
	public void copyReference(Object o) {
		com.FileManagerX.Tools.CopyCollections.copyReference(o, this);
	}
	public void copyValue(Object o) {
		com.FileManagerX.Tools.CopyCollections.copyValue(o, this);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T searchByCount(int count) {
		if(count < 0 || count > this.content.size()) { return null; }
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(count-- == 0) {
				return it.getNext();
			}
		}
		return null;
	}
	public T fetchByCount(int count) {
		if(count < 0 || count > this.content.size()) { return null; }
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(count-- == 0) {
				it.remove();
				return it.getNext();
			}
		}
		return null;
	}
	public BasicHashSet<T,K> searchesByCount(int bg, int ed) {
		BasicHashSet<T,K> res = new BasicHashSet<T,K>();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size() - 1; }
		
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		int cnt = 0;
		while(it.hasNext()) {
			if(cnt < bg) { cnt++; continue; }
			if(cnt <= ed) { res.add(it.getNext()); cnt++; continue; }
			if(cnt > ed) { break; }
		}
		return res;
	}
	public BasicHashSet<T,K> fetchesByCount(int bg, int ed) {
		BasicHashSet<T,K> res = new BasicHashSet<T,K>();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size() - 1; }
		
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		int cnt = 0;
		while(it.hasNext()) {
			if(cnt < bg) { cnt++; continue; }
			if(cnt <= ed) { res.add(it.getNext()); it.remove(); cnt++; continue; }
			if(cnt > ed) { break; }
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T searchByKey(K key) {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				return it.getNext();
			}
		}
		return null;
	}
	public T fetchByKey(K key) {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			T t = it.getNext();
			if(this.getKey(t).equals(key)) {
				it.remove();
				return t;
			}
		}
		return null;
	}
	public BasicHashSet<T,K> searchesByKey(K key) {
		BasicHashSet<T,K> res = new BasicHashSet<T,K>();
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				res.add(it.getNext());
			}
		}
		return res;
	}
	public BasicHashSet<T,K> fetchesByKey(K key) {
		BasicHashSet<T,K> res = new BasicHashSet<T,K>();
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				res.add(it.getNext());
				it.remove();
			}
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class IteratorImpl implements com.FileManagerX.Interfaces.IIterator<T> {
		private java.util.Iterator<T> iterator = content.iterator();
		
		public boolean hasNext() {
			return iterator.hasNext();
		}
		public T getNext() {
			return iterator.next();
		}
		public void remove() {
			iterator.remove();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
