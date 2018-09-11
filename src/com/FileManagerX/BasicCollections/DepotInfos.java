package com.FileManagerX.BasicCollections;

public class DepotInfos extends BasicCollection<com.FileManagerX.BasicModels.DepotInfo> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.BasicModels.DepotInfo) {
					com.FileManagerX.BasicModels.DepotInfo i = (com.FileManagerX.BasicModels.DepotInfo)item;
					return i.getIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DepotInfos() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(KeyForIndex);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicModels.DepotInfo searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getIndex() == index) {
				return d;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DepotInfo fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getIndex() == index) {
				it.remove();
				return d;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DepotInfo searchByDataBaseIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getDBIndex() == index) {
				return d;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DepotInfo fetchByDataBaseIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getDBIndex() == index) {
				it.remove();
				return d;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DepotInfo searchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getUrl().equals(url)) {
				return d;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DepotInfo fetchByUrl(String url) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getUrl().equals(url)) {
				it.remove();
				return d;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DepotInfo searchByName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getName().equals(name)) {
				return d;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.DepotInfo fetchByName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DepotInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DepotInfo d = it.getNext();
			if(d.getName().equals(name)) {
				it.remove();
				return d;
			}
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
