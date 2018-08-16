package com.FileManagerX.Interfaces;

public interface IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database);
	public boolean setUnit(com.FileManagerX.DataBase.Unit unit);
	public boolean setIsRunning(boolean running);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo();
	public com.FileManagerX.DataBase.Unit getUnit();
	public com.FileManagerX.Interfaces.IDBManager getUnitMananger();
	public default com.FileManagerX.Interfaces.IDBManager getUnitMananger(com.FileManagerX.DataBase.Unit unit) {
		return getUnitMananger();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isConnected();
	public boolean isRunning();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
	public default boolean connect(com.FileManagerX.DataBase.Unit unit) {
		return connect();
	}
	public default boolean disconnect(com.FileManagerX.DataBase.Unit unit) {
		return disconnect();
	}
	public default boolean load(com.FileManagerX.DataBase.Unit unit) {
		return load();
	}
	public default boolean save(com.FileManagerX.DataBase.Unit unit) {
		return save();
	}
	public default boolean create(com.FileManagerX.DataBase.Unit unit) {
		return create();
	}
	public default boolean delete(com.FileManagerX.DataBase.Unit unit) {
		return delete();
	}
	public default boolean exists(com.FileManagerX.DataBase.Unit unit) {
		return exists();
	}
	public default boolean clear(com.FileManagerX.DataBase.Unit unit) {
		return clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public default Object querys(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		return querys(conditions);
	}
	public default Object query(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		return query(conditions);
	}
	public default Object updates(Object items, com.FileManagerX.DataBase.Unit unit) {
		return updates(items);
	}
	public default boolean update(Object item, com.FileManagerX.DataBase.Unit unit) {
		return update(item);
	}
	public default Object removes(Object items, com.FileManagerX.DataBase.Unit unit) {
		return removes(items);
	}
	public default boolean remove(Object item, com.FileManagerX.DataBase.Unit unit) {
		return remove(item);
	}
	public default long size(com.FileManagerX.DataBase.Unit unit) {
		return size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
