package com.FileManagerX.DataBase;

public class Manager implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	
	private com.FileManagerX.Interfaces.IDBManagers managers;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database) {
		if(database == null) {
			return false;
		}
		this.database = database;
		return true;
	}
	public boolean setUnit(com.FileManagerX.DataBase.Unit unit) {
		if(unit == null) {
			return false;
		}
		this.unit = unit;
		return true;
	}
	public boolean setIsRunning(boolean running) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.database;
	}
	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	public com.FileManagerX.Interfaces.IDBManager getUnitMananger() {
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager getUnitMananger(Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.nextIdleManager(unit);
		return dbm;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Manager() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.MANAGER;
		this.managers = com.FileManagerX.Factories.DataBaseFactory.createManagers();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isConnected() {
		return true;
	}
	public boolean isRunning() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean connect() {
		if(com.FileManagerX.BasicEnums.DataBaseType.MySQL.equals(this.database.getType())) {
			this.createManager_MySQL();
		}
		else if(com.FileManagerX.BasicEnums.DataBaseType.TXT.equals(this.database.getType())) {
			this.createManager_TXT();
		}
		else {
			return false;
		}
		
		boolean ok = false;
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.managers.getContent()) {
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok |= dbm.connect();
			}
		}
		return ok;
	}
	public boolean disconnect() {
		boolean ok = true;
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.managers.getContent()) {
			ok &= dbm.disconnect();
		}
		return ok;
	}
	public boolean load() {
		boolean ok = true;
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.managers.getContent()) {
			ok &= dbm.load();
		}
		return ok;
	}
	public boolean save() {
		boolean ok = true;
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.managers.getContent()) {
			ok &= dbm.save();
		}
		return ok;
	}
	public boolean create() {
		boolean ok = true;
		com.FileManagerX.Interfaces.IDBManager shell = this.managers.fetchUnit(Unit.SHELL);
		ok &= shell == null ? true : shell.create();
		
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.managers.getContent()) {
			ok &= dbm.create();
		}
		
		if(shell != null) { this.managers.add(shell); }
		return ok;
	}
	public boolean delete() {
		boolean ok = true;
		com.FileManagerX.Interfaces.IDBManager shell = this.managers.fetchUnit(Unit.SHELL);
		if(shell == null) { return true; }
		ok &= shell.delete();
		
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.managers.getContent()) {
			ok &= dbm.delete();
		}
		return ok;
	}
	public boolean exists() {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(Unit.SHELL);
		return dbm == null ? false : dbm.exists();
	}
	public boolean clear() {
		boolean ok = true;
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.managers.getContent()) {
			ok &= dbm.clear();
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys(Object conditions) {
		return null;
	}
	public Object query(Object conditions) {
		return null;
	}
	public Object updates(Object items) {
		return null;
	}
	public boolean update(Object item) {
		return false;
	}
	public Object removes(Object items) {
		return null;
	}
	public boolean remove(Object item) {
		return false;
	}
	public long size() {
		return 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Object querys(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return null;
		}
		dbm.setIsRunning(true);
		Object res = dbm.querys(conditions);
		dbm.setIsRunning(false);
		return res;
	}
	public Object query(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return null;
		}
		dbm.setIsRunning(true);
		Object res = dbm.query(conditions);
		dbm.setIsRunning(false);
		return res;
	}
	public Object updates(Object items, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return null;
		}
		dbm.setIsRunning(true);
		Object res = dbm.updates(items);
		dbm.setIsRunning(false);
		return res;
	}
	public boolean update(Object item, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		dbm.setIsRunning(true);
		boolean res = dbm.update(item);
		dbm.setIsRunning(false);
		return res;
	}
	public Object removes(Object items, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return null;
		}
		dbm.setIsRunning(true);
		Object res = dbm.removes(items);
		dbm.setIsRunning(false);
		return res;
	}
	public boolean remove(Object item, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		dbm.setIsRunning(true);
		boolean res = dbm.remove(item);
		dbm.setIsRunning(false);
		return res;
	}
	public long size(com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return -1;
		}
		dbm.setIsRunning(true);
		long res = dbm.size();
		dbm.setIsRunning(false);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void createManager_MySQL() {
		this.managers.clear();
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_SHELL());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Folder());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_File());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Machine());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Depot());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_DataBase());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_User());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Invitation());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Picture());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Gif());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Music());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Invitation());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_CFG());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Chat());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Group());
	}
	private void createManager_TXT() {
		this.managers.clear();
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_SHELL());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Folder());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_File());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Machine());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Depot());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_DataBase());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_User());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Invitation());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Picture());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Gif());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Music());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Invitation());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_CFG());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Chat());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Group());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

