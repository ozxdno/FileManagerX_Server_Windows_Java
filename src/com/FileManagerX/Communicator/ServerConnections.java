package com.FileManagerX.Communicator;

public class ServerConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IServerConnection> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.Interfaces.IServerConnection item) {
		return item == null ? null : item.getIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized com.FileManagerX.Interfaces.IServerConnection searchMachineIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		if(type == null) { return null; }
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IServerConnection> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IServerConnection con = it.getNext();
			if(con.getClientMachineInfo().getIndex() == index && type.contains(con.getType())) {
				return con;
			}
		}
		return null;
	}
	public synchronized com.FileManagerX.Interfaces.IServerConnection fetchMachineIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		if(type == null) { return null; }
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IServerConnection> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IServerConnection con = it.getNext();
			if(con.getClientMachineInfo().getIndex() == index && type.contains(con.getType())) {
				it.remove();
				return con;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
