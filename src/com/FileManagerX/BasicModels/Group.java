package com.FileManagerX.BasicModels;

public class Group implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private com.FileManagerX.BasicCollections.Users users;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_GroupIndex();
		return true;
	}
	public boolean setUsers(com.FileManagerX.BasicCollections.Users users) {
		if(users == null) {
			return false;
		}
		this.users = users;
		return true;
	}
	
	public boolean setGroup(com.FileManagerX.MyNet.Group group) {
		if(group == null) {
			return false;
		}
		
		this.index = group.getIndex();
		this.users.clear();
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.MyNet.User> it = group.getIterator();
		while(it.hasNext()) {
			this.users.add(it.getNext().getUser());
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return index;
	}
	public com.FileManagerX.BasicCollections.Users getUsers() {
		return users;
	}
	
	public com.FileManagerX.MyNet.Group getGroup() {
		com.FileManagerX.MyNet.Group group = new com.FileManagerX.MyNet.Group();
		group.setIndex(index);
		com.FileManagerX.Interfaces.IIterator<User> it = this.users.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.MyNet.User u = new com.FileManagerX.MyNet.User();
			u.setUser(it.getNext());
			group.add(u);
		}
		return group;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Group() {
		initThis();
	}
	private void initThis() {
		index = 0;
		users = new com.FileManagerX.BasicCollections.Users();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public Config toConfig() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.users.toConfig());
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public Config input(String in) {
		return this.input(new Config(in));
	}
	public Config input(Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		c = this.users.input(c);
		if(!c.getIsOK()) { return c; }
		
		return c;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Group) {
			Group g = (Group)o;
			this.index = g.index;
			this.users = g.users;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Group) {
			Group g = (Group)o;
			this.index = g.index;
			this.users.copyValue(users);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
