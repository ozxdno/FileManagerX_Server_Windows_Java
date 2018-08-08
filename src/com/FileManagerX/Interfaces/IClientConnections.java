package com.FileManagerX.Interfaces;

public interface IClientConnections extends ICollection {

	public boolean setConnections(java.util.LinkedList<com.FileManagerX.Interfaces.IClientConnection> connections);
	public boolean setPermitIdle(long permitIdle);
	public boolean setNext_ConnectionIndex(int nextIndex);
	
	public java.util.LinkedList<com.FileManagerX.Interfaces.IClientConnection> getConnections();
	public long getPermitIdle();
	public int getNext_ConnectionIndex();
	
	public void removeIdleConnections();
	public void removeAllConnections();
	
	public int indexOf(int connectionIndex);
	public com.FileManagerX.Interfaces.IClientConnection search(int connectionIndex);
	public com.FileManagerX.Interfaces.IClientConnection fetch(int connectionIndex);
	public void delete(int connectionIndex);
	
	public int indexOf(long serverMachineIndex);
	public com.FileManagerX.Interfaces.IClientConnection search(long serverMachineIndex);
	public com.FileManagerX.Interfaces.IClientConnection fetch(long serverMachineIndex);
	public void delete(long serverMachineIndex);
}
