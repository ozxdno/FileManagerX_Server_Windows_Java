package com.FileManagerX.BasicCollections;

public class BasicCollection <T extends com.FileManagerX.Interfaces.IPublic>
	implements com.FileManagerX.Interfaces.ICollection<T> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.Interfaces.ICollection<T> collection;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public boolean setContent(com.FileManagerX.Interfaces.ICollection<T> content) {
		if(content == null) { return false; }
		com.FileManagerX.Interfaces.ICollection.IKey key = 
				this.collection == null ? null : this.collection.getIKey();
		Class<T> clazz = this.collection == null ?
				null : this.collection.getTClass();
		if(content.getIKey() == null) { content.setKey(key); }
		if(content.getTClass() == null) { content.setClass(clazz); }
		this.collection = content;
		if(this.collection.getTClass() == null) {
			try {
				java.lang.reflect.ParameterizedType type = (java.lang.reflect.ParameterizedType)
						this.getClass().getGenericSuperclass();
				clazz = (Class<T>)type.getActualTypeArguments()[0];
				this.collection.setClass(clazz);
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			}
		}
		
		return true;
	}
	public boolean setKey(IKey key) {
		return this.collection.setKey(key);
	}
	public boolean setSafe(boolean safe) {
		return this.collection.setSafe(safe);
	}
	public boolean setClass(Class<T> clazz) {
		return this.collection.setClass(clazz);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IIterator<T> getIterator() {
		return this.collection.getIterator();
	}
	public Object getKey(T e) {
		return this.collection.getKey(e);
	}
	
	public com.FileManagerX.Interfaces.ICollection<T> getContent() {
		return this.collection;
	}
	
	public com.FileManagerX.Interfaces.ICollection.IKey getIKey() {
		return this.collection.getIKey();
	}
	public Class<T> getTClass() {
		return this.collection.getTClass();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicCollection() {
		this.initThis();
	}
	private void initThis() {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public T createT() {
		return this.collection.createT();
	}
	public int size() {
		return this.collection.size();
	}
	public void clear() {
		this.collection.clear();
	}
	public boolean add(T item) {
		return this.collection.add(item);
	}
	public boolean sort(java.util.Comparator<T> c) {
		return this.collection.sort(c);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toString() {
		return this.collection.toString();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		return this.collection.toConfig();
	}
	public String output() {
		return this.collection.output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.collection.input(in);
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		return this.collection.input(c);
	}
	public void copyReference(Object o) {
		this.collection.copyReference(o);
	}
	public void copyValue(Object o) {
		this.collection.copyValue(o);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public T searchByCount(int count) {
		return this.collection.searchByCount(count);
	}
	public T fetchByCount(int count) {
		return this.collection.fetchByCount(count);
	}
	public com.FileManagerX.Interfaces.ICollection<T> searchesByCount(int bg, int ed) {
		return this.collection.searchesByCount(bg, ed);
	}
	public com.FileManagerX.Interfaces.ICollection<T> fetchesByCount(int bg, int ed) {
		return this.collection.searchesByCount(bg, ed);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T searchByKey(Object key) {
		return this.collection.searchByKey(key);
	}
	public T fetchByKey(Object key) {
		return this.collection.fetchByKey(key);
	}
	public com.FileManagerX.Interfaces.ICollection<T> searchesByKey(Object key) {
		return this.collection.searchesByKey(key);
	}
	public com.FileManagerX.Interfaces.ICollection<T> fetchesByKey(Object key) {
		return this.collection.fetchesByKey(key);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
