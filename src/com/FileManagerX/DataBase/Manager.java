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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.database;
	}
	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	public com.FileManagerX.Interfaces.IDBManager getUnitMananger() {
		return this.managers.searchUnit(unit);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Manager() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.managers = com.FileManagerX.Factories.DataBaseFactory.createManagers();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isConnected() {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(unit);
		return dbm == null ? false : dbm.isConnected();
	}
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
			dbm.setDBInfo(database);
			ok |= dbm.connect();
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
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(this.unit);
		return dbm == null ? null : dbm.querys(conditions);
	}
	public Object query(Object conditions) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(this.unit);
		return dbm == null ? null : dbm.query(conditions);
	}
	public Object updates(Object items) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(this.unit);
		return dbm == null ? null : dbm.updates(items);
	}
	public boolean update(Object item) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(this.unit);
		return dbm == null ? false : dbm.update(item);
	}
	public Object removes(Object items) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(this.unit);
		return dbm == null ? null : dbm.removes(items);
	}
	public boolean remove(Object item) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(this.unit);
		return dbm == null ? false : dbm.remove(item);
	}
	public long size() {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchUnit(this.unit);
		return dbm == null ? 0 : dbm.size();
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
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

