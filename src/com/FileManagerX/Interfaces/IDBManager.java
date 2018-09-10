package com.FileManagerX.Interfaces;

public interface IDBManager extends IPublic {
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean querys(Object conditions, Object result);
	public boolean query(Object conditions, Object result);
	public boolean updates(Object items, Object errors);
	public boolean update(Object item);
	public boolean removes(Object items, Object errors);
	public boolean remove(Object item);
	public long size();
	
	public default Object querys2(Object conditions) {
		querys(conditions, null);
		return null;
	}
	public default Object query2(Object conditions) {
		querys(conditions, null);
		return null;
	}
	public default Object removes2(Object items) {
		removes(items, null);
		return null;
	}
	public default Object updates2(Object items) {
		updates(items, null);
		return null;
	}
	
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
		clear();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public default boolean querys(Object conditions, Object result, com.FileManagerX.DataBase.Unit unit) {
		return querys(conditions, result);
	}
	public default boolean query(Object conditions, Object result, com.FileManagerX.DataBase.Unit unit) {
		return query(conditions, result);
	}
	public default boolean updates(Object items, Object errors, com.FileManagerX.DataBase.Unit unit) {
		return updates(items, errors);
	}
	public default boolean update(Object item, com.FileManagerX.DataBase.Unit unit) {
		return update(item);
	}
	public default boolean removes(Object items, Object errors, com.FileManagerX.DataBase.Unit unit) {
		return removes(items, errors);
	}
	public default boolean remove(Object item, com.FileManagerX.DataBase.Unit unit) {
		return remove(item);
	}
	public default long size(com.FileManagerX.DataBase.Unit unit) {
		return size();
	}

	public default Object querys2(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		return querys2(conditions);
	}
	public default Object query2(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		return query2(conditions);
	}
	public default Object removes2(Object items, com.FileManagerX.DataBase.Unit unit) {
		return removes2(items);
	}
	public default Object updates2(Object items, com.FileManagerX.DataBase.Unit unit) {
		return updates2(items);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
