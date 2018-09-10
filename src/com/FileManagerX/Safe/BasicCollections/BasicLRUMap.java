package com.FileManagerX.Safe.BasicCollections;

public class BasicLRUMap <T extends com.FileManagerX.Interfaces.IPublic, K>
	implements com.FileManagerX.Interfaces.ICollection<T, K>,
			   com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private java.util.concurrent.ConcurrentHashMap<K, Element> content;
	private int limit;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setLimit(int limit) {
		this.limit = limit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IIterator<T> getIterator() {
		return new IteratorImpl();
	}
	public K getKey(T e) {
		return null;
	}
	public int getLimit() {
		return limit;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicLRUMap() {
		this.initThis();
	}
	private void initThis() {
		this.content = new java.util.concurrent.ConcurrentHashMap<>();
		this.limit = -1;
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
		Element ex = this.content.get(this.getKey(item));
		if(ex == null) {
			Element ce = new Element();
			ce.e = item;
			this.content.put(this.getKey(item), ce);
		}
		else {
			ex.time = com.FileManagerX.Tools.Time.getTicks();
		}
		
		while(this.content.size() > this.limit) {
			Object key = null;
			long min = Long.MAX_VALUE;
			IteratorImpl it = (IteratorImpl)this.getIterator();
			while(it.hasNext()) {
				Element ie = it.getElement();
				if(ie.time < min) {
					min = ie.time;
					key = this.getKey(ie.e);
				}
			}
			this.content.remove(key);
		}
		
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
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			T item = it.getNext();
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
	public BasicHashMap<T,K> searchesByCount(int bg, int ed) {
		BasicHashMap<T,K> res = new BasicHashMap<T,K>();
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
	public BasicHashMap<T,K> fetchesByCount(int bg, int ed) {
		BasicHashMap<T,K> res = new BasicHashMap<T,K>();
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
		Element e = this.content.get(key);
		return e == null ? null : e.e;
	}
	public T fetchByKey(Object key) {
		Element e = this.content.remove(key);
		return e == null ? null : e.e;
	}
	public BasicHashMap<T,K> searchesByKey(K key) {
		BasicHashMap<T,K> res = new BasicHashMap<T,K>();
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			if(this.getKey(it.getNext()).equals(key)) {
				res.add(it.getNext());
			}
		}
		return res;
	}
	public BasicHashMap<T,K> fetchesByKey(K key) {
		BasicHashMap<T,K> res = new BasicHashMap<T,K>();
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
		private java.util.Iterator<java.util.Map.Entry<K, Element>> iterator = content.entrySet().iterator();
		
		public boolean hasNext() {
			return iterator.hasNext();
		}
		public T getNext() {
			return iterator.next().getValue().e;
		}
		public void remove() {
			iterator.remove();
		}
		
		public Element getElement() {
			return iterator.next().getValue();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class Element {
		public long time = com.FileManagerX.Tools.Time.getTicks();
		public T e = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
