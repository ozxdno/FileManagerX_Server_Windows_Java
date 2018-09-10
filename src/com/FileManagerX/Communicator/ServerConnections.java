package com.FileManagerX.Communicator;

public class ServerConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IServerConnection> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ServerConnections() {
		this.initThis();
	}
	private void initThis() {
		this.setKey(new KeyForIndex());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.IServerConnection) {
				com.FileManagerX.Interfaces.IServerConnection i = 
						(com.FileManagerX.Interfaces.IServerConnection)item;
				return i.getIndex();
			}
			return null;
		}
	}
	public static class KeyForMachine implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.IServerConnection) {
				com.FileManagerX.Interfaces.IServerConnection i = 
						(com.FileManagerX.Interfaces.IServerConnection)item;
				return i.getClientMachineInfo().getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
