package Communicator;

import java.util.*;

import Interfaces.IClientLinker;

public class ClientTCP implements IClientLinker{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<Interfaces.IClientConnection> connections;
	private long permitIdle;
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Interfaces.IClientConnection> getConnections() {
		return this.connections;
	}
	public long getPermitIdle() {
		return this.permitIdle;
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
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean add(Interfaces.IClientConnection connection) {
		this.removeIdleConnections();
		if(connection == null) {
			return false;
		}
		this.connections.add(connection);
		return true;
	}
	public void removeIdleConnections() {
		for(int i=this.connections.size() - 1; i>=0; i--) {
			if(!this.connections.get(i).isRunning()) {
				this.connections.get(i).disconnect();
				this.connections.remove(i);
				continue;
			}
			long ticks1 = Tools.Time.getTicks();
			long ticks2 = this.connections.get(i).getLastReceiveTime();
			long passed = ticks1 - ticks2;
			if(passed > this.permitIdle) {
				this.connections.get(i).disconnect();
				this.connections.remove(i);
			}
		}
	}
	public void removeAllConnections() {
		for(int i=this.connections.size()-1; i>=0; i--) {
			this.connections.get(i).disconnect();
			this.connections.remove(i);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOf(String serverIp) {
		for(int i=0; i<this.connections.size(); i++) {
			if(this.connections.get(i).getServerMachineInfo().getIp().equals(serverIp)) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IClientConnection search(String serverIp) {
		int idx = this.indexOf(serverIp);
		if(idx < 0) {
			return null;
		}
		return this.connections.get(idx);
	}
	public void remove(String serverIp) {
		int idx = this.indexOf(serverIp);
		if(idx < 0) {
			return;
		}
		
		this.connections.get(idx).disconnect();
		this.connections.remove(idx);
	}
	
	public int indexOf(long userIndex) {
		for(int i=0; i<this.connections.size(); i++) {
			if(this.connections.get(i).getUser().getIndex() == userIndex) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IClientConnection search(long userIndex) {
		int idx = this.indexOf(userIndex);
		if(idx < 0) {
			return null;
		}
		return this.connections.get(idx);
	}
	public void remove(long userIndex) {
		int idx = this.indexOf(userIndex);
		if(idx < 0) {
			return;
		}
		
		this.connections.get(idx).disconnect();
		this.connections.remove(idx);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
