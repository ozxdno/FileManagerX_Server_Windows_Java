package com.FileManagerX.Communicator;

import java.util.Collections;
import java.util.Comparator;

public class ServerConnections implements com.FileManagerX.Interfaces.IServerConnections {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private java.util.LinkedList<com.FileManagerX.Interfaces.IServerConnection> connections;
	private long permitIdle;
	private int Next_ConnectionIndex;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnections(java.util.LinkedList<com.FileManagerX.Interfaces.IServerConnection> connections) {
		if(connections == null) {
			return false;
		}
		this.connections = connections;
		return true;
	}
	public boolean setPermitIdle(long permitIdle) {
		if(permitIdle > 0) {
			this.permitIdle = permitIdle;
			return true;
		}
		return false;
	}
	public boolean setNext_ConnectionIndex(int nextIndex) {
		this.Next_ConnectionIndex = nextIndex;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public java.util.LinkedList<com.FileManagerX.Interfaces.IServerConnection> getConnections() {
		return this.connections;
	}
	public long getPermitIdle() {
		return this.permitIdle;
	}
	public int getNext_ConnectionIndex() {
		return this.Next_ConnectionIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ServerConnections() {
		initThis();
	}
	private void initThis() {
		if(this.connections == null) {
			this.connections = new java.util.LinkedList<>();
		}
		this.connections.clear();
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForServerPermitIdle;
		this.Next_ConnectionIndex = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int size() {
		return this.connections.size();
	}
	public void clear() {
		this.removeAllConnections();
		initThis();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			this.connections.add((com.FileManagerX.Interfaces.IServerConnection)item);
			return false;
		} catch(Exception e) {
			return true;
		}
	}
	public java.util.LinkedList<com.FileManagerX.Interfaces.IServerConnection> getContent() {
		return this.connections;
	}
	/**
	 * Sort By ConnectionIndex
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.Interfaces.IServerConnection>() {
			public int compare(com.FileManagerX.Interfaces.IServerConnection e1,
					com.FileManagerX.Interfaces.IServerConnection e2) {
				return e1.getIndex() > e2.getIndex() ? 1 : -1;
			}
		};
		
		try {
			Collections.sort(this.connections, c);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(
					"Error in Compare",e.toString());
			return false;
		}
	}
	
	/**
	 * Sort ConnectionIndex
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.Interfaces.IServerConnection>() {
			public int compare(com.FileManagerX.Interfaces.IServerConnection e1,
					com.FileManagerX.Interfaces.IServerConnection e2) {
				return e1.getIndex() > e2.getIndex() ? -1 : 1;
			}
		};
		
		try {
			Collections.sort(this.connections, c);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Error in Compare",e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized void removeIdleConnections() {
		for(int i=this.connections.size() - 1; i>=0; i--) {
			if(!this.connections.get(i).isRunning()) {
				this.connections.remove(i);
				continue;
			}
		}
	}
	public synchronized void removeAllConnections() {
		for(int i=this.connections.size()-1; i>=0; i--) {
			this.connections.get(i).disconnect();
			this.connections.remove(i);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOf(int index) {
		int i = -1;
		for(com.FileManagerX.Interfaces.IServerConnection c : this.connections) {
			i++;
			if(c.getIndex() == index) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IServerConnection search(int index) {
		for(com.FileManagerX.Interfaces.IServerConnection c : this.connections) {
			if(c.getIndex() == index) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IServerConnection fetch(int index) {
		java.util.Iterator<com.FileManagerX.Interfaces.IServerConnection> it = this.connections.iterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IServerConnection c = it.next();
			if(c.getIndex() == index) {
				it.remove();
				return c;
			}
		}
		return null;
	}
	public void delete(int index) {
		this.fetch(index);
	}
	
	public int indexOf(long index) {
		int i = -1;
		for(com.FileManagerX.Interfaces.IServerConnection c : this.connections) {
			i++;
			if(c.getServerMachineInfo().getIndex() == index && 
					c.getType().equals(com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.Interfaces.IServerConnection search(long index) {
		for(com.FileManagerX.Interfaces.IServerConnection c : this.connections) {
			if(c.getServerMachineInfo().getIndex() == index &&
					c.getType().equals(com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND)) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IServerConnection fetch(long index) {
		java.util.Iterator<com.FileManagerX.Interfaces.IServerConnection> it = this.connections.iterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IServerConnection c = it.next();
			if(c.getServerMachineInfo().getIndex() == index &&
					c.getType().equals(com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND)) {
				it.remove();
				return c;
			}
		}
		return null;
	}
	public void delete(long index) {
		this.fetch(index);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
