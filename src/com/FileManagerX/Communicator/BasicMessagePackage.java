package com.FileManagerX.Communicator;

public class BasicMessagePackage implements com.FileManagerX.Interfaces.IBasicMessagePackage {

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
	
	private long index;
	private long id;
	private int priority;
	
	private boolean isRecord;
	private boolean isDircet;
	private boolean isBroadCast;
	
	private long permitIdle;
	private long sendTime;
	private long receiveTime;
	private com.FileManagerX.Interfaces.IRoutePathPackage rrp;
	
	private static long nextIndex = 0;
	
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
		if(com.FileManagerX.Tools.Url.isIp(ip)) {
			this.ip1 = ip;
			return true;
		}
		return false;
	}
	public boolean setIp2(String ip) {
		if(com.FileManagerX.Tools.Url.isIp(ip)) {
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
		if(password == null) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_NULL.register();
			return false;
		}
		if(password.length() == 0) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_EMPTY.register();
			return false;
		}
		this.password = password;
		return true;
	}
	
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setId(long id) {
		this.id = id;
		return true;
	}
	public boolean setPriority(int priority) {
		if(priority < 0) {
			priority = 0;
		}
		this.priority = priority;
		return true;
	}
	public boolean setPriority(com.FileManagerX.BasicEnums.CMDPriority priority) {
		if(priority == null) {
			return false;
		}
		this.priority = priority.ordinal();
		return true;
	}
	
	public boolean setIsRecord(boolean isRecord) {
		this.isRecord = isRecord;
		return true;
	}
	public boolean setIsDirect(boolean isDirect) {
		this.isDircet = isDirect;
		return true;
	}
	public boolean setIsBroadCast(boolean isBroadCast) {
		this.isBroadCast = isBroadCast;
		return true;
	}
	
	public boolean setPermitIdle(long idle) {
		if(idle < 0) {
			idle = 0;
		}
		this.permitIdle = idle;
		return true;
	}
	public boolean setSendTime(long sendTime) {
		if(sendTime < 0) {
			sendTime = 0;
		}
		this.sendTime = sendTime;
		return true;
	}
	public boolean setSendTime() {
		this.sendTime = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setReceiveTime(long receiveTime) {
		if(receiveTime < 0) {
			receiveTime = 0;
		}
		this.receiveTime = receiveTime;
		return true;
	}
	public boolean setReceiveTime() {
		this.receiveTime = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setRoutPathPackage(com.FileManagerX.Interfaces.IRoutePathPackage rrp) {
		if(rrp == null) {
			return false;
		}
		this.rrp = rrp;
		return true;
	}
	public boolean setRoutePathPackage() {
		return this.rrp.setThis(this.sourMachineIndex, this.destMachineIndex);
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
	
	public long getIndex() {
		return this.index;
	}
	public long getId() {
		return this.id;
	}
	public int getPriority() {
		return this.priority;
	}
	public com.FileManagerX.BasicEnums.CMDPriority getPriorityEnum() {
		return com.FileManagerX.BasicEnums.CMDPriority.values()[this.priority];
	}
	
	public boolean isRecord() {
		return this.isRecord;
	}
	public boolean isDirect() {
		return this.isDircet;
	}
	public boolean isBroadCast() {
		return this.isBroadCast;
	}
	
	public long getPermitIdle() {
		return this.permitIdle;
	}
	public long getSendTime() {
		return this.sendTime;
	}
	public long getReceiveTime() {
		return this.receiveTime;
	}
	public com.FileManagerX.Interfaces.IRoutePathPackage getRoutePathPackage() {
		return this.rrp;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BasicMessagePackage() {
		initThis();
	}
	private void initThis() {
		this.sourMachineIndex = 0;
		this.destMachineIndex = 0;
		this.sourDepotIndex = 0;
		this.destDepotIndex = 0;
		this.sourDataBaseIndex = 0;
		this.destDataBaseIndex = 0;
		this.sourUserIndex = 0;
		this.destUserIndex = 0;
		
		this.ip1 = "";
		this.ip2 = "";
		this.port1 = 0;
		this.port2 = 0;
		
		this.password = "";
		
		BasicMessagePackage.nextIndex++;
		this.index = BasicMessagePackage.nextIndex;
		this.id = -1;
		this.priority = 10;
		
		this.isRecord = false;
		this.isDircet = false;
		this.isBroadCast = false;
		
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForCommandReceive;
		this.rrp = com.FileManagerX.Factories.CommunicatorFactory.createRRP();
		this.sendTime = com.FileManagerX.Tools.Time.getTicks();
		this.receiveTime = com.FileManagerX.Tools.Time.getTicks();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.Interfaces.IClientConnection connection) {
		
		try {
			boolean ok = true;
			ok &= this.setSourMachineIndex(connection.getClientMachineInfo().getIndex());
			ok &= this.setDestMachineIndex(connection.getServerMachineInfo().getIndex());
			ok &= this.setSourUserIndex(connection.getClientUser().getIndex());
			ok &= this.setDestUserIndex(connection.getServerUser().getIndex());
			ok &= this.setIp1(connection.getClientMachineInfo().getIp());
			ok &= this.setIp2(connection.getServerMachineInfo().getIp());
			ok &= this.setPort1(connection.getClientMachineInfo().getPort());
			ok &= this.setPort2(connection.getServerMachineInfo().getPort());
			ok &= this.setPassword(connection.getClientUser().getPassword());
			return ok;
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
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
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.id);
		c.addToBottom(this.priority);
		c.addToBottom(this.isRecord);
		c.addToBottom(this.isDircet);
		c.addToBottom(this.isBroadCast);
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
		c.addToBottom(this.permitIdle);
		c.addToBottom(this.sendTime);
		c.addToBottom(this.receiveTime);
		c.addToBottom(new com.FileManagerX.BasicModels.Config(this.rrp.output()));
		
		return c.output();
	}
	public String input(String in) {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.id = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.priority = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		this.isRecord = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.isDircet = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.isBroadCast = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
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
		this.permitIdle = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sendTime = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.receiveTime = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return this.rrp.input(c.output());
	}
	public void copyReference(Object o) {
		BasicMessagePackage bmp = (BasicMessagePackage)o;
		this.sourMachineIndex = bmp.sourMachineIndex;
		this.destMachineIndex = bmp.destMachineIndex;
		this.sourDepotIndex = bmp.destDepotIndex;
		this.destDepotIndex = bmp.destDepotIndex;
		this.sourDataBaseIndex = bmp.sourDataBaseIndex;
		this.destDataBaseIndex = bmp.destDataBaseIndex;
		this.sourUserIndex = bmp.sourUserIndex;
		this.destUserIndex = bmp.destUserIndex;
		
		this.ip1 = bmp.ip1;
		this.ip2 = bmp.ip2;
		this.port1 = bmp.port1;
		this.port2 = bmp.port2;
		this.password = bmp.password;
		
		this.index = bmp.index;
		this.id = bmp.id;
		this.priority = bmp.priority;
		
		this.isRecord = bmp.isRecord;
		this.isDircet = bmp.isDircet;
		this.isBroadCast = bmp.isBroadCast;
		
		this.permitIdle = bmp.permitIdle;
		this.sendTime = bmp.sendTime;
		this.receiveTime = bmp.receiveTime;
		this.rrp = bmp.rrp;
	}
	public void copyValue(Object o) {
		BasicMessagePackage bmp = (BasicMessagePackage)o;
		this.sourMachineIndex = bmp.sourMachineIndex;
		this.destMachineIndex = bmp.destMachineIndex;
		this.sourDepotIndex = bmp.destDepotIndex;
		this.destDepotIndex = bmp.destDepotIndex;
		this.sourDataBaseIndex = bmp.sourDataBaseIndex;
		this.destDataBaseIndex = bmp.destDataBaseIndex;
		this.sourUserIndex = bmp.sourUserIndex;
		this.destUserIndex = bmp.destUserIndex;
		
		this.ip1 = new String(bmp.ip1);
		this.ip2 = new String(bmp.ip2);
		this.port1 = bmp.port1;
		this.port2 = bmp.port2;
		this.password = new String(bmp.password);
		
		this.index = bmp.index;
		this.id = bmp.id;
		this.priority = bmp.priority;
		
		this.isRecord = bmp.isRecord;
		this.isDircet = bmp.isDircet;
		this.isBroadCast = bmp.isBroadCast;
		
		this.permitIdle = bmp.permitIdle;
		this.sendTime = bmp.sendTime;
		this.receiveTime = bmp.receiveTime;
		this.rrp.copyValue(bmp.rrp);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
