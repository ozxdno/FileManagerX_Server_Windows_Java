package com.FileManagerX.BasicCollections;

public class Supports extends BasicHashMap<com.FileManagerX.BasicModels.Support> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForExtension =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.BasicModels.Support) {
					com.FileManagerX.BasicModels.Support i = (com.FileManagerX.BasicModels.Support)item;
					return i.getExtension();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Supports() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(KeyForExtension);
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
}
