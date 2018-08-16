package com.FileManagerX.DataBase;

import java.util.*;

public class Managers implements com.FileManagerX.Interfaces.IDBManagers {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.Interfaces.IDBManager> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.Interfaces.IDBManager> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.Interfaces.IDBManager> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Managers() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.Interfaces.IDBManager>();
		}
		content.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int size() {
		return content.size();
	}
	public void clear() {
		initThis();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			content.add((com.FileManagerX.Interfaces.IDBManager)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By DataBaseIndex
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.Interfaces.IDBManager>() {
			public int compare(com.FileManagerX.Interfaces.IDBManager e1, com.FileManagerX.Interfaces.IDBManager e2) {
				if(e1.getDBInfo().getIndex() > e2.getDBInfo().getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		
		try {
			Collections.sort(this.getContent(), c);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(com.FileManagerX.BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	/**
	 * Sort By DataBaseIndex
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.Interfaces.IDBManager>() {
			public int compare(com.FileManagerX.Interfaces.IDBManager e1, com.FileManagerX.Interfaces.IDBManager e2) {
				if(e1.getDBInfo().getIndex() > e2.getDBInfo().getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		try {
			Collections.sort(this.getContent(), c);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(com.FileManagerX.BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOfDataBaseName(String dbName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getName().equals(dbName)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchDataBaseName(String dbName) {
		int index = this.indexOfDataBaseName(dbName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchDataBaseName(String dbName) {
		int index = this.indexOfDataBaseName(dbName);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteDataBaseName(String dbName) {
		int index = this.indexOfDataBaseName(dbName);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOfDepotName(String depotName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getDepotInfo().getName().equals(depotName)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchDepotName(String depotName) {
		int index = this.indexOfDepotName(depotName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchDepotName(String depotName) {
		int index = this.indexOfDepotName(depotName);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteDepotName(String depotName) {
		int index = this.indexOfDepotName(depotName);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOfDataBaseIndex(long idx) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getIndex() == idx) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchDataBaseIndex(long idx) {
		int index = this.indexOfDataBaseIndex(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchDataBaseIndex(long idx) {
		int index = this.indexOfDataBaseIndex(idx);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteDataBaseIndex(long idx) {
		int index = this.indexOfDataBaseIndex(idx);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfDepotIndex(long idx) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getDepotInfo().getIndex() == idx) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchDepotIndex(long idx) {
		int index = this.indexOfDepotIndex(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchDepotIndex(long idx) {
		int index = this.indexOfDepotIndex(idx);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteDepotIndex(long idx) {
		int index = this.indexOfDepotIndex(idx);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfMachineName(String machineName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getMachineInfo().getName().equals(machineName)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchMachineName(String machineName) {
		int index = this.indexOfMachineName(machineName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchMachineName(String machineName) {
		int index = this.indexOfMachineName(machineName);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteMachineName(String machineName) {
		int index = this.indexOfMachineName(machineName);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfMachineIndex(long idx) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getMachineInfo().getIndex() == idx) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchMachineIndex(long idx) {
		int index = this.indexOfMachineIndex(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchMachineIndex(long idx) {
		int index = this.indexOfMachineIndex(idx);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteMachineIndex(long idx) {
		int index = this.indexOfMachineIndex(idx);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfMachineIP(String machineIP) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getMachineInfo().getIp().equals(machineIP)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchMachineIP(String machineIP) {
		int index = this.indexOfMachineIP(machineIP);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchMachineIP(String machineIP) {
		int index = this.indexOfMachineIP(machineIP);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteMachineIP(String machineIP) {
		int index = this.indexOfMachineIP(machineIP);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfDataBaseUrl(String dbUrl) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getUrl().equals(dbUrl)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchDataBaseUrl(String dbUrl) {
		int index = this.indexOfDataBaseUrl(dbUrl);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchDataBaseUrl(String dbUrl) {
		int index = this.indexOfDataBaseUrl(dbUrl);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteDataBaseUrl(String dbUrl) {
		int index = this.indexOfDataBaseUrl(dbUrl);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfDepotUrl(String depotUrl) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getDBInfo().getDepotInfo().getUrl().equals(depotUrl)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchDepotUrl(String depotUrl) {
		int index = this.indexOfDepotUrl(depotUrl);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchDepotUrl(String depotUrl) {
		int index = this.indexOfDepotUrl(depotUrl);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteDepotUrl(String depotUrl) {
		int index = this.indexOfDepotUrl(depotUrl);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfUnit(Unit unit) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getUnit().equals(unit)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IDBManager searchUnit(Unit unit) {
		int index = this.indexOfUnit(unit);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.Interfaces.IDBManager fetchUnit(Unit unit) {
		int index = this.indexOfUnit(unit);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.Interfaces.IDBManager i = content.get(index);
		this.content.get(index).disconnect();
		this.content.remove(index);
		return i;
	}
	public void deleteUnit(Unit unit) {
		int index = this.indexOfUnit(unit);
		if(index >= 0) {
			this.content.get(index).disconnect();
			this.content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void removeIdleDBManagers() {
		for(int i=this.content.size()-1; i>=0; i--) {
			if(!this.content.get(i).isConnected()) {
				this.content.remove(i);
			}
		}
	}
	public void removeAllDBManagers() {
		for(int i=this.content.size()-1; i>=0; i--) {
			this.content.get(i).disconnect();
			this.content.remove(i);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IDBManager nextIdleManager(Unit unit) {
		java.util.LinkedList<com.FileManagerX.Interfaces.IDBManager> runningManagers = 
				new java.util.LinkedList<>();
		
		com.FileManagerX.BasicEnums.DataBaseType type = com.FileManagerX.BasicEnums.DataBaseType.TXT;
		int amount = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : this.content) {
			if(!dbm.getUnit().equals(unit)) {
				continue;
			}
			type = dbm.getDBInfo().getType();
			amount++;
			if(dbm.isConnected() && !dbm.isRunning()) {
				return dbm;
			}
			else {
				runningManagers.add(dbm);
			}
		}
		
		if(amount < com.FileManagerX.Globals.Configurations.DataBaseConnectionPoolSize) {
			if(com.FileManagerX.BasicEnums.DataBaseType.TXT.equals(type)) {
				if(amount == 0) {
					com.FileManagerX.Interfaces.IDBManager dbm = unit.getManager(type);
					dbm.connect();
					if(dbm.isConnected() && !dbm.isRunning()) {
						this.add(dbm);
						return dbm;
					}
				}
			}
			else {
				com.FileManagerX.Interfaces.IDBManager dbm = unit.getManager(type);
				dbm.connect();
				if(dbm.isConnected() && !dbm.isRunning()) {
					this.add(dbm);
					return dbm;
				}
			}
		}
		
		if(runningManagers.size() == 0) {
			return null;
		}
		int index = (int) (com.FileManagerX.Tools.Time.getTicks() % runningManagers.size());
		return runningManagers.get(index);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

