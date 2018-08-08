package com.FileManagerX.Interfaces;

import java.util.List;

import com.FileManagerX.DataBase.Unit;

public interface IDBManagers extends ICollection {

	public boolean setContent(List<com.FileManagerX.Interfaces.IDBManager> content);
	public List<com.FileManagerX.Interfaces.IDBManager> getContent();
	
	public int indexOfDataBaseName(String dbName);
	public com.FileManagerX.Interfaces.IDBManager searchDataBaseName(String dbName);
	public com.FileManagerX.Interfaces.IDBManager fetchDataBaseName(String dbName);
	public void deleteDataBaseName(String dbName);
	
	public int indexOfDepotName(String depotName);
	public com.FileManagerX.Interfaces.IDBManager searchDepotName(String depotName);
	public com.FileManagerX.Interfaces.IDBManager fetchDepotName(String depotName);
	public void deleteDepotName(String depotName);
	
	public int indexOfDataBaseIndex(long idx);
	public com.FileManagerX.Interfaces.IDBManager searchDataBaseIndex(long idx);
	public com.FileManagerX.Interfaces.IDBManager fetchDataBaseIndex(long idx);
	public void deleteDataBaseIndex(long idx);
	
	public int indexOfDepotIndex(long idx);
	public com.FileManagerX.Interfaces.IDBManager searchDepotIndex(long idx);
	public com.FileManagerX.Interfaces.IDBManager fetchDepotIndex(long idx);
	public void deleteDepotIndex(long idx);
	
	public int indexOfMachineName(String machineName);
	public com.FileManagerX.Interfaces.IDBManager searchMachineName(String machineName);
	public com.FileManagerX.Interfaces.IDBManager fetchMachineName(String machineName);
	public void deleteMachineName(String machineName);
	
	public int indexOfMachineIndex(long idx);
	public com.FileManagerX.Interfaces.IDBManager searchMachineIndex(long idx);
	public com.FileManagerX.Interfaces.IDBManager fetchMachineIndex(long idx);
	public void deleteMachineIndex(long idx);
	
	public int indexOfMachineIP(String machineIP);
	public com.FileManagerX.Interfaces.IDBManager searchMachineIP(String machineIP);
	public com.FileManagerX.Interfaces.IDBManager fetchMachineIP(String machineIP);
	public void deleteMachineIP(String machineIP);
	
	public int indexOfDataBaseUrl(String dbUrl);
	public com.FileManagerX.Interfaces.IDBManager searchDataBaseUrl(String dbUrl);
	public com.FileManagerX.Interfaces.IDBManager fetchDataBaseUrl(String dbUrl);
	public void deleteDataBaseUrl(String dbUrl);
	
	public int indexOfDepotUrl(String depotUrl);
	public com.FileManagerX.Interfaces.IDBManager searchDepotUrl(String depotUrl);
	public com.FileManagerX.Interfaces.IDBManager fetchDepotUrl(String depotUrl);
	public void deleteDepotUrl(String depotUrl);
	
	public int indexOfUnit(Unit unit);
	public com.FileManagerX.Interfaces.IDBManager searchUnit(Unit unit);
	public com.FileManagerX.Interfaces.IDBManager fetchUnit(Unit unit);
	public void deleteUnit(Unit unit);
	
	public void removeIdleDBManagers();
	public void removeAllDBManagers();
}
