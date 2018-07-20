package Communicator;

import java.util.*;

import Interfaces.IClientLinker;

public class ClientTCP implements IClientLinker{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<Interfaces.IClientConnection> connections;
	private long permitIdle;
	private int Next_ConnectionIndex;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnections(List<Interfaces.IClientConnection> connections) {
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
	
	public List<Interfaces.IClientConnection> getConnections() {
		return this.connections;
	}
	public long getPermitIdle() {
		return this.permitIdle;
	}
	public int getNext_ConnectionIndex() {
		return this.Next_ConnectionIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ClientTCP() {
		initThis();
	}
	private void initThis() {
		if(this.connections == null) {
			this.connections = new ArrayList<Interfaces.IClientConnection>();
		}
		this.connections.clear();
		this.permitIdle = Globals.Configurations.TimeForClientPermitIdle;
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
			this.connections.add((Interfaces.IClientConnection)item);
			return false;
		} catch(Exception e) {
			return true;
		}
	}
	/**
	 * Sort By ConnectionIndex
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<Interfaces.IClientConnection>() {
			public int compare(Interfaces.IClientConnection e1, Interfaces.IClientConnection e2) {
				return e1.getIndex() > e2.getIndex() ? 1 : -1;
			}
		};
		
		try {
			Collections.sort(this.connections, c);
			return true;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
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
		Comparator c = new Comparator<Interfaces.IClientConnection>() {
			public int compare(Interfaces.IClientConnection e1, Interfaces.IClientConnection e2) {
				return e1.getIndex() > e2.getIndex() ? -1 : 1;
			}
		};
		
		try {
			Collections.sort(this.connections, c);
			return true;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void removeIdleConnections() {
		for(int i=this.connections.size() - 1; i>=0; i--) {
			if(!this.connections.get(i).isRunning()) {
				//this.connections.get(i).disconnect();
				this.connections.remove(i);
				continue;
			}
			if(!this.connections.get(i).test()) {
				this.connections.get(i).disconnect();
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
		if(this.connections == null) {
			return -1;
		}
		for(int i=0; i<this.connections.size(); i++) {
			if(this.connections.get(i).getIndex() == index) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IClientConnection search(int index) {
		int idx = indexOf(index);
		if(idx == -1) {
			return null;
		}
		return this.connections.get(idx);
	}
	public Interfaces.IClientConnection fetch(int index) {
		int idx = indexOf(index);
		if(idx == -1) {
			return null;
		}
		Interfaces.IClientConnection res = this.connections.get(idx);
		this.connections.remove(idx);
		return res;
	}
	public void delete(int index) {
		int idx = indexOf(index);
		if(idx == -1) {
			return;
		}
		this.connections.get(idx).disconnect();
		this.connections.remove(idx);
	}
	
	public int indexOf(long serverMachineIndex) {
		if(this.connections == null) {
			return -1;
		}
		for(int i=0; i<this.connections.size(); i++) {
			if(this.connections.get(i).getServerMachineInfo().getIndex() == serverMachineIndex &&
				BasicEnums.ConnectionType.TRANSPORT_COMMAND.equals(this.connections.get(i).getType())) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IClientConnection search(long serverMachineIndex) {
		int idx = indexOf(serverMachineIndex);
		if(idx == -1) {
			return null;
		}
		return this.connections.get(idx);
	}
	public Interfaces.IClientConnection fetch(long serverMachineIndex) {
		int idx = indexOf(serverMachineIndex);
		if(idx == -1) {
			return null;
		}
		Interfaces.IClientConnection res = this.connections.get(idx);
		this.connections.remove(idx);
		return res;
	}
	public void delete(long serverMachineIndex) {
		int idx = indexOf(serverMachineIndex);
		if(idx == -1) {
			return;
		}
		this.connections.get(idx).disconnect();
		this.connections.remove(idx);
	}
	
	public int indexOf(String name) {
		if(this.connections == null) {
			return -1;
		}
		for(int i=0; i<this.connections.size(); i++) {
			if(this.connections.get(i).getConnectionName().equals(name) &&
				BasicEnums.ConnectionType.TRANSPORT_COMMAND.equals(this.connections.get(i).getType())) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IClientConnection search(String name) {
		int idx = indexOf(name);
		if(idx == -1) {
			return null;
		}
		return this.connections.get(idx);
	}
	public Interfaces.IClientConnection fetch(String name) {
		int idx = indexOf(name);
		if(idx == -1) {
			return null;
		}
		Interfaces.IClientConnection res = this.connections.get(idx);
		this.connections.remove(idx);
		return res;
	}
	public void delete(String name) {
		int idx = indexOf(name);
		if(idx == -1) {
			return;
		}
		this.connections.get(idx).disconnect();
		this.connections.remove(idx);
	}
	
	public int indexOf(String ip, int port) {
		if(this.connections == null) {
			return -1;
		}
		for(int i=0; i<this.connections.size(); i++) {
			String iip = this.connections.get(i).getServerMachineInfo().getIp();
			int iport = this.connections.get(i).getSocket().getPort();
			if(iip.equals(ip) && iport == port) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IClientConnection search(String ip, int port) {
		int idx = indexOf(ip, port);
		if(idx == -1) {
			return null;
		}
		return this.connections.get(idx);
	}
	public Interfaces.IClientConnection fetch(String ip, int port) {
		int idx = indexOf(ip, port);
		if(idx == -1) {
			return null;
		}
		Interfaces.IClientConnection res = this.connections.get(idx);
		this.connections.remove(idx);
		return res;
	}
	public void delete(String ip, int port) {
		int idx = indexOf(ip, port);
		if(idx == -1) {
			return;
		}
		this.connections.get(idx).disconnect();
		this.connections.remove(idx);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
