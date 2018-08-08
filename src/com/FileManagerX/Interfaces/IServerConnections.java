package com.FileManagerX.Interfaces;

public interface IServerConnections extends ICollection {

	public boolean setConnections(java.util.LinkedList<com.FileManagerX.Interfaces.IServerConnection> connections);
	public boolean setPermitIdle(long permitIdle);
	public boolean setNext_ConnectionIndex(int nextIndex);
	
	public java.util.LinkedList<com.FileManagerX.Interfaces.IServerConnection> getConnections();
	public long getPermitIdle();
	public int getNext_ConnectionIndex();
	
	public void removeIdleConnections();
	public void removeAllConnections();
	
	public int indexOf(int connectionIndex);
	public com.FileManagerX.Interfaces.IServerConnection search(int connectionIndex);
	public com.FileManagerX.Interfaces.IServerConnection fetch(int connectionIndex);
	public void delete(int connectionIndex);
	
	public int indexOf(long clientMachineIndex);
	public com.FileManagerX.Interfaces.IServerConnection search(long clientMachineIndex);
	public com.FileManagerX.Interfaces.IServerConnection fetch(long clientMachineIndex);
	public void delete(long clientMachineIndex);
}
