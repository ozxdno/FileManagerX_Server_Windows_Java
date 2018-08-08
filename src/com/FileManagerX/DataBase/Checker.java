package com.FileManagerX.DataBase;

public class Checker implements com.FileManagerX.Interfaces.IDBChecker {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IDBManager dbmanager;
	private Checker_Server cs;
	private Checker_NotServer cns;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBManager(com.FileManagerX.Interfaces.IDBManager dbmanager) {
		if(dbmanager == null || !dbmanager.isConnected()) {
			return false;
		}
		this.dbmanager = dbmanager;
		this.cs = new Checker_Server();
		this.cs.setDBManager(dbmanager);
		this.cns = new Checker_NotServer();
		this.cns.setDBManager(dbmanager);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IDBManager getDBManager() {
		return this.dbmanager;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Checker() {
		initThis();
	}
	public Checker(com.FileManagerX.Interfaces.IDBManager dbmanager) {
		initThis();
		this.setDBManager(dbmanager);
	}
	private void initThis() {
		this.dbmanager = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return this.setDBManager((com.FileManagerX.Interfaces.IDBManager)infos);
	}
	public boolean check() {
		boolean ok = true;
		ok &= this.checkServer();
		ok &= this.checkNotServer();
		return ok;
	}
	public boolean checkServer() {
		return cs.check();
	}
	public boolean checkNotServer() {
		return cns.check();
	}
	
	public boolean checkDataBaseAndTables_Server() {
		return cs.checkDataBaseAndTables();
	}
	public boolean checkIndexes_Server() {
		return cs.checkIndex();
	}
	public boolean checkMachines() {
		return cs.checkMachines();
	}
	public boolean checkDepots() {
		return cs.checkDepots();
	}
	public boolean checkDataBases() {
		return cs.checkDataBases();
	}
	public boolean checkUsers() {
		return cs.checkUsers();
	}
	public boolean checkInvitations() {
		return cs.checkInvitations();
	}
	
	public boolean checkDataBaseAndTables_NotServer() {
		return cns.checkDataBaseAndTables();
	}
	public boolean checkIndexes_NotServer() {
		return true;
	}
	public boolean checkFoldersAndFiles() {
		return cns.checkFoldersAndFiles();
	}
	public boolean checkPictures() {
		return cns.checkPictures();
	}
	public boolean checkGifs() {
		return cns.checkGifs();
	}
	public boolean checkMusics() {
		return cns.checkMusics();
	}
	public boolean checkViedos() {
		return cns.checkViedos();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
