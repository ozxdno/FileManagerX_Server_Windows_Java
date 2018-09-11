package com.FileManagerX.BasicCollections;

public class Invitations extends BasicCollection<com.FileManagerX.BasicModels.Invitation> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForCode =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.BasicModels.Invitation) {
					com.FileManagerX.BasicModels.Invitation i = (com.FileManagerX.BasicModels.Invitation)item;
					return i.getCode();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Invitations() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(KeyForCode);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Invitation searchByEndTime(long endTime) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Invitation> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Invitation i = it.getNext();
			if(i.getEndTime() == endTime) {
				return i;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.Invitation fetchByEndTime(long endTime) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Invitation> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Invitation i = it.getNext();
			if(i.getEndTime() == endTime) {
				it.remove();
				return i;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Invitations searchesByBeforeEndTime(long endTime) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Invitation> it = this.getIterator();
		Invitations is = new Invitations();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Invitation i = it.getNext();
			if(i.getEndTime() < endTime) {
				is.add(i);
			}
		}
		return is;
	}
	public Invitations fetchesByBeforeEndTime(long endTime) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Invitation> it = this.getIterator();
		Invitations is = new Invitations();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Invitation i = it.getNext();
			if(i.getEndTime() < endTime) {
				it.remove();
				is.add(i);
			}
		}
		return is;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
