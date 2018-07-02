package Interfaces;

public interface IServerChecker {

	public boolean setDBManager(Interfaces.IDBManager dbmanager);
	public Interfaces.IDBManager getDBManager();
	
	public boolean initialize(Object infos);
	public boolean check();
	
	public boolean checkMachines();
	public boolean checkDepots();
	public boolean checkDataBases();
	public boolean checkUsers();
	public boolean checkInvitations();
	
}
