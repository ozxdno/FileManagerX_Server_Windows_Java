package com.FileManagerX.BasicModels;

public class MachineInfo implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private long userIndex;
	private String name;
	private String mac;
	private String ip;
	private int port;
	private String path;
	
	private com.FileManagerX.BasicEnums.MachineType type;
	private com.FileManagerX.BasicEnums.MachineState state;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getIndex() {
		return index;
	}
	public long getUserIndex() {
		return userIndex;
	}
	public String getName() {
		return name;
	}
	public String getMac() {
		return mac;
	}
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public String getUrl() {
		return ip + ":" + String.valueOf(port);
	}
	public String getPath() {
		return this.path;
	}
	
	public com.FileManagerX.BasicEnums.MachineType getType() {
		return type;
	}
	public com.FileManagerX.BasicEnums.MachineState getState() {
		return state;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_MachineIndex();
		return true;
	}
	public boolean setUserIndex(long index) {
		this.userIndex = index;
		return true;
	}
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	public boolean setName() {
		try {
			this.name = java.net.InetAddress.getLocalHost().getHostName();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean setMac(String mac) {
		if(mac == null) {
			return false;
		}
		this.mac = mac;
		return false;
	}
	public boolean setMac(byte[] macAddress) {
		if(macAddress == null) {
			return false;
		}
		if(macAddress.length == 0) {
			return false;
		}
		this.mac = Integer.toHexString(macAddress[0] & 0xFF);
		for(int i=1; i<macAddress.length; i++) {
			this.mac += '-' + Integer.toHexString(macAddress[i] & 0xFF);
		}
		this.mac = this.mac.toUpperCase();
		return true;
	}
	public boolean setMac() {
		try {
			byte[] macAddress = java.net.NetworkInterface.getByInetAddress(java.net.InetAddress.getLocalHost()).
					getHardwareAddress();
			this.setMac(macAddress);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean setIp(String ip) {
		if(com.FileManagerX.Tools.Url.isIp(ip)) {
			this.ip = ip;
			return true;
		}
		return false;
	}
	public boolean setIp() {
		try {
			java.net.InetAddress adr = java.net.InetAddress.getLocalHost();
			this.ip = adr.getHostAddress().toString();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean setPort(int port) {
		if(0 <= port && port < 65536) {
			this.port = port;
			return true;
		}
		return false;
	}
	public boolean setPort() {
		this.port = 40000;
		return true;
	}
	public boolean setPath(String path) {
		if(path == null) {
			return false;
		}
		this.path = path;
		return true;
	}
	
	public boolean setType(com.FileManagerX.BasicEnums.MachineType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setState(com.FileManagerX.BasicEnums.MachineState state) {
		if(state == null) {
			return false;
		}
		this.state = state;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public MachineInfo() {
		initThis();
	}
	public MachineInfo(String url) {
		initThis();
		setIp(com.FileManagerX.Tools.Url.getIp(url));
		setPort(com.FileManagerX.Tools.Url.getPort(url));
	}
	public MachineInfo(int port) {
		initThis();
		try {
			java.net.InetAddress adr = java.net.InetAddress.getLocalHost();
			this.ip = adr.getHostAddress().toString();
			this.name = adr.getHostName().toString();
			this.setPort(port);
		} catch(Exception e) {
			;
		}
	}
	public MachineInfo(String ip, int port) {
		initThis();
		setIp(ip);
		setPort(port);
	}
	private void initThis() {
		index = -1;
		userIndex = com.FileManagerX.Globals.Configurations.This_UserIndex;
		mac = "";
		name = "";
		ip = "";
		port = -1;
		path = "";
		type = com.FileManagerX.BasicEnums.MachineType.TEMPORARY;
		state = com.FileManagerX.BasicEnums.MachineState.RUNNING;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		String name = this.name;
		if(name == null || name.length() == 0) {
			name = "No Name";
		}
		String ip = this.ip;
		if(ip == null || ip.length() == 0) {
			ip = "[Illegal IP]";
		}
		return "[" + name + "] " + ip + ":" + String.valueOf(port);
	}
	public Config toConfig() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(index);
		c.addToBottom(userIndex);
		c.addToBottom(name);
		c.addToBottom(mac);
		c.addToBottom(ip);
		c.addToBottom(port);
		c.addToBottom(path);
		c.addToBottom(this.type.toString());
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
			this.userIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.name = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.mac = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.ip = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.port = c.fetchFirstInt();
			if(!c.getIsOK()) { return c; }
			this.path = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.type = com.FileManagerX.BasicEnums.MachineType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.state = com.FileManagerX.BasicEnums.MachineState.valueOf(c.fetchFirstString());
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
		
		if(o instanceof MachineInfo) {
			MachineInfo m = (MachineInfo)o;
			this.index = m.index;
			this.userIndex = m.userIndex;
			this.name = m.name;
			this.mac = m.mac;
			this.ip = m.ip;
			this.port = m.port;
			this.path = m.path;
			this.type = m.type;
			this.state = m.state;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof MachineInfo) {
			MachineInfo m = (MachineInfo)o;
			this.index = m.index;
			this.userIndex = m.userIndex;
			this.name = new String(m.name);
			this.mac = new String(m.mac);
			this.ip = new String(m.ip);
			this.port = m.port;
			this.path = new String(m.path);
			this.type = m.type;
			this.state = m.state;
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
