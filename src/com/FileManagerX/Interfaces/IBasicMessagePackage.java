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
	
	public boolean setPassword(String password);
	
	public boolean setIndex(long index);
	public boolean setIndex();
	public boolean setId(long id);
	public boolean setPriority(int priority);
	public boolean setPriority(com.FileManagerX.BasicEnums.CMDPriority priority);
	public boolean setProcess(long index);
	
	public boolean setIsRecord(boolean isRecord);
	public boolean setIsDirect(boolean isDirect);
	public boolean setBroadCast(com.FileManagerX.Deliver.Broadcast broadcast);
	
	public boolean setPermitIdle(long idle);
	public boolean setRoutPathPackage(com.FileManagerX.Interfaces.IRoutePathPackage rrp);
	
	public boolean setThis_LocalAsSour();
	public boolean setThis_LocalAsDest();
	public boolean setThis_ServerAsSour();
	public boolean setThis_ServerAsDest();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getSourMachineIndex();
	public long getDestMachineIndex();
	public long getSourDepotIndex();
	public long getDestDepotIndex();
	public long getSourDataBaseIndex();
	public long getDestDataBaseIndex();
	public long getSourUserIndex();
	public long getDestUserIndex();
	
	public String getPassword();
	
	public long getIndex();
	public long getId();
	public int getPriority();
	public com.FileManagerX.BasicEnums.CMDPriority getPriorityEnum();
	public long getProcess();
	
	public boolean isRecord();
	public boolean isDirect();
	public com.FileManagerX.Deliver.Broadcast getBroadcast();
	
	public long getPermitIdle();
	public com.FileManagerX.Interfaces.IRoutePathPackage getRoutePathPackage();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void swapSourAndDest();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
