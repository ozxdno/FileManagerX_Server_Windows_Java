package com.FileManagerX.BasicCollections;

public class Users extends BasicCollection<com.FileManagerX.BasicModels.User> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Users() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(new KeyForIndex());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.User createT() {
		return new com.FileManagerX.BasicModels.User();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicModels.User searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User u = it.getNext();
			if(u.getIndex() == index) {
				return u;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.User fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User d = it.getNext();
			if(d.getIndex() == index) {
				it.remove();
				return d;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.User searchByLoginName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User u = it.getNext();
			if(u.getLoginName().equals(name)) {
				return u;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.User fetchByLoginName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User u = it.getNext();
			if(u.getLoginName().equals(name)) {
				it.remove();
				return u;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.User searchByNickName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User u = it.getNext();
			if(u.getNickName().equals(name)) {
				return u;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.User fetchByNickName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User u = it.getNext();
			if(u.getNickName().equals(name)) {
				it.remove();
				return u;
			}
		}
		return null;
	}
	public Users searchesByNickName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		Users users = new Users();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User u = it.getNext();
			if(u.getNickName().equals(name)) {
				users.add(u);
			}
		}
		return users;
	}
	public Users fetchesByNickName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it = this.getIterator();
		Users users = new Users();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.User u = it.getNext();
			if(u.getNickName().equals(name)) {
				it.remove();
				users.add(u);
			}
		}
		return users;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.BasicModels.User) {
				com.FileManagerX.BasicModels.User i = (com.FileManagerX.BasicModels.User)item;
				return i.getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
