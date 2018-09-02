package com.FileManagerX.BasicModels;

public class DepotInfo implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long index;
	private long dbIndex;
	private com.FileManagerX.BasicModels.MachineInfo machineInfo;
	private String url;
	private com.FileManagerX.BasicModels.DataBaseInfo dbInfo;
	private String name;
	private com.FileManagerX.BasicEnums.DepotState state;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_DepotIndex();
		return true;
	}
	public boolean setDBIndex(long dbIndex) {
		this.dbIndex = dbIndex;
		return true;
	}
	public boolean setDBIndex() {
		if(this.dbInfo == null) {
			return false;
		}
		this.dbIndex = this.dbInfo.getIndex();
		return true;
	}
	public boolean setMachineInfo(MachineInfo machineInfo) {
		if(machineInfo == null) {
			return false;
		}
		this.machineInfo = machineInfo;
		return true;
	}
	public boolean setUrl(String url) {
		if(url == null) {
			return false;
		}
		this.url = url;
		return true;
	}
	public boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo dbInfo) {
		if(dbInfo == null) {
			return false;
		}
		this.dbInfo = dbInfo;
		return true;
	}
	public boolean setName(String name) {
		if(name == null) {
			return false;
		}
		this.name = name;
		return true;
	}
	public boolean setState(com.FileManagerX.BasicEnums.DepotState state) {
		if(state == null) {
			return false;
		}
		this.state = state;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return this.index;
	}
	public long getDBIndex() {
		return this.dbIndex;
	}
	public MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	public String getUrl() {
		return this.url;
	}
	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.dbInfo;
	}
	public String getName() {
		return this.name;
	}
	public com.FileManagerX.BasicEnums.DepotState getState() {
		return this.state;
	}
	
	public com.FileManagerX.Interfaces.IDepotManager getManager() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setDepot(this);
		return dm;
	}
	public com.FileManagerX.Interfaces.IDepotChecker getChecker() {
		com.FileManagerX.Interfaces.IDepotChecker dc = com.FileManagerX.Factories.DepotFactory.createChecker();
		return dc;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public DepotInfo() {
		initThis();
	}
	private void initThis() {
		index = -1;
		dbIndex = -1;
		if(this.machineInfo == null) {
			this.machineInfo = new MachineInfo();
		}
		this.machineInfo.clear();
		url = "";
		this.dbInfo = null;
		name = "";
		state = com.FileManagerX.BasicEnums.DepotState.Running;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		String name = this.name;
		if(name.length() == 0) {
			name = "No Name";
		}
		return "[" + name + "] " + url;
	}
	public Config toConfig() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.machineInfo.toConfig());
		c.addToBottom(this.url);
		c.addToBottom(this.dbIndex);
		c.addToBottom(this.name);
		c.addToBottom(this.state.toString());
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
		
		try {
			if(!c.getIsOK()) { return c; }
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			c = this.machineInfo.input(c);
			if(!c.getIsOK()) { return c; }
			this.url = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.dbIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.name = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.state = com.FileManagerX.BasicEnums.DepotState.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof DepotInfo) {
			DepotInfo d = (DepotInfo)o;
			this.index = d.index;
			this.machineInfo = d.machineInfo;
			this.url = d.url;
			this.dbIndex = d.dbIndex;
			this.dbInfo = d.dbInfo;
			this.name = d.name;
			this.state = d.state;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof DepotInfo) {
			DepotInfo d = (DepotInfo)o;
			this.index = d.index;
			this.machineInfo.copyValue(d.machineInfo);;
			this.url = new String(d.url);
			this.dbIndex = d.dbIndex;
			this.dbInfo = d.dbInfo;
			this.name = new String(d.name);
			this.state = d.state;
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
