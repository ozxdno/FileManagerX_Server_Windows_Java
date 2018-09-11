package com.FileManagerX.Communicator;

public class ClientConnections extends com.FileManagerX.Processes.Manager
	<com.FileManagerX.Interfaces.IClientConnection> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.Interfaces.IClientConnection) {
					com.FileManagerX.Interfaces.IClientConnection i = 
							(com.FileManagerX.Interfaces.IClientConnection)item;
					return i.getIndex();
				}
				return null;
			}
		};
	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForMachine =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.Interfaces.IClientConnection) {
					com.FileManagerX.Interfaces.IClientConnection i = 
							(com.FileManagerX.Interfaces.IClientConnection)item;
					return i.getServerMachineInfo().getIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ClientConnections() {
		this.initThis();
	}
	private void initThis() {
		this.setKey(KeyForMachine);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
