package Communicator;

import java.util.List;

public interface IClientLinker {

	public boolean setConnections(List<ClientConnection> connections);
	public boolean setPermitIdle(long permitIdle);
	
	public List<ClientConnection> getConnections();
	public long getPermitIdle();
	
	public boolean add(ClientConnection connection);
	public void removeIdleConnections();
	public void removeAllConnections();
	
	public int indexOf(String serverIp);
	public ClientConnection search(String serverIp);
	public void remove(String serverIp);
	
	public int indexOf(long userIndex);
	public ClientConnection search(long userIndex);
	public void remove(long userIndex);
}
