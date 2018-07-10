package Interfaces;

public interface IServerChecker {

	public boolean setDBManager(Interfaces.IDBManager dbmanager);
	public Interfaces.IDBManager getDBManager();
	public Interfaces.IDepotChecker getDepotChecker();
	
	public boolean initialize(Object infos);
	public boolean check();
	
	public boolean checkIndex();
	public boolean checkMachines();
	public boolean checkDepots();
	public boolean checkDataBases();
	public boolean checkUsers();
	public boolean checkInvitations();
	
}
