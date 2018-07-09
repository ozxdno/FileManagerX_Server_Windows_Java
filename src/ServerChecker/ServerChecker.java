package ServerChecker;

public class ServerChecker implements Interfaces.IServerChecker {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Interfaces.IDBManager dbmanager;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBManager(Interfaces.IDBManager dbmanager) {
		if(dbmanager == null || !dbmanager.isConnected()) {
			return false;
		}
		this.dbmanager = dbmanager;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IDBManager getDBManager() {
		return this.dbmanager;
	}
	
	public Interfaces.IDepotChecker getDepotChecker() {
		if(this.dbmanager == null) {
			return null;
		}
		return this.dbmanager.getChecker();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ServerChecker() {
		initThis();
	}
	public ServerChecker(Interfaces.IDBManager dbmanager) {
		initThis();
		this.setDBManager(dbmanager);
	}
	private void initThis() {
		this.dbmanager = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return this.setDBManager((Interfaces.IDBManager)infos);
	}
	
	public boolean check() {
		if(this.dbmanager == null) {
			return false;
		}
		
		if(!this.checkMachines()) {
			return false;
		}
		if(!this.checkDepots()) {
			return false;
		}
		if(!this.checkDataBases()) {
			return false;
		}
		if(!this.checkUsers()) {
			return false;
		}
		if(!this.checkInvitations()) {
			return false;
		}
		
		return true;
	}
	public boolean checkMachines() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	public boolean checkDepots() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	public boolean checkDataBases() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	public boolean checkUsers() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	public boolean checkInvitations() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
