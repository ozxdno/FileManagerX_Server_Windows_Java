package com.FileManagerX.BasicCollections;

public class Groups extends BasicHashMap<com.FileManagerX.BasicModels.Group, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.BasicModels.Group item) {
		return item == null ? null : item.getIndex();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Group createT() {
		return new com.FileManagerX.BasicModels.Group();
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
