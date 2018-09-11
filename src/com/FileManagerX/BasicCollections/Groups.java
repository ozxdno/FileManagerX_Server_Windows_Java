package com.FileManagerX.BasicCollections;

public class Groups extends BasicCollection<com.FileManagerX.BasicModels.Group> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex = 
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.BasicModels.Group) {
					com.FileManagerX.BasicModels.Group i = (com.FileManagerX.BasicModels.Group)item;
					return i.getIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Groups() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(KeyForIndex);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Group searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Group> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Group g = it.getNext();
			if(g.getIndex() == index) {
				return g;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.Group fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Group> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Group g = it.getNext();
			if(g.getIndex() == index) {
				it.remove();
				return g;
			}
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
