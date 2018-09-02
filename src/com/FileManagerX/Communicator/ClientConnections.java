package com.FileManagerX.Communicator;

public class ClientConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IClientConnection> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.Interfaces.IClientConnection item) {
		return item == null ? null : item.getIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized com.FileManagerX.Interfaces.IClientConnection searchMachineIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		if(type == null) { return null; }
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IClientConnection con = it.getNext();
			if(con.getServerMachineInfo().getIndex() == index && type.contains(con.getType())) {
				return con;
			}
		}
		return null;
	}
	public synchronized com.FileManagerX.Interfaces.IClientConnection fetchMachineIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		if(type == null) { return null; }
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IClientConnection con = it.getNext();
			if(con.getServerMachineInfo().getIndex() == index && type.contains(con.getType())) {
				it.remove();
				return con;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
