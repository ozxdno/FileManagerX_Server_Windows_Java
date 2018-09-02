package com.FileManagerX.MyNet;

public class Depot implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DepotInfo depot;
	private String name;
	private Net permit;
	private int limit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDepot(com.FileManagerX.BasicModels.DepotInfo depot) {
		if(depot == null) {
			return false;
		}
		this.depot = depot;
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
		if(limit < 0) {
			return false;
		}
		this.limit = limit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DepotInfo getDepot() {
		return this.depot;
	}
	public String getName() {
		return this.name;
	}
	public Net getPermit() {
		return this.permit;
	}
	public int getLimit() {
		return this.limit;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Depot() {
		initThis();
	}
	private void initThis() {
		this.depot = new com.FileManagerX.BasicModels.DepotInfo();
		this.name = "";
		this.permit = new Net();
		this.permit.setName("Default Depot");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.depot.getName();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField("Net-" + this.getClass().getSimpleName());
		c.addToBottom(this.name);
		c.addToBottom(this.depot.getIndex());
		c.addToBottom(this.limit);
		c.addToBottom(this.permit.size());
		com.FileManagerX.Interfaces.IIterator<Group> it = this.permit.getIterator();
		while(it.hasNext()) {
			c.addToBottom(it.getNext().getName());
		}
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
		this.depot.setIndex(c.fetchFirstLong());
		if(!c.getIsOK()) { return c; }
		this.limit = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		int amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		for(int i=0; i<amount; i++) {
			Group g = new Group();
			g.setName(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.permit.add(g);
		}
		return c;
	}
	public void copyReference(Object o) {
		
	}
	public void copyValue(Object o) {
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean load() {
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			return com.FileManagerX.Globals.Datas.DBManager.query(
					"[&] Index = " + this.depot.getIndex(),
					this.depot,
					com.FileManagerX.DataBase.Unit.Depot
				);
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(
					com.FileManagerX.DataBase.Unit.Depot,
					"[&] Index = " + this.depot.getIndex(),
					com.FileManagerX.Globals.Datas.ServerConnection
					);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			if(rep == null || !rep.isOK()) { return false; }
			this.depot = (com.FileManagerX.BasicModels.DepotInfo)rep.getResult();
			return true;
		}
	}
	public void refresh() {
		this.load();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
