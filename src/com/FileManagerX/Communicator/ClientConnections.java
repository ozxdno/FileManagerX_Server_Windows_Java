package com.FileManagerX.Communicator;

public class ClientConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IClientConnection> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ClientConnections() {
		initThis();
	}
	private void initThis() {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfConnectionIndex(long index, com.FileManagerX.BasicEnums.ConnectionType type) {
		if(type == null) { return -1; }
		
		int i = -1;
		for(com.FileManagerX.Interfaces.IClientConnection c : this.getContent()) {
			i++;
			if(c.getIndex() == index && type.contains(c.getType())) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IClientConnection searchConnectionIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		for(com.FileManagerX.Interfaces.IClientConnection c : this.getContent()) {
			if(c.getIndex() == index && type.contains(c.getType())) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IClientConnection fetchConnectionIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		java.util.Iterator<com.FileManagerX.Interfaces.IClientConnection> it = this.getContent().iterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IClientConnection c = it.next();
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
		for(com.FileManagerX.Interfaces.IClientConnection c : this.getContent()) {
			i++;
			if(c.getServerMachineInfo().getIndex() == index && 
					type.contains(c.getType())) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IClientConnection searchMachineIndex(long index, 
			com.FileManagerX.BasicEnums.ConnectionType type) {
		for(com.FileManagerX.Interfaces.IClientConnection c : this.getContent()) {
			if(c.getServerMachineInfo().getIndex() == index &&
					type.contains(c.getType())) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IClientConnection fetchMachineIndex(long index,
			com.FileManagerX.BasicEnums.ConnectionType type) {
		java.util.Iterator<com.FileManagerX.Interfaces.IClientConnection> it = this.getContent().iterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IClientConnection c = it.next();
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
