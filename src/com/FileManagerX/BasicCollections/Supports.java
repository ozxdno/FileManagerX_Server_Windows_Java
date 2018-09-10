package com.FileManagerX.BasicCollections;

public class Supports extends BasicHashMap<com.FileManagerX.BasicModels.Support> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Supports() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(new KeyForExtension());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Support createT() {
		return new com.FileManagerX.BasicModels.Support();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Supports searchesByType(com.FileManagerX.BasicEnums.FileType type) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Support> it = this.getIterator();
		Supports supports = new Supports();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Support s = it.getNext();
			if(s.getType().equals(type)) {
				supports.add(s);
			}
		}
		return supports;
	}
	public Supports fetchesByType(com.FileManagerX.BasicEnums.FileType type) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Support> it = this.getIterator();
		Supports supports = new Supports();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Support s = it.getNext();
			if(s.getType().equals(type)) {
				it.remove();
				supports.add(s);
			}
		}
		return supports;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForExtension implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.BasicModels.Support) {
				com.FileManagerX.BasicModels.Support i = (com.FileManagerX.BasicModels.Support)item;
				return i.getExtension();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
