package com.FileManagerX.MyNet;

public class Machine extends com.FileManagerX.Safe.BasicCollections.BasicLRUMap<Depot, String> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IRoutePathPackage rpp;
	private com.FileManagerX.BasicModels.MachineInfo machine;
	private String name;
	private Net permit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setMachine(com.FileManagerX.BasicModels.MachineInfo machine) {
		if(machine == null) {
			return false;
		}
		this.machine = machine;
		return true;
	}
	public boolean setRoutePathPackage(com.FileManagerX.Interfaces.IRoutePathPackage rpp) {
		if(rpp == null) {
			return false;
		}
		this.rpp = rpp;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicModels.MachineInfo getMachine() {
		return this.machine;
	}
	public com.FileManagerX.Interfaces.IRoutePathPackage getRoutePathPackage() {
		return this.rpp;
	}
	public String getName() {
		return this.name;
	}
	public Net getPermit() {
		return this.permit;
	}
	
	public String getKey(Depot item) {
		return item == null ? null : item.getName();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Machine() {
		initThis();
	}
	private void initThis() {
		this.machine = new com.FileManagerX.BasicModels.MachineInfo();
		this.rpp = com.FileManagerX.Factories.CommunicatorFactory.createRRP();
		this.name = "Default Machine";
		this.permit = new Net();
		this.permit.setName("Permit Groups");
	}
	public Depot createT() {
		return new Depot();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.machine.getName();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField("Net-" + this.getClass().getSimpleName());
		c.addToBottom(this.name);
		c.addToBottom(this.machine.getIndex());
		c.addToBottom(this.rpp.toConfig());
		c.addToBottom(this.getLimit());
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
		this.machine.setIndex(c.fetchFirstLong());
		if(!c.getIsOK()) { return c; }
		c = this.rpp.input(c);
		if(!c.getIsOK()) { return c; }
		this.setLimit(c.fetchFirstInt());
		if(!c.getIsOK()) { return c; }
		int amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		for(int i=0; i<amount; i++) {
			Group g = new Group();
			g.setName(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
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
					"[&] Index = " + this.machine.getIndex(),
					this.machine,
					com.FileManagerX.DataBase.Unit.Depot
				);
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(
					com.FileManagerX.DataBase.Unit.Machine,
					"[&] Index = " + this.machine.getIndex()
					);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			if(rep == null || !rep.isOK()) { return false; }
			this.machine = (com.FileManagerX.BasicModels.MachineInfo)rep.getResult();
			return true;
		}
	}
	public void refresh() {
		this.load();
		com.FileManagerX.Interfaces.IIterator<Depot> it = this.getIterator();
		while(it.hasNext()) {
			it.getNext().refresh();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
