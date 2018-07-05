package Communicator;

public class BasicMessagePackage implements Interfaces.IBasicMessagePackage {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long sourMachineIndex;
	private long destMachineIndex;
	private long sourDepotIndex;
	private long destDepotIndex;
	private long sourDataBaseIndex;
	private long destDataBaseIndex;
	private long sourUserIndex;
	private long destUserIndex;
	
	private String ip1;
	private String ip2;
	private int port1;
	private int port2;
	
	private String password;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setSourMachineIndex(long index) {
		this.sourMachineIndex = index;
		return true;
	}
	public boolean setDestMachineIndex(long index) {
		this.destMachineIndex = index;
		return true;
	}
	public boolean setSourDepotIndex(long index) {
		this.sourDepotIndex = index;
		return true;
	}
	public boolean setDestDepotIndex(long index) {
		this.destDepotIndex = index;
		return true;
	}
	public boolean setSourDataBaseIndex(long index) {
		this.sourDataBaseIndex = index;
		return true;
	}
	public boolean setDestDataBaseIndex(long index) {
		this.destDataBaseIndex = index;
		return true;
	}
	public boolean setSourUserIndex(long index) {
		this.sourUserIndex = index;
		return true;
	}
	public boolean setDestUserIndex(long index) {
		this.destUserIndex = index;
		return true;
	}
	
	public boolean setIp1(String ip) {
		if(Tools.Url.isIp(ip)) {
			this.ip1 = ip;
			return true;
		}
		return false;
	}
	public boolean setIp2(String ip) {
		if(Tools.Url.isIp(ip)) {
			this.ip2 = ip;
			return true;
		}
		return false;
	}
	public boolean setPort1(int port) {
		this.port1 = port;
		return true;
	}
	public boolean setPort2(int port) {
		this.port2 = port;
		return true;
	}
	
	public boolean setPassword(String password) {
		if(password == null || password.length() == 0) {
			return false;
		}
		this.password = password;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getSourMachineIndex() {
		return this.sourMachineIndex;
	}
	public long getDestMachineIndex() {
		return this.destMachineIndex;
	}
	public long getSourDepotIndex() {
		return this.sourDepotIndex;
	}
	public long getDestDepotIndex() {
		return this.destDepotIndex;
	}
	public long getSourDataBaseIndex() {
		return this.sourDataBaseIndex;
	}
	public long getDestDataBaseIndex() {
		return this.destDataBaseIndex;
	}
	public long getSourUserIndex() {
		return this.sourUserIndex;
	}
	public long getDestUserIndex() {
		return this.destUserIndex;
	}
	
	public String getIp1() {
		return this.ip1;
	}
	public String getIp2() {
		return this.ip2;
	}
	public int getPort1() {
		return this.port1;
	}
	public int getPort2() {
		return this.port2;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BasicMessagePackage() {
		initThis();
	}
	private void initThis() {
		this.sourMachineIndex = Globals.Configurations.This_MachineIndex;
		this.destMachineIndex = Globals.Configurations.Server_MachineIndex;
		this.sourDepotIndex = 0;
		this.destDepotIndex = 0;
		this.sourDataBaseIndex = 0;
		this.destDataBaseIndex = 0;
		this.sourUserIndex = Globals.Configurations.This_UserIndex;
		this.destUserIndex = Globals.Configurations.Server_UserIndex;
		
		this.ip1 = Globals.Datas.ThisMachine.getIp();
		this.ip2 = Globals.Datas.ServerMachine.getIp();
		this.port1 = Globals.Datas.ThisMachine.getPort();
		this.port2 = Globals.Datas.ServerMachine.getPort();
		
		this.password = Globals.Datas.ThisUser.getPassword();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "[Machine]: " + this.sourMachineIndex + "->" + this.destMachineIndex + 
			  ", [Address]: " + this.ip1 + ":" + this.port1 + "->" + this.ip2 + ":" + this.port2;
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.sourMachineIndex);
		c.addToBottom(this.destMachineIndex);
		c.addToBottom(this.sourDepotIndex);
		c.addToBottom(this.destDepotIndex);
		c.addToBottom(this.sourDataBaseIndex);
		c.addToBottom(this.destDataBaseIndex);
		c.addToBottom(this.sourUserIndex);
		c.addToBottom(this.destUserIndex);
		c.addToBottom(this.ip1);
		c.addToBottom(this.ip2);
		c.addToBottom(this.port1);
		c.addToBottom(this.port2);
		c.addToBottom(this.password);
		
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		this.sourMachineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.destMachineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sourDepotIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.destDepotIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sourDataBaseIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.destDataBaseIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sourUserIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.destUserIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.ip1 = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.ip2 = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.port1 = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		this.port2 = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		this.password = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		BasicMessagePackage bmp = (BasicMessagePackage)o;
		this.sourMachineIndex = bmp.sourMachineIndex;
		this.destMachineIndex = bmp.destMachineIndex;
		this.sourDepotIndex = bmp.destDepotIndex;
		this.destDepotIndex = bmp.destDepotIndex;
		this.sourDataBaseIndex = bmp.sourDataBaseIndex;
		this.destDataBaseIndex = bmp.destDataBaseIndex;
		this.ip1 = bmp.ip1;
		this.ip2 = bmp.ip2;
		this.port1 = bmp.port1;
		this.port2 = bmp.port2;
		this.password = bmp.password;
	}
	public void copyValue(Object o) {
		BasicMessagePackage bmp = (BasicMessagePackage)o;
		this.sourMachineIndex = bmp.sourMachineIndex;
		this.destMachineIndex = bmp.destMachineIndex;
		this.sourDepotIndex = bmp.destDepotIndex;
		this.destDepotIndex = bmp.destDepotIndex;
		this.sourDataBaseIndex = bmp.sourDataBaseIndex;
		this.destDataBaseIndex = bmp.destDataBaseIndex;
		this.ip1 = new String(bmp.ip1);
		this.ip2 = new String(bmp.ip2);
		this.port1 = bmp.port1;
		this.port2 = bmp.port2;
		this.password = new String(bmp.password);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
