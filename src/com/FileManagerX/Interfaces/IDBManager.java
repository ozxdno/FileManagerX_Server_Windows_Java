package com.FileManagerX.Interfaces;

public interface IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database);
	public boolean setUnit(com.FileManagerX.DataBase.Unit unit);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo();
	public com.FileManagerX.DataBase.Unit getUnit();
	public com.FileManagerX.Interfaces.IDBManager getUnitMananger();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isConnected();
	public boolean connect();
	public boolean disconnect();
	public boolean load();
	public boolean save();
	public boolean create();
	public boolean delete();
	public boolean exists();
	public boolean clear();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys(Object conditions);
	public Object query(Object conditions);
	public Object updates(Object items);
	public boolean update(Object item);
	public Object removes(Object items);
	public boolean remove(Object item);
	public long size();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
