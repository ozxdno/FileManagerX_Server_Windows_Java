package com.FileManagerX.BasicCollections;

public class Folders extends BasicCollection<com.FileManagerX.BasicModels.Folder> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Folders() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(new KeyForIndex());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Folder createT() {
		return new com.FileManagerX.BasicModels.Folder();
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

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.BasicModels.Folder) {
				com.FileManagerX.BasicModels.Folder i = (com.FileManagerX.BasicModels.Folder)item;
				return i.getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
