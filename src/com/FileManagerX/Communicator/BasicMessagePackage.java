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
	
	private com.FileManagerX.Deliver.Broadcast broadcast;
	
	private long permitIdle;
	private long sendTime;
	private long receiveTime;
	private com.FileManagerX.Interfaces.IRoutePathPackage rpp;
	
	private static long nextIndex = 0;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setSourMachineIndex(long index) {
		this.rpp.setSourMachine(index);
		this.sourMachineIndex = index;
		return true;
	}
	public boolean setDestMachineIndex(long index) {
		this.rpp.setDestMachine(index);
		this.broadcast.setDestMachine(index);
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
	public boolean setBroadCast(com.FileManagerX.Deliver.Broadcast broadcast) {
		if(broadcast == null) {
			return false;
		}
		this.broadcast = broadcast;
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
	public boolean setRoutPathPackage(com.FileManagerX.Interfaces.IRoutePathPackage rpp) {
		if(rpp == null) {
			return false;
		}
		this.rpp = rpp;
		return true;
	}
	public boolean setRoutePathPackage() {
		return this.rpp.setThis(this.sourMachineIndex, this.destMachineIndex);
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
	public com.FileManagerX.Deliver.Broadcast getBroadcast() {
		return this.broadcast;
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
		return this.rpp;
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
		this.broadcast = new com.FileManagerX.Deliver.Broadcast();
		
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Transport;
		this.rpp = com.FileManagerX.Factories.CommunicatorFactory.createRRP();
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
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.id);
		c.addToBottom(this.priority);
		c.addToBottom(this.isRecord);
		c.addToBottom(this.isDircet);
		c.addToBottom(this.broadcast.toConfig());
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
		c.addToBottom(this.rpp.toConfig());
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
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.id = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.priority = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		this.isRecord = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return c; }
		this.isDircet = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return c; }
		c = this.broadcast.input(c);
		if(!c.getIsOK()) { return c; }
		this.sourMachineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.destMachineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.sourDepotIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.destDepotIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.sourDataBaseIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.destDataBaseIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.sourUserIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.destUserIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.ip1 = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.ip2 = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.port1 = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		this.port2 = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		this.password = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.permitIdle = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.sendTime = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		this.receiveTime = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		c = this.rpp.input(c);
		if(!c.getIsOK()) { return c; }
		return c;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof BasicMessagePackage) {
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
			this.broadcast.copyReference(bmp.broadcast);
			
			this.permitIdle = bmp.permitIdle;
			this.sendTime = bmp.sendTime;
			this.receiveTime = bmp.receiveTime;
			this.rpp = bmp.rpp;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof BasicMessagePackage) {
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
			this.broadcast.copyValue(bmp.broadcast);
			
			this.permitIdle = bmp.permitIdle;
			this.sendTime = bmp.sendTime;
			this.receiveTime = bmp.receiveTime;
			this.rpp.copyValue(bmp.rpp);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
