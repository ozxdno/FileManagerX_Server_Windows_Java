package com.FileManagerX.BasicCollections;

public class BasicArrayList <T extends com.FileManagerX.Interfaces.IPublic>
	implements com.FileManagerX.Interfaces.ICollection<T>,
			   com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.Interfaces.ICollection.IKey key;
	private java.util.List<T> content;
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
		java.util.List<T> newcontent = safe ?
				java.util.Collections.synchronizedList(new java.util.ArrayList<T>()) :
				new java.util.ArrayList<>();
		newcontent.addAll(this.content);
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

	public BasicArrayList() {
		initThis();
	}
	private void initThis() {
		this.content = new java.util.ArrayList<>();
		this.key = null;
		this.safe = false;
		this.createT();
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
	public BasicArrayList<T> searchesByCount(int bg, int ed) {
		BasicArrayList<T> res = new BasicArrayList<T>();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size() - 1; }
		for(int i=bg; i<=ed; i++) {
			res.add(this.content.get(i));
		}
		return res;
	}
	public BasicArrayList<T> fetchesByCount(int bg, int ed) {
		BasicArrayList<T> res = new BasicArrayList<T>();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size() - 1; }
		for(int i=ed; i>=bg; i--) {
			res.add(this.content.get(i));
			this.content.remove(i);
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T searchByKey(Object key) {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				return it.getNext();
			}
		}
		return null;
	}
	public T fetchByKey(Object key) {
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
	public BasicArrayList<T> searchesByKey(Object key) {
		BasicArrayList<T> res = new BasicArrayList<T>();
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				res.add(it.getNext());
			}
		}
		return res;
	}
	public BasicArrayList<T> fetchesByKey(Object key) {
		BasicArrayList<T> res = new BasicArrayList<T>();
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
		T item = null;
		
		public boolean hasNext() {
			boolean ok = iterator.hasNext();
			item = ok ? iterator.next() : null;
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
