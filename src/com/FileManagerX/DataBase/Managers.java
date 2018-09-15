package com.FileManagerX.DataBase;

public class Managers extends com.FileManagerX.BasicCollections.BasicCollection
	<com.FileManagerX.Interfaces.IDBManager> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForUnit =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.Interfaces.IDBManager) {
					com.FileManagerX.Interfaces.IDBManager i = (com.FileManagerX.Interfaces.IDBManager)item;
					return i.getUnit();
				}
				return null;
			}
		};
	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForDataBaseIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.Interfaces.IDBManager) {
					com.FileManagerX.Interfaces.IDBManager i = (com.FileManagerX.Interfaces.IDBManager)item;
					return i.getDBInfo().getIndex();
				}
				return null;
			}
		};

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private long permitIdle = 5 * 60 * 60;
	private long tryat = 0;
	private boolean ok = true;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database) {
		if(database == null) { return false; }
		this.database = database;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.database;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Managers() {
		initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicLinkedList<>());
		this.setKey(KeyForUnit);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IDBManager searchByDataBaseIndex(long database) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getIndex() == database) {
				return dbm;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager fetchByDataBaseIndex(long database) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getIndex() == database) {
				it.remove();
				return dbm;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IDBManager searchByDepotIndex(long depot) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getDepotIndex() == depot) {
				return dbm;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager fetchByDepotIndex(long depot) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getDepotIndex() == depot) {
				it.remove();
				return dbm;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IDBManager searchByDataBaseName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getName().equals(name)) {
				return dbm;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager fetchByDataBaseName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getName().equals(name)) {
				it.remove();
				return dbm;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IDBManager searchByDepotName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getDepotInfo().getName().equals(name)) {
				return dbm;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager fetchByDepotName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getDepotInfo().getName().equals(name)) {
				it.remove();
				return dbm;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IDBManager searchByUnit(Unit unit) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getUnit().equals(unit)) {
				return dbm;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager fetchByUnit(Unit unit) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getUnit().equals(unit)) {
				it.remove();
				return dbm;
			}
		}
		return null;
	}
	public Managers searchesByUnit(Unit unit) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		Managers managers = new Managers();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getUnit().equals(unit)) {
				managers.add(dbm);
			}
		}
		return managers;
	}
	public Managers fetchesByUnit(Unit unit) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		Managers managers = new Managers();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getUnit().equals(unit)) {
				managers.add(dbm);
				it.remove();
			}
		}
		return managers;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized void removeIdleManagers() {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(!dbm.isConnected()) {
				it.remove();
			}
		}
	}
	public synchronized void removeAllManagers() {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			dbm.disconnect();
			it.remove();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IDBManager nextIdleManager(Unit unit) {
		if(unit == null) { return null; }
		
		Managers managers = this.searchesByUnit(unit);
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = managers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.isConnected() && !dbm.isRunning()) {
				return dbm;
			}
		}
		
		if(!this.ok && com.FileManagerX.Tools.Time.getTicks() - this.tryat < this.permitIdle) {
			return null;
		}
		
		if(database.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
			if(managers.size() > 0) {
				return managers.fetchByCount(com.FileManagerX.Tools.Random.SimpleRandomNumber(0, managers.size()-1));
			}
			else {
				if(Unit.File.equals(unit)) {
					TXTManager_File dbm = new TXTManager_File();
					com.FileManagerX.Interfaces.IDBManager dbmf = this.searchByUnit(Unit.Folder);
					if(dbmf == null) {
						dbmf = new TXTManager_Folder();
						dbmf.setDBInfo(database);
						dbmf.connect();
						if(dbmf.isConnected()) { this.add(dbmf); }
					}
					
					dbm.setFoldersManager(dbmf);
					dbm.setDBInfo(database);
					dbm.connect();
					this.tryat = com.FileManagerX.Tools.Time.getTicks();
					this.ok = dbm.isConnected();
					if(dbm.isConnected()) { this.add(dbm); return dbm; }
					return null;
				}
				
				com.FileManagerX.Interfaces.IDBManager dbm = unit.getManager
						(com.FileManagerX.BasicEnums.DataBaseType.TXT);
				dbm.setDBInfo(database);
				dbm.connect();
				this.tryat = com.FileManagerX.Tools.Time.getTicks();
				this.ok = dbm.isConnected();
				if(dbm.isConnected()) { this.add(dbm); return dbm; }
				return null;
			}
		}
		
		if(database.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
			if(managers.size() < com.FileManagerX.Globals.Configurations.LimitForJDBC) {
				if(Unit.File.equals(unit)) {
					MySQLManager_File dbm = new MySQLManager_File();
					com.FileManagerX.Interfaces.IDBManager dbmf = this.searchByUnit(Unit.Folder);
					if(dbmf == null) {
						dbmf = new MySQLManager_Folder();
						dbmf.setDBInfo(database);
						dbmf.connect();
						if(dbmf.isConnected()) { this.add(dbmf); }
					}
					dbm.setFoldersManager(dbmf);
					dbm.setDBInfo(database);
					dbm.connect();
					
					this.tryat = com.FileManagerX.Tools.Time.getTicks();
					this.ok = dbm.isConnected();
					
					if(dbm.isConnected()) { this.add(dbm); return dbm; }
					dbm.disconnect();
					return null;
				}
				com.FileManagerX.Interfaces.IDBManager dbm = unit.getManager(this.database.getType());
				dbm.setDBInfo(database);
				dbm.connect();
				
				this.tryat = com.FileManagerX.Tools.Time.getTicks();
				this.ok = dbm.isConnected();
				
				if(dbm.isConnected()) { this.add(dbm); return dbm; }
				dbm.disconnect();
				return null;
			}
			else {
				return managers.fetchByCount(com.FileManagerX.Tools.Random.SimpleRandomNumber(0, managers.size()-1));
			}
		}
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

