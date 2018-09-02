package com.FileManagerX.BasicCollections;

public class Configs extends BasicArrayList<com.FileManagerX.BasicModels.Config, String> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getKey(com.FileManagerX.BasicModels.Config item) {
		return item == null ? null : item.getField();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Config createT() {
		return new com.FileManagerX.BasicModels.Config();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicModels.Config searchByField(String field) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Config> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Config c = it.getNext();
			if(c.getField().equals(field)) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.Config fetchByField(String field) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Config> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Config c = it.getNext();
			if(c.getField().equals(field)) {
				it.remove();
				return c;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
