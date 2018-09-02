package com.FileManagerX.DataBase;

public class Managers implements com.FileManagerX.Interfaces.ICollection
	<com.FileManagerX.Interfaces.IDBManager, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private java.util.LinkedList<com.FileManagerX.Interfaces.IDBManager> content;
	private com.FileManagerX.BasicModels.DataBaseInfo database;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database) {
		if(database == null) {
			return false;
		}
		this.database = database;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> getIterator() {
		return new IteratorImpl();
	}
	public Long getKey(com.FileManagerX.Interfaces.IDBManager item) {
		return item == null ? null : item.getDBInfo().getIndex();
	}
	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.database;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Managers() {
		initThis();
	}
	private void initThis() {
		this.content = new java.util.LinkedList<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int size() {
		return content.size();
	}
	public void clear() {
		initThis();
	}
	public boolean add(com.FileManagerX.Interfaces.IDBManager item) {
		this.content.add(item);
		return true;
	}
	public boolean sort(java.util.Comparator<com.FileManagerX.Interfaces.IDBManager> c) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toString() {
		return this.database.toString();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		return null;
	}
	public String output() {
		return "";
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
	
	public com.FileManagerX.Interfaces.IDBManager searchByKey(Long key) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			if(key.equals(this.getKey(it.getNext()))) {
				return it.getNext();
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager fetchByKey(Long key) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(key.equals(this.getKey(dbm))) {
				it.remove();
				return dbm;
			}
		}
		return null;
	}
	public Managers searchesByKey(Long key) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		Managers res = new Managers();
		while(it.hasNext()) {
			if(key.equals(this.getKey(it.getNext()))) {
				res.add(it.getNext());
			}
		}
		return res;
	}
	public Managers fetchesByKey(Long key) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		Managers res = new Managers();
		while(it.hasNext()) {
			if(key.equals(this.getKey(it.getNext()))) {
				res.add(it.getNext());
				it.remove();
			}
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IDBManager searchByCount(int count) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(--count < 0) {
				return dbm;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IDBManager fetchByCount(int count) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(--count < 0) {
				it.remove();
				return dbm;
			}
		}
		return null;
	}
	public Managers searchesByCount(int bg, int ed) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		Managers res = new Managers();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size()-1; }
		int cnt = 0;
		while(it.hasNext()) {
			if(cnt < bg) { cnt++; continue; }
			if(cnt <= ed) { res.add(it.getNext()); cnt++; continue; }
			if(cnt > ed) { break; }
		}
		return res;
	}
	public Managers fetchesByCount(int bg, int ed) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = this.getIterator();
		Managers res = new Managers();
		if(bg < 0) { bg = 0; }
		if(ed >= this.size()) { ed = this.size()-1; }
		int cnt = 0;
		while(it.hasNext()) {
			if(cnt < bg) { cnt++; continue; }
			if(cnt <= ed) { res.add(it.getNext()); it.remove(); cnt++; continue; }
			if(cnt > ed) { break; }
		}
		return res;
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
		for(int i=this.content.size()-1; i>=0; i--) {
			if(!this.content.get(i).isConnected()) {
				this.content.remove(i);
			}
		}
	}
	public synchronized void removeAllManagers() {
		for(int i=this.content.size()-1; i>=0; i--) {
			this.content.get(i).disconnect();
			this.content.remove(i);
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
						this.add(dbmf);
						dbmf.setDBInfo(database);
						dbmf.connect();
					}
					dbm.setFoldersManager(dbmf);
					this.add(dbm);
					dbm.setDBInfo(database);
					dbm.connect();
					return (dbm.isConnected() && !dbm.isRunning()) ? dbm : null;
				}
				
				com.FileManagerX.Interfaces.IDBManager dbm = unit.getManager
						(com.FileManagerX.BasicEnums.DataBaseType.TXT);
				this.add(dbm);
				dbm.setDBInfo(database);
				dbm.connect();
				return (dbm.isConnected() && !dbm.isRunning()) ? dbm : null;
			}
		}
		
		if(database.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
			if(managers.size() < com.FileManagerX.Globals.Configurations.LimitForJDBC) {
				if(Unit.File.equals(unit)) {
					MySQLManager_File dbm = new MySQLManager_File();
					com.FileManagerX.Interfaces.IDBManager dbmf = this.searchByUnit(Unit.Folder);
					if(dbmf == null) {
						dbmf = new MySQLManager_Folder();
						this.add(dbmf);
						dbmf.setDBInfo(database);
						dbmf.connect();
					}
					dbm.setFoldersManager(dbmf);
					this.add(dbm);
					dbm.setDBInfo(database);
					dbm.connect();
					return (dbm.isConnected() && !dbm.isRunning()) ? dbm : null;
				}
				com.FileManagerX.Interfaces.IDBManager dbm = unit.getManager(this.database.getType());
				this.add(dbm);
				dbm.setDBInfo(database);
				dbm.connect();
				return (dbm.isConnected() && !dbm.isRunning()) ? dbm : null;
			}
			else {
				return managers.fetchByCount(com.FileManagerX.Tools.Random.SimpleRandomNumber(0, managers.size()-1));
			}
		}
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class IteratorImpl implements com.FileManagerX.Interfaces.IIterator
		<com.FileManagerX.Interfaces.IDBManager> {
		private java.util.Iterator<com.FileManagerX.Interfaces.IDBManager> iterator = content.iterator();
		
		public boolean hasNext() {
			return iterator.hasNext();
		}
		public com.FileManagerX.Interfaces.IDBManager getNext() {
			return iterator.next();
		}
		public void remove() {
			iterator.remove();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

