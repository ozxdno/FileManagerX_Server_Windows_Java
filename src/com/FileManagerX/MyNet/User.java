package com.FileManagerX.MyNet;

public class User extends com.FileManagerX.BasicCollections.BasicCollection<Machine> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.User user;
	private String name;
	private Net permit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUser(com.FileManagerX.BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
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
	public boolean setPermit(Net permit) {
		if(permit == null) {
			return false;
		}
		this.permit = permit;
		return true;
	}
	public boolean setLimit(int limit) {
		com.FileManagerX.Safe.BasicCollections.BasicLRUMap<Machine> map = 
				(com.FileManagerX.Safe.BasicCollections.BasicLRUMap<Machine>)this.getContent();
		return map.setLimit(limit);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.User getUser() {
		return this.user;
	}
	public String getName() {
		return this.name;
	}
	public Net getPermit() {
		return this.permit;
	}
	public int getLimit() {
		com.FileManagerX.Safe.BasicCollections.BasicLRUMap<Machine> map = 
				(com.FileManagerX.Safe.BasicCollections.BasicLRUMap<Machine>)this.getContent();
		return map.getLimit();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public User() {
		initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.Safe.BasicCollections.BasicLRUMap<>());
		this.setKey(new KeyForName());
		this.user = new com.FileManagerX.BasicModels.User();
		this.name = "Default User";
		this.permit = new Net();
		this.permit.setName("Permit Groups");
	}
	public Machine createT() {
		return new Machine();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.user.getNickName();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField("Net-" + this.getClass().getSimpleName());
		c.addToBottom(this.user.getIndex());
		c.addToBottom(this.name);
		c.addToBottom(this.getLimit());
		c.addToBottom(this.permit.size());
		com.FileManagerX.Interfaces.IIterator<Group> it = this.permit.getIterator();
		while(it.hasNext()) {
			c.addToBottom_Encode(it.getNext().getName());
		}
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
		this.user.setIndex(c.fetchFirstLong());
		if(!c.getIsOK()) { return c; }
		this.name = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.setLimit(c.fetchFirstInt());
		if(!c.getIsOK()) { return c; }
		this.permit.clear();
		int amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		for(int i=0; i<amount; i++) {
			Group g = new Group();
			g.setName(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.permit.add(g);
		}
		this.setLimit(c.fetchFirstInt());
		if(!c.getIsOK()) { return c; }
		
		return c;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof User) {
			super.copyReference(o);
			User u = (User)o;
			this.user = u.user;
			this.name = u.name;
			this.permit = u.permit;
			this.setLimit(u.getLimit());
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof User) {
			super.copyValue(o);
			User u = (User)o;
			this.user = u.user;
			this.name = u.name;
			this.permit = u.permit;
			this.setLimit(u.getLimit());
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean load() {
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			return com.FileManagerX.Globals.Datas.DBManager.query(
					"[&] Index = " + this.user.getIndex(),
					this.user,
					com.FileManagerX.DataBase.Unit.User);
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(
					com.FileManagerX.DataBase.Unit.User,
					"[&] Index = " + this.user.getIndex()
				);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			if(rep == null || !rep.isOK()) { return false; }
			this.user = (com.FileManagerX.BasicModels.User)rep.getResult();
			return true;
		}
	}
	public void refresh() {
		this.load();
		com.FileManagerX.Interfaces.IIterator<Machine> it = this.getIterator();
		while(it.hasNext()) {
			it.getNext().refresh();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForName implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.MyNet.Machine) {
				com.FileManagerX.MyNet.Machine i = (com.FileManagerX.MyNet.Machine)item;
				return i.getName();
			}
			return null;
		}
	}
	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.MyNet.Machine) {
				com.FileManagerX.MyNet.Machine i = (com.FileManagerX.MyNet.Machine)item;
				return i.getMachine().getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
