package com.FileManagerX.Communicator;

public class ClientConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IClientConnection> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ClientConnections() {
		this.initThis();
	}
	private void initThis() {
		this.setKey(new KeyForMachine());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.IClientConnection) {
				com.FileManagerX.Interfaces.IClientConnection i = 
						(com.FileManagerX.Interfaces.IClientConnection)item;
				return i.getIndex();
			}
			return null;
		}
	}
	public static class KeyForMachine implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.IClientConnection) {
				com.FileManagerX.Interfaces.IClientConnection i = 
						(com.FileManagerX.Interfaces.IClientConnection)item;
				return i.getServerMachineInfo().getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
