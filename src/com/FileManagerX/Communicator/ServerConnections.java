package com.FileManagerX.Communicator;

public class ServerConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IServerConnection> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.Interfaces.IServerConnection) {
					com.FileManagerX.Interfaces.IServerConnection i = 
							(com.FileManagerX.Interfaces.IServerConnection)item;
					return i.getIndex();
				}
				return null;
			}
		};
	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForMachine =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.Interfaces.IServerConnection) {
					com.FileManagerX.Interfaces.IServerConnection i = 
							(com.FileManagerX.Interfaces.IServerConnection)item;
					return i.getClientMachineInfo().getIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ServerConnections() {
		this.initThis();
	}
	private void initThis() {
		this.setKey(KeyForIndex);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
