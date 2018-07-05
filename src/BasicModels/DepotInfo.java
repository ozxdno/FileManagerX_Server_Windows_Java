package BasicModels;

public class DepotInfo implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long index;
	private long dbIndex;
	private BasicModels.MachineInfo machineInfo;
	private String url; // TXT: files exist at
						// SQL: files exist at
	private BasicModels.DataBaseInfo dbInfo;
	private String name;
	private BasicEnums.DepotState state;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = Globals.Configurations.Next_DepotIndex + 1;
		Globals.Configurations.Next_DepotIndex = this.index;
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
	public boolean setDBInfo(BasicModels.DataBaseInfo dbInfo) {
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
	public boolean setState(BasicEnums.DepotState state) {
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
	public BasicModels.DataBaseInfo getDBInfo() {
		return this.dbInfo;
	}
	public String getName() {
		return this.name;
	}
	public BasicEnums.DepotState getState() {
		return this.state;
	}
	
	public Interfaces.IDepotManager getManager() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setDepot(this);
		return dm;
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
		state = BasicEnums.DepotState.Running;
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
	public String output() {
		Config c = new Config("DataBaseInfo = ");
		c.addToBottom(index);
		c.addToBottom(new Config(this.machineInfo.output()));
		c.addToBottom(url);
		c.addToBottom(this.dbIndex);
		c.addToBottom(name);
		c.addToBottom(state.toString());
		return c.output();
	}
	public String input(String in) {
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		in = this.machineInfo.input(c.output());
		if(in == null) { return null; }
		c.setLine(in);
		this.url = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.dbIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.name = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.state = BasicEnums.DepotState.valueOf(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
		DepotInfo d = (DepotInfo)o;
		this.index = d.index;
		this.machineInfo = d.machineInfo;
		this.url = d.url;
		this.dbIndex = d.dbIndex;
		this.dbInfo = d.dbInfo;
		this.name = d.name;
		this.state = d.state;
	}
	public void copyValue(Object o) {
		DepotInfo d = (DepotInfo)o;
		this.index = d.index;
		this.machineInfo.copyValue(d.machineInfo);;
		this.url = new String(d.url);
		this.dbIndex = d.dbIndex;
		this.dbInfo = d.dbInfo;
		this.name = new String(d.name);
		this.state = d.state;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isLocal() {
		return this.machineInfo.isLocal();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
