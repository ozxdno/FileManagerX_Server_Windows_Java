package com.FileManagerX.BasicCollections;

public class DataBaseInfos extends BasicCollection<com.FileManagerX.BasicModels.DataBaseInfo> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DataBaseInfos() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(new KeyForIndex());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo createT() {
		return new com.FileManagerX.BasicModels.DataBaseInfo();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicModels.DataBaseInfo searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getIndex() == index) {
				return db;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DataBaseInfo fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getIndex() == index) {
				it.remove();
				return db;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo searchByDepotIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getDepotIndex() == index) {
				return db;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DataBaseInfo fetchByDepotIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getDepotIndex() == index) {
				it.remove();
				return db;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo searchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getUrl().equals(url)) {
				return db;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DataBaseInfo fetchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getUrl().equals(url)) {
				it.remove();
				return db;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo searchByName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getName().equals(name)) {
				return db;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DataBaseInfo fetchByName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			if(db.getName().equals(name)) {
				it.remove();
				return db;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.BasicModels.DataBaseInfo) {
				com.FileManagerX.BasicModels.DataBaseInfo i = (com.FileManagerX.BasicModels.DataBaseInfo)item;
				return i.getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
