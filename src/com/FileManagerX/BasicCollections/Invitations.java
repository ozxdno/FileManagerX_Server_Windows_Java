package com.FileManagerX.BasicCollections;

public class Invitations extends BasicHashMap<com.FileManagerX.BasicModels.Invitation, String> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getKey(com.FileManagerX.BasicModels.Invitation item) {
		return item == null ? null : item.getCode();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Invitation createT() {
		return new com.FileManagerX.BasicModels.Invitation();
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
