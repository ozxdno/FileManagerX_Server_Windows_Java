package com.FileManagerX.BasicCollections;

public class BasicArrayList <T extends com.FileManagerX.Interfaces.IPublic, K>
	implements com.FileManagerX.Interfaces.ICollection<T, K>,
			   com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private java.util.ArrayList<T> content;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IIterator<T> getIterator() {
		return new IteratorImpl();
	}
	public K getKey(T e) {
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicArrayList() {
		initThis();
	}
	private void initThis() {
		this.content = new java.util.ArrayList<>();
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
		this.content.add(item);
		return true;
	}
	public boolean sort(java.util.Comparator<T> c) {
		this.content.sort(c);
		return true;
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
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
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
		return this.content.get(count);
	}
	public T fetchByCount(int count) {
		if(count < 0 || count > this.content.size()) { return null; }
		return this.content.remove(count);
	}
	public BasicArrayList<T,K> searchesByCount(int bg, int ed) {
		BasicArrayList<T,K> res = new BasicArrayList<T,K>();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size() - 1; }
		for(int i=bg; i<=ed; i++) {
			res.add(this.content.get(i));
		}
		return res;
	}
	public BasicArrayList<T,K> fetchesByCount(int bg, int ed) {
		BasicArrayList<T,K> res = new BasicArrayList<T,K>();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size() - 1; }
		for(int i=ed; i>=bg; i--) {
			res.add(this.content.get(i));
			this.content.remove(i);
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
	public BasicArrayList<T,K> searchesByKey(K key) {
		BasicArrayList<T,K> res = new BasicArrayList<T,K>();
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				res.add(it.getNext());
			}
		}
		return res;
	}
	public BasicArrayList<T,K> fetchesByKey(K key) {
		BasicArrayList<T,K> res = new BasicArrayList<T,K>();
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
