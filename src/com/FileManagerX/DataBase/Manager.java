package com.FileManagerX.DataBase;

public class Manager implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private Managers managers;
	
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
		this.managers = new Managers();
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
		
		boolean ok = true;
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok &= dbm.connect();
			}
		}
		return ok;
	}
	public boolean disconnect() {
		boolean ok = false;
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok &= dbm.disconnect();
			}
		}
		return ok;
	}
	public boolean load() {
		boolean ok = false;
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok &= dbm.load();
			}
		}
		return ok;
	}
	public boolean save() {
		boolean ok = false;
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok &= dbm.save();
			}
		}
		return ok;
	}
	public boolean create() {
		boolean ok = true;
		com.FileManagerX.Interfaces.IDBManager shell = this.managers.fetchByUnit(Unit.SHELL);
		ok &= shell == null ? true : shell.create();
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok &= dbm.create();
			}
		}
		
		if(shell != null) { this.managers.add(shell); }
		return ok;
	}
	public boolean delete() {
		boolean ok = true;
		com.FileManagerX.Interfaces.IDBManager shell = this.managers.fetchByUnit(Unit.SHELL);
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok &= dbm.delete();
			}
		}
	
		ok &= shell == null ? true : shell.delete();
		return ok;
	}
	public boolean exists() {
		com.FileManagerX.Interfaces.IDBManager dbm = this.managers.searchByUnit(Unit.SHELL);
		return dbm == null ? false : dbm.exists();
	}
	public void clear() {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				dbm.clear();
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toString() {
		return this.getDBInfo() == null ? "NULL" : this.getDBInfo().toString();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		return null;
	}
	public String output() {
		return null;
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return null;
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		return null;
	}
	public void copyReference(Object o) {
		;
	}
	public void copyValue(Object o) {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean querys(Object conditions, Object result) {
		return false;
	}
	public boolean query(Object conditions, Object result) {
		return false;
	}
	public boolean updates(Object items, Object errors) {
		return false;
	}
	public boolean update(Object item) {
		return false;
	}
	public boolean removes(Object items, Object errors) {
		return false;
	}
	public boolean remove(Object item) {
		return false;
	}
	public long size() {
		return 0;
	}
	
	public Object querys2(Object conditions) {
		return null;
	}
	public Object query2(Object conditions) {
		return null;
	}
	public Object removes2(Object items) {
		return null;
	}
	public Object updates2(Object items) {
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean querys(Object conditions, Object result, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		boolean res = dbm.querys(conditions, result);
		return res;
	}
	public boolean query(Object conditions, Object result, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		boolean res = dbm.query(conditions, result);
		return res;
	}
	public boolean updates(Object items, Object errors, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		boolean res = dbm.updates(items, errors);
		return res;
	}
	public boolean update(Object item, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		boolean res = dbm.update(item);
		return res;
	}
	public boolean removes(Object items, Object errors, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		boolean res = dbm.removes(items, errors);
		return res;
	}
	public boolean remove(Object item, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		boolean res = dbm.remove(item);
		return res;
	}
	public long size(com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return -1;
		}
		
		long res = dbm.size();
		return res;
	}
	
	public Object querys2(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		Object res = dbm.querys2(conditions);
		return res;
	}
	public Object query2(Object conditions, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		Object res = dbm.query2(conditions);
		return res;
	}
	public Object removes2(Object items, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		Object res = dbm.removes2(items);
		return res;
	}
	public Object updates2(Object items, com.FileManagerX.DataBase.Unit unit) {
		com.FileManagerX.Interfaces.IDBManager dbm = this.getUnitMananger(unit);
		if(dbm == null) {
			return false;
		}
		
		Object res = dbm.updates2(items);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		boolean ok = false;
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				this.managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				dbm.setDBInfo(database);
				ok &= dbm.check();
			}
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void createManager_MySQL() {
		
		this.managers.setDBInfo(this.database);
		/*
		this.managers.clear();
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_SHELL());
		com.FileManagerX.Interfaces.IDBManager fdm = new com.FileManagerX.DataBase.MySQLManager_Folder();
		com.FileManagerX.DataBase.MySQLManager_File fm = new com.FileManagerX.DataBase.MySQLManager_File();
		fm.setFoldersManager(fdm);
		this.managers.add(fdm);
		this.managers.add(fm);
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Machine());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Depot());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_DataBase());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_User());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Invitation());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Picture());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Gif());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Music());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_CFG());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Chat());
		this.managers.add(new com.FileManagerX.DataBase.MySQLManager_Group());
		*/
	}
	private void createManager_TXT() {
		
		this.managers.setDBInfo(this.database);
		/*
		this.managers.clear();
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_SHELL());
		com.FileManagerX.Interfaces.IDBManager fdm = new com.FileManagerX.DataBase.TXTManager_Folder();
		com.FileManagerX.DataBase.TXTManager_File fm = new com.FileManagerX.DataBase.TXTManager_File();
		fm.setFoldersManager(fdm);
		this.managers.add(fdm);
		this.managers.add(fm);
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Machine());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Depot());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_DataBase());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_User());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Invitation());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Picture());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Gif());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Music());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_CFG());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Chat());
		this.managers.add(new com.FileManagerX.DataBase.TXTManager_Group());
		*/
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

