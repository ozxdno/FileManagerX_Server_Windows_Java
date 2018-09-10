package com.FileManagerX.BasicCollections;

public class BasicCollection <T extends com.FileManagerX.Interfaces.IPublic>
	implements com.FileManagerX.Interfaces.ICollection<T> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.Interfaces.ICollection<T> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(com.FileManagerX.Interfaces.ICollection<T> content) {
		if(content == null) { return false; }
		this.content = content;
		return true;
	}
	public boolean setKey(IKey key) {
		return this.content.setKey(key);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IIterator<T> getIterator() {
		return this.content.getIterator();
	}
	public Object getKey(T e) {
		return this.content.getKey(e);
	}
	
	public com.FileManagerX.Interfaces.ICollection<T> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public T createT() {
		return null;
	}
	public int size() {
		return this.content.size();
	}
	public void clear() {
		this.content.clear();
	}
	public boolean add(T item) {
		return this.content.add(item);
	}
	public boolean sort(java.util.Comparator<T> c) {
		return this.content.sort(c);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toString() {
		return this.content.toString();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		return this.content.toConfig();
	}
	public String output() {
		return this.content.output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.content.input(in);
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		return this.content.input(c);
	}
	public void copyReference(Object o) {
		this.content.copyReference(o);
	}
	public void copyValue(Object o) {
		this.content.copyValue(o);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public T searchByCount(int count) {
		return this.content.searchByCount(count);
	}
	public T fetchByCount(int count) {
		return this.content.fetchByCount(count);
	}
	public com.FileManagerX.Interfaces.ICollection<T> searchesByCount(int bg, int ed) {
		return this.content.searchesByCount(bg, ed);
	}
	public com.FileManagerX.Interfaces.ICollection<T> fetchesByCount(int bg, int ed) {
		return this.content.searchesByCount(bg, ed);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T searchByKey(Object key) {
		return this.searchByKey(key);
	}
	public T fetchByKey(Object key) {
		return this.fetchByKey(key);
	}
	public com.FileManagerX.Interfaces.ICollection<T> searchesByKey(Object key) {
		return this.searchesByKey(key);
	}
	public com.FileManagerX.Interfaces.ICollection<T> fetchesByKey(Object key) {
		return this.fetchesByKey(key);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
