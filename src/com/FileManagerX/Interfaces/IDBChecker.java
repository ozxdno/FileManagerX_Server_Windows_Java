package com.FileManagerX.Interfaces;

public interface IDBChecker {

	public boolean setDBManager(com.FileManagerX.Interfaces.IDBManager dbmanager);
	
	public com.FileManagerX.Interfaces.IDBManager getDBManager();
	
	public boolean initialize(Object infos);
	
	public boolean check();
	public boolean checkServer();
	public boolean checkNotServer();
	
	public boolean checkDataBaseAndTables_Server();
	public boolean checkIndexes_Server();
	public boolean checkMachines();
	public boolean checkDepots();
	public boolean checkDataBases();
	public boolean checkUsers();
	public boolean checkInvitations();
	
	public boolean checkDataBaseAndTables_NotServer();
	public boolean checkIndexes_NotServer();
	public boolean checkFoldersAndFiles();
	public boolean checkPictures();
	public boolean checkGifs();
	public boolean checkMusics();
	public boolean checkViedos();
}
