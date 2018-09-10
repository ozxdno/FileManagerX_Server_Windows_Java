package com.FileManagerX.BasicCollections;

public class Files extends BasicHashMap<com.FileManagerX.BasicModels.File, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.BasicModels.File item) {
		return item == null ? null : item.getIndex();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.File createT() {
		return new com.FileManagerX.BasicModels.File();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.File searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.File> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.File f = it.getNext();
			if(f.getIndex() == index) {
				return f;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.File fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.File> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.File f = it.getNext();
			if(f.getIndex() == index) {
				it.remove();
				return f;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.File searchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.File> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.File f = it.getNext();
			if(f.getUrl().equals(url)) {
				return f;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.File fetchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.File> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.File f = it.getNext();
			if(f.getUrl().equals(url)) {
				it.remove();
				return f;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
