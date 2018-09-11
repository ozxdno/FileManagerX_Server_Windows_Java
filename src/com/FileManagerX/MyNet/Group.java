package com.FileManagerX.MyNet;

public class Group extends com.FileManagerX.BasicCollections.BasicCollection<User> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForName =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.MyNet.User) {
					com.FileManagerX.MyNet.User i = (com.FileManagerX.MyNet.User)item;
					return i.getName();
				}
				return null;
			}
		};
	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.MyNet.User) {
					com.FileManagerX.MyNet.User i = (com.FileManagerX.MyNet.User)item;
					return i.getUser().getIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String name;
	private long index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = ++com.FileManagerX.Globals.Configurations.Next_GroupIndex;
		return true;
	}
	public boolean setName(String name) {
		if(name == null) {
			return false;
		}
		if(name.length() == 0) {
			return false;
		}
		this.name = name;
		return true;
	}
	public boolean setLimit(int limit) {
		com.FileManagerX.BasicCollections.BasicLRUMap<User> map = 
				(com.FileManagerX.BasicCollections.BasicLRUMap<User>)this.getContent();
		return map.setLimit(limit);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return this.index;
	}
	public String getName() {
		return this.name;
	}
	public int getLimit() {
		com.FileManagerX.BasicCollections.BasicLRUMap<User> map = 
				(com.FileManagerX.BasicCollections.BasicLRUMap<User>)this.getContent();
		return map.getLimit();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Group() {
		initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicLRUMap<>());
		this.setSafe(true);
		this.setKey(KeyForName);
		this.name = "Default Group";
		this.setLimit(com.FileManagerX.Globals.Configurations.LimitForGroupUser);
	}
	public User createT() {
		return new User();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.name;
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField("Net-" + this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.name);
		c.addToBottom(this.getLimit());
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.name = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.setLimit(c.fetchFirstInt());
		if(!c.getIsOK()) { return c; }
		
		return c;
	}
	public void copyReference(Object o) {
		;
	}
	public void copyValue(Object o) {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public User searchByName(String name) {
		return this.searchByKey(name);
	}
	public User fetchByName(String name) {
		return this.fetchByKey(name);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void refresh() {
		com.FileManagerX.Interfaces.IIterator<User> it = this.getIterator();
		while(it.hasNext()) {
			it.getNext().refresh();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
