package com.FileManagerX.BasicModels;

public class MachineInfo implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private long userIndex;
	private String name;
	private String mac;
	private String ip;
	private int port;
	
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
		this.index = com.FileManagerX.Globals.Configurations.Next_MachineIndex + 1;
		com.FileManagerX.Globals.Configurations.Next_MachineIndex = index;
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
			byte[] macAddress = java.net.NetworkInterface.getByInetAddress(java.net.InetAddress.getLocalHost()).
					getHardwareAddress();
			this.name = Integer.toHexString(macAddress[0] & 0xFF);
			for(int i=1; i<macAddress.length; i++) {
				this.name += '-' + Integer.toHexString(macAddress[i] & 0xFF);
			}
			this.name = this.name.toUpperCase();
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
		name = "";
		ip = "";
		port = -1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	@Override
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
	public String output() {
		Config c = new Config("Machine = ");
		c.addToBottom(index);
		c.addToBottom(userIndex);
		c.addToBottom(name);
		c.addToBottom(mac);
		c.addToBottom(ip);
		c.addToBottom(port);
		return c.output();
	}
	public String input(String in) {
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.userIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.name = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.mac = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.ip = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.port = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
		MachineInfo m = (MachineInfo)o;
		this.index = m.index;
		this.userIndex = m.userIndex;
		this.name = m.name;
		this.mac = m.mac;
		this.ip = m.ip;
		this.port = m.port;
	}
	public void copyValue(Object o) {
		MachineInfo m = (MachineInfo)o;
		this.index = m.index;
		this.userIndex = m.userIndex;
		this.name = new String(m.name);
		this.mac = new String(m.mac);
		this.ip = new String(m.ip);
		this.port = m.port;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isLocal() {
		return this.index == com.FileManagerX.Globals.Configurations.This_MachineIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
