package Factories;

public class DBManagerFactory {
	
	public static final Interfaces.IDBManager createDBManager() {
		return new DataBaseManager.DBManager();
	}
	
	public static final Interfaces.IDBManagers createDBManagers() {
		return new DataBaseManager.DBManagers();
	}
	
	public static final Interfaces.IDBManager createSQLManager() {
		return new DataBaseManager.SQLManager();
	}
	
	public static final Interfaces.IDBManager createTXTManager() {
		return new DataBaseManager.TXTManager();
	}
}
