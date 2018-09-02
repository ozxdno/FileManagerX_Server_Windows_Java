package com.FileManagerX.MyNet;

public class Net extends com.FileManagerX.BasicCollections.BasicHashMap<Group, String> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String name;
	private int limit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		if(limit < 0) {
			return false;
		}
		this.limit = limit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getName() {
		return this.name;
	}
	public int getLimit() {
		return this.limit;
	}

	public String getKey(Group item) {
		return item == null ? null : item.getName();
	}
	
	public Manager getManager() {
		Manager m = new Manager();
		m.setNet(this);
		return m;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Net() {
		initThis();
	}
	private void initThis() {
		this.name = "Default Net";
		
		com.FileManagerX.Factories.MyNetFactory.createServerGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createMyGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createTempGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createHideGroup(this);
	}
	public Group createT() {
		return new Group();
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
		c.addToBottom(this.name);
		c.addToBottom(this.limit);
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
		this.name = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.limit = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		
		return c;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof Net) {
			super.copyReference(o);
			Net n = (Net)o;
			this.name = n.name;
			this.limit = n.limit;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof Net) {
			super.copyValue(o);
			Net n = (Net)o;
			this.name = new String(n.name);
			this.limit = n.limit;
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Group searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<Group> it = this.getIterator();
		while(it.hasNext()) {
			Group g = it.getNext();
			if(g.getIndex() == index) {
				return g;
			}
		}
		return null;
	}
	public Group fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<Group> it = this.getIterator();
		while(it.hasNext()) {
			Group g = it.getNext();
			if(g.getIndex() == index) {
				it.remove();
				return g;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Group searchByName(String name) {
		return this.searchByKey(name);
	}
	public Group fetchByName(String name) {
		return this.fetchByKey(name);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void refresh() {
		com.FileManagerX.Factories.MyNetFactory.createServerGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createMyGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createTempGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createHideGroup(this);
		
		com.FileManagerX.Interfaces.IIterator<Group> it = this.getIterator();
		while(it.hasNext()) {
			it.getNext().refresh();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
