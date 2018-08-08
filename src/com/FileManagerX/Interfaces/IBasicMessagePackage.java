package com.FileManagerX.Interfaces;

public interface IBasicMessagePackage extends IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setSourMachineIndex(long index);
	public boolean setDestMachineIndex(long index);
	public boolean setSourDepotIndex(long index);
	public boolean setDestDepotIndex(long index);
	public boolean setSourDataBaseIndex(long index);
	public boolean setDestDataBaseIndex(long index);
	public boolean setSourUserIndex(long index);
	public boolean setDestUserIndex(long index);
	
	public boolean setIp1(String ip);
	public boolean setIp2(String ip);
	public boolean setPort1(int port);
	public boolean setPort2(int port);
	
	public boolean setPassword(String password);
	
	public boolean setIndex(long index);
	public boolean setId(long id);
	public boolean setPriority(int priority);
	public boolean setPriority(com.FileManagerX.BasicEnums.CMDPriority priority);
	
	public boolean setIsRecord(boolean isRecord);
	public boolean setIsDirect(boolean isDirect);
	public boolean setIsBroadCast(boolean isBroadCast);
	
	public boolean setPermitIdle(long idle);
	public boolean setSendTime(long sendTime);
	public boolean setSendTime();
	public boolean setReceiveTime(long receiveTime);
	public boolean setReceiveTime();
	public boolean setRoutPathPackage(com.FileManagerX.Interfaces.IRoutePathPackage rrp);
	public boolean setRoutePathPackage();
	
	public boolean setThis(com.FileManagerX.Interfaces.IClientConnection connection);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getSourMachineIndex();
	public long getDestMachineIndex();
	public long getSourDepotIndex();
	public long getDestDepotIndex();
	public long getSourDataBaseIndex();
	public long getDestDataBaseIndex();
	public long getSourUserIndex();
	public long getDestUserIndex();
	
	public String getIp1();
	public String getIp2();
	public int getPort1();
	public int getPort2();
	
	public String getPassword();
	
	public long getIndex();
	public long getId();
	public int getPriority();
	public com.FileManagerX.BasicEnums.CMDPriority getPriorityEnum();
	
	public boolean isRecord();
	public boolean isDirect();
	public boolean isBroadCast();
	
	public long getPermitIdle();
	public long getSendTime();
	public long getReceiveTime();
	public com.FileManagerX.Interfaces.IRoutePathPackage getRoutePathPackage();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
