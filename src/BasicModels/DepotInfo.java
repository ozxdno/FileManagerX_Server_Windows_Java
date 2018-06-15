package BasicModels;

public class DepotInfo implements Tools.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long index;
	private long dbIndex;
	private BasicModels.MachineInfo machineInfo;
	private String url; // 不包含IP/Port信息
	private BasicModels.DataBaseInfo dbInfo;
	private String name;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		return true;
	}
	public boolean setDBIndex(long dbIndex) {
		this.dbIndex = dbIndex;
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
		if(this.dbInfo == null) {
			this.dbInfo = new DataBaseInfo();
		}
		this.dbInfo.clear();
		name = "";
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
		String mUrl = this.machineInfo.getUrl();
		return "[" + name + "]: " + mUrl + "\\" + url;
	}
	public String output() {
		Config c = new Config("DataBaseInfo = ");
		c.addToBottom(index);
		c.addToBottom(new Config(this.machineInfo.output()));
		c.addToBottom(url);
		c.addToBottom(this.dbIndex);
		c.addToBottom(name);
		return c.output();
	}
	public String input(String in) {
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		in = this.machineInfo.input(c.output());
		if(in == null) { return null; }
		c.setValue(in);
		this.url = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.dbIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.name = c.fetchFirstString();
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
	}
	public void copyValue(Object o) {
		DepotInfo d = (DepotInfo)o;
		this.index = d.index;
		this.machineInfo.copyValue(d.machineInfo);;
		this.url = new String(d.url);
		this.dbIndex = d.dbIndex;
		this.dbInfo = d.dbInfo;
		this.name = new String(d.name);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isLocal() {
		return this.machineInfo.isLocal();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
