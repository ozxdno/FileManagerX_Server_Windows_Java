package Interfaces;

import java.util.List;

public interface IClientLinker extends ICollection {

	public boolean setConnections(List<Interfaces.IClientConnection> connections);
	public boolean setPermitIdle(long permitIdle);
	public boolean setNext_ConnectionIndex(int nextIndex);
	
	public List<Interfaces.IClientConnection> getConnections();
	public long getPermitIdle();
	public int getNext_ConnectionIndex();
	
	public void removeIdleConnections();
	public void removeAllConnections();
	
	
	
	public int indexOf(int index);
	public Interfaces.IClientConnection search(int index);
	public Interfaces.IClientConnection fetch(int index);
	public void delete(int index);
	
	public int indexOf(String ip, int port);
	public Interfaces.IClientConnection search(String ip, int port);
	public Interfaces.IClientConnection fetch(String ip, int port);
	public void delete(String ip, int port);
}
