package com.FileManagerX.BasicCollections;

public class BasicLinkedHashMap <T extends com.FileManagerX.Interfaces.IPublic>
	implements com.FileManagerX.Interfaces.ICollection<T>,
			   com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.Interfaces.ICollection.IKey key;
	private java.util.Map<Object, T> content;
	private Class<T> clazz;
	private boolean safe;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setContent(com.FileManagerX.Interfaces.ICollection<T> content) {
		return false;
	}
	public boolean setKey(com.FileManagerX.Interfaces.ICollection.IKey key) {
		if(key == null) { return false; }
		this.key = key;
		return true;
	}
	public boolean setSafe(boolean safe) {
		if(safe == this.safe) { return true; }
		java.util.Map<Object, T> newcontent = safe ?
				java.util.Collections.synchronizedMap(new java.util.LinkedHashMap<>(16, 0.75f, true)) :
				new java.util.LinkedHashMap<>(16, 0.75f, true);
		newcontent.putAll(this.content);
		this.content = newcontent;
		this.safe = safe;
		return true;
	}
	public boolean setClass(Class<T> clazz) {
		this.clazz = clazz;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IIterator<T> getIterator() {
		return new IteratorImpl();
	}
	public Object getKey(T e) {
		return key == null ? null : key.getKey(e);
	}

	public com.FileManagerX.Interfaces.ICollection.IKey getIKey() {
		return this.key;
	}
	public Class<T> getTClass() {
		return this.clazz;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicLinkedHashMap() {
		initThis();
	}
	private void initThis() {
		this.content = new java.util.LinkedHashMap<>(16, 0.75f, true);
		this.key = com.FileManagerX.Interfaces.ICollection.defaultKey();
		this.safe = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T createT() { 
		try {
			return clazz == null ? null : clazz.newInstance();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public int size() {
		return this.content.size();
	}
	public void clear() {
		initThis();
	}
	public boolean add(T item) {
		this.content.put(this.getKey(item), item);
		return true;
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
		String res = "";
		while(it.hasNext()) {
			res += "[" + it.getNext().toString() + "]->";
		}
		return res;
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.content.size());
		for(java.util.Map.Entry<Object, T> item : this.content.entrySet()) {
			c.addToBottom(item.getValue().toConfig());
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
			this.add(t);
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
	public BasicLinkedHashMap<T> searchesByCount(int bg, int ed) {
		BasicLinkedHashMap<T> res = new BasicLinkedHashMap<T>();
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
	public BasicLinkedHashMap<T> fetchesByCount(int bg, int ed) {
		BasicLinkedHashMap<T> res = new BasicLinkedHashMap<T>();
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
	
	public T searchByKey(Object key) {
		return this.content.get(key);
	}
	public T fetchByKey(Object key) {
		return this.content.remove(key);
	}
	public BasicLinkedHashMap<T> searchesByKey(Object key) {
		BasicLinkedHashMap<T> res = new BasicLinkedHashMap<T>();
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				res.add(it.getNext());
			}
		}
		return res;
	}
	public BasicLinkedHashMap<T> fetchesByKey(Object key) {
		BasicLinkedHashMap<T> res = new BasicLinkedHashMap<T>();
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
		private java.util.Iterator<java.util.Map.Entry<Object, T>> iterator = content.entrySet().iterator();
		T item = null;
		
		public boolean hasNext() {
			boolean ok = iterator.hasNext();
			item = ok ? iterator.next().getValue() : null;
			return ok;
		}
		public T getNext() {
			return item;
		}
		public void remove() {
			iterator.remove();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
