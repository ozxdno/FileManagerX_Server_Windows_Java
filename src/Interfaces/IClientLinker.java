package Interfaces;

import java.util.List;

public interface IClientLinker {

	public boolean setConnections(List<Interfaces.IClientConnection> connections);
	public boolean setPermitIdle(long permitIdle);
	
	public List<Interfaces.IClientConnection> getConnections();
	public long getPermitIdle();
	
	public boolean add(Interfaces.IClientConnection connection);
	public void removeIdleConnections();
	public void removeAllConnections();
	
	public int indexOf(String serverIp);
	public Interfaces.IClientConnection search(String serverIp);
	public void remove(String serverIp);
	
	public int indexOf(long userIndex);
	public Interfaces.IClientConnection search(long userIndex);
	public void remove(long userIndex);
}
