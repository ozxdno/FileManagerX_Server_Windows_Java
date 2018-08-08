package com.FileManagerX.Factories;

public class DataBaseFactory {
	
	public final static com.FileManagerX.Interfaces.IDBManager createManager() {
		return new com.FileManagerX.DataBase.Manager();
	}
	
	public final static com.FileManagerX.Interfaces.IDBManagers createManagers() {
		return new com.FileManagerX.DataBase.Managers();
	}
	
	public final static com.FileManagerX.Interfaces.IDBChecker createChecker() {
		return new com.FileManagerX.DataBase.Checker();
	}
	
}
