package Interfaces;

import java.util.List;

public interface IServerScanner extends ICollection {
	
	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo);
	public boolean setConnections(List<Interfaces.IServerConnection> connections);
	public boolean setPermitIdle(long permitIdle);
	public boolean setNext_ConnectionIndex(int nextIndex);
	
	public BasicModels.MachineInfo getServerMachineInfo();
	public List<Interfaces.IServerConnection> getConnections();
	public long getPermitIdle();
	public int getNext_ConnectionIndex();
	public boolean isRunning();
	
	public boolean initialize(Object infos);
	public boolean connect();
	public void disconnect();
	
	public void removeIdleConnections();
	public void removeAllConnections();
	
	public int indexOf(int index);
	public Interfaces.IServerConnection search(int index);
	public Interfaces.IServerConnection fetch(int index);
	public void delete(int index);
	
	public int indexOf(long clientMachineIndex);
	public Interfaces.IServerConnection search(long clientMachineIndex);
	public Interfaces.IServerConnection fetch(long clientMachineIndex);
	public void delete(long clientMachineIndex);
	
	public int indexOf(String name);
	public Interfaces.IServerConnection search(String name);
	public Interfaces.IServerConnection fetch(String name);
	public void delete(String name);
	
	public int indexOf(String ip, int port);
	public Interfaces.IServerConnection search(String ip, int port);
	public Interfaces.IServerConnection fetch(String ip, int port);
	public void delete(String ip, int port);
}
