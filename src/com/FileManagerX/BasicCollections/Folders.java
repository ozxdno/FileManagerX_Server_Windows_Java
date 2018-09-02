package com.FileManagerX.BasicCollections;

public class Folders extends BasicHashMap<com.FileManagerX.BasicModels.Folder, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.BasicModels.Folder item) {
		return item == null ? null : item.getIndex();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Folder createT() {
		return new com.FileManagerX.BasicModels.Folder();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public java.util.Comparator<com.FileManagerX.BasicModels.Folder> getACmpByIndex() {
		return new java.util.Comparator<com.FileManagerX.BasicModels.Folder>() {
			public int compare(com.FileManagerX.BasicModels.Folder e1, com.FileManagerX.BasicModels.Folder e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
	}
	public java.util.Comparator<com.FileManagerX.BasicModels.Folder> getDCmpByIndex() {
		return new java.util.Comparator<com.FileManagerX.BasicModels.Folder>() {
			public int compare(com.FileManagerX.BasicModels.Folder e1, com.FileManagerX.BasicModels.Folder e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Folder searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Folder> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Folder f = it.getNext();
			if(f.getIndex() == index) {
				return f;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.Folder fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Folder> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Folder f = it.getNext();
			if(f.getIndex() == index) {
				it.remove();
				return f;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Folder searchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Folder> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Folder f = it.getNext();
			if(f.getUrl().equals(url)) {
				return f;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.Folder fetchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Folder> it =
				this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Folder f = it.getNext();
			if(f.getUrl().equals(url)) {
				it.remove();
				return f;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
