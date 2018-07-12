package Interfaces;

public interface IDepotChecker {

	public boolean setDBManager(Interfaces.IDBManager dbmanager);
	public Interfaces.IDBManager getDBManager();
	public Interfaces.IServerChecker getServerChecker();
	
	public boolean initialize(Object infos);
	public boolean check();
	
	public boolean checkDataBaseAndTables();
	
	public boolean checkFoldersAndFiles();
	public boolean checkPictures();
	public boolean checkGifs();
	public boolean checkMusics();
	public boolean checkViedos();
}
