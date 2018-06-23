package Interfaces;

import java.util.List;

public interface IDBManagers {

	public boolean setContent(List<Interfaces.IDBManager> content);
	public List<Interfaces.IDBManager> getContent();
	
	public void clear();
	public int size();
	public boolean add(Interfaces.IDBManager item);
	
	public int indexOfDataBaseName(String dbName);
	public Interfaces.IDBManager searchDataBaseName(String dbName);
	public Interfaces.IDBManager fetchDataBaseName(String dbName);
	public void deleteDataBaseName(String dbName);
	
	public int indexOfDepotName(String depotName);
	public Interfaces.IDBManager searchDepotName(String depotName);
	public Interfaces.IDBManager fetchDepotName(String depotName);
	public void deleteDepotName(String depotName);
	
	public int indexOfDataBaseIndex(long idx);
	public Interfaces.IDBManager searchDataBaseIndex(long idx);
	public Interfaces.IDBManager fetchDataBaseIndex(long idx);
	public void deleteDataBaseIndex(long idx);
	
	public int indexOfDepotIndex(long idx);
	public Interfaces.IDBManager searchDepotIndex(long idx);
	public Interfaces.IDBManager fetchDepotIndex(long idx);
	public void deleteDepotIndex(long idx);
	
	public int indexOfMachineName(String machineName);
	public Interfaces.IDBManager searchMachineName(String machineName);
	public Interfaces.IDBManager fetchMachineName(String machineName);
	public void deleteMachineName(String machineName);
	
	public int indexOfMachineIndex(long idx);
	public Interfaces.IDBManager searchMachineIndex(long idx);
	public Interfaces.IDBManager fetchMachineIndex(long idx);
	public void deleteMachineIndex(long idx);
	
	public int indexOfMachineIP(String machineIP);
	public Interfaces.IDBManager searchMachineIP(String machineIP);
	public Interfaces.IDBManager fetchMachineIP(String machineIP);
	public void deleteMachineIP(String machineIP);
	
	public int indexOfDataBaseUrl(String dbUrl);
	public Interfaces.IDBManager searchDataBaseUrl(String dbUrl);
	public Interfaces.IDBManager fetchDataBaseUrl(String dbUrl);
	public void deleteDataBaseUrl(String dbUrl);
	
	public int indexOfDepotUrl(String depotUrl);
	public Interfaces.IDBManager searchDepotUrl(String depotUrl);
	public Interfaces.IDBManager fetchDepotUrl(String depotUrl);
	public void deleteDepotUrl(String depotUrl);
	
	public void removeIdleDBManagers();
	public void removeAllDBManagers();
}
