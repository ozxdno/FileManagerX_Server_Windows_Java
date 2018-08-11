package com.FileManagerX.Interfaces;

public interface IRoutePathPackage extends IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setStartTime(long startTime);
	public boolean setStartTime();
	public boolean setArriveTime(long arriveTime);
	public boolean setArriveTime();
	public boolean setBackTime(long backTime);
	public boolean setBackTime();
	
	public boolean setDepth(long depth);
	public boolean setMoreDepth();
	public boolean setLessDepth();
	
	public boolean setSourMachine(long sour);
	public boolean setDestMachine(long dest);
	public boolean setDeliverMachines(java.util.List<Long> machines);
	public boolean setDeliverMachine(long deliver);
	
	public boolean setThis(long sourMachine, long destMachine);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getStartTime();
	public long getArriveTime();
	public long getBackTime();
	
	public long getDepth();
	
	public long getSourMachine();
	public long getDestMachine();
	public java.util.List<Long> getDeliverMachines();
	public long getDeliverMachine();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void reverse();
	public void add(long machine);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
