package com.FileManagerX.Interfaces;

public interface IOperatorPackage extends IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIsStart(boolean start);
	public boolean setIsQuery(boolean query);
	public boolean setIndex(long index);
	public boolean setIndex();
	
	public boolean setExitConnection(boolean exit);
	public boolean setExitOperator(boolean exit);
	public boolean setStopOperator(boolean stop);
	public boolean setRestartOperator(boolean restart);
	
	public boolean setFinishedAmount(int amount);
	public boolean setRemainAmount(int amount);
	public boolean setRequireAmount(int amount);
	public boolean setResults(java.util.List<String> results);
	
	public boolean setArgs(String args);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isStart();
	public boolean isQuery();
	public long getIndex();
	
	public boolean isExitConnection();
	public boolean isExitOperator();
	public boolean isStopOperator();
	public boolean isRestartOperator();
	
	public int getFinishedAmount();
	public int getRemainAmount();
	public int getRequireAmount();
	public java.util.List<String> getResults();
	
	public String getArgs();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
}
