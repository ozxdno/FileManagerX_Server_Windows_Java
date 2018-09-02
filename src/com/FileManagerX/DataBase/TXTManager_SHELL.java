package com.FileManagerX.DataBase;

public class TXTManager_SHELL implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database) {
		if(database == null) {
			return false;
		}
		this.database = database;
		this.name = database.getUrl();
		return true;
	}
	public synchronized boolean setUnit(com.FileManagerX.DataBase.Unit unit) {
		if(unit == null) {
			return false;
		}
		this.unit = unit;
		return true;
	}
	public synchronized boolean setIsRunning(boolean running) {
		this.running = running;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.database;
	}
	public synchronized com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	public synchronized com.FileManagerX.Interfaces.IDBManager getUnitMananger() {
		return this;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_SHELL() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.SHELL;
		this.connected = false;
		this.name = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized boolean isConnected() {
		return this.connected;
	}
	public synchronized boolean isRunning() {
		return this.running;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean connect() {
		this.name = this.database.getUrl();
		return this.connected = this.exists();
	}
	public synchronized boolean disconnect() {
		return this.connected = false;
	}
	public synchronized boolean load() {
		return true;
	}
	public synchronized boolean save() {
		return true;
	}
	public synchronized boolean create() {
		if(this.exists()) { return true; }
		
		try {
			java.io.File folder = new java.io.File(this.name);
			return folder.mkdirs();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Create DataBase Failed");
			return false;
		}
	}
	public synchronized boolean delete() {
		if(!this.exists()) { return true; }
		
		try {
			com.FileManagerX.Interfaces.IDepotManager dm = this.getDBInfo().getDepotInfo().getManager();
			dm.setUncheck(true);
			return dm.deleteDirectory(this.name);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Delete DataBase Failed");
			return false;
		}
	}
	public synchronized boolean exists() {
		if(this.database == null) {
			return false;
		}
		
		java.io.File folder = new java.io.File(this.database.getUrl());
		return folder.exists() && folder.isDirectory();
	}
	public synchronized boolean clear() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized boolean querys(Object conditions, Object result) {
		return false;
	}
	public synchronized boolean query(Object conditions, Object result) {
		return false;
	}
	public synchronized boolean updates(Object items, Object errors) {
		return false;
	}
	public synchronized boolean update(Object item) {
		return false;
	}
	public synchronized boolean removes(Object items, Object errors) {
		return false;
	}
	public synchronized boolean remove(Object item) {
		return false;
	}
	public synchronized long size() {
		return 1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean check() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
