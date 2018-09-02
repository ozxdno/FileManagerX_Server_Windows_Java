package com.FileManagerX.BasicCollections;

public class DataBaseInfos extends BasicHashMap<com.FileManagerX.BasicModels.DataBaseInfo, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.BasicModels.DataBaseInfo item) {
		return item == null ? null : item.getIndex();
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
}
