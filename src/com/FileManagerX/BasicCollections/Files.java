package com.FileManagerX.BasicCollections;

public class Files extends BasicCollection<com.FileManagerX.BasicModels.File> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.BasicModels.File) {
					com.FileManagerX.BasicModels.File i = (com.FileManagerX.BasicModels.File)item;
					return i.getIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Files() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(KeyForIndex);
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
