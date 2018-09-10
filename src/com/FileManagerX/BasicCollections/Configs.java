package com.FileManagerX.BasicCollections;

public class Configs extends BasicCollection<com.FileManagerX.BasicModels.Config> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Configs() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicLinkedList<>());
		this.setKey(new KeyForField());
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

	public static class KeyForField implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.BasicModels.Config) {
				com.FileManagerX.BasicModels.Config i = (com.FileManagerX.BasicModels.Config)item;
				return i.getField();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
