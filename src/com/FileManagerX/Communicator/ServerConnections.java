package com.FileManagerX.Communicator;

public class ServerConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IServerConnection> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ServerConnections() {
		initThis();
	}
	private void initThis() {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfConnectionIndex(long index, com.FileManagerX.BasicEnums.ConnectionType type) {
		if(type == null) { return -1; }
		
		int i = -1;
		for(com.FileManagerX.Interfaces.IServerConnection c : this.getContent()) {
			i++;
			if(c.getIndex() == index && type.contains(c.getType())) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IServerConnection searchConnectionIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		for(com.FileManagerX.Interfaces.IServerConnection c : this.getContent()) {
			if(c.getIndex() == index && type.contains(c.getType())) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IServerConnection fetchConnectionIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		java.util.Iterator<com.FileManagerX.Interfaces.IServerConnection> it = this.getContent().iterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IServerConnection c = it.next();
			if(c.getIndex() == index && type.contains(c.getType())) {
				it.remove();
				return c;
			}
		}
		return null;
	}
	public void deleteConnectionIndex(long index, com.FileManagerX.BasicEnums.ConnectionType type) {
		this.fetchConnectionIndex(index, type);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfMachineIndex(long index, com.FileManagerX.BasicEnums.ConnectionType type) {
		int i = -1;
		for(com.FileManagerX.Interfaces.IServerConnection c : this.getContent()) {
			i++;
			if(c.getServerMachineInfo().getIndex() == index && 
					type.contains(c.getType())) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IServerConnection searchMachineIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		for(com.FileManagerX.Interfaces.IServerConnection c : this.getContent()) {
			if(c.getServerMachineInfo().getIndex() == index &&
					type.contains(c.getType())) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IServerConnection fetchMachineIndex(long index,
			com.FileManagerX.BasicEnums.ConnectionType type) {
		java.util.Iterator<com.FileManagerX.Interfaces.IServerConnection> it = this.getContent().iterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IServerConnection c = it.next();
			if(c.getServerMachineInfo().getIndex() == index &&
					type.contains(c.getType())) {
				it.remove();
				return c;
			}
		}
		return null;
	}
	public void deleteMachineIndex(long index, com.FileManagerX.BasicEnums.ConnectionType type) {
		this.fetchMachineIndex(index, type);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
