package Communicator;

import java.net.*;
import java.util.*;

public class ServerTCP implements Interfaces.IPublic, Interfaces.IServerScanner {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private Scanner scanner;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getServerMachineInfo() {
		return serverMachineInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo) {
		if(serverMachineInfo == null) {
			return false;
		}
		this.serverMachineInfo = serverMachineInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ServerTCP() {
		initThis();
	}
	public ServerTCP(BasicModels.MachineInfo serverMachineInfo) {
		initThis();
		setServerMachineInfo(serverMachineInfo);
	}
	private void initThis() {
		if(serverMachineInfo == null) {
			serverMachineInfo = new BasicModels.MachineInfo();
		}
		serverMachineInfo.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "Server: " + this.serverMachineInfo.getName() + " " + 
				this.serverMachineInfo.getIp() + ":" +
				String.valueOf(this.serverMachineInfo.getPort());
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config(this.serverMachineInfo.output());
		c.setField("ServerTCP");
		return c.output();
	}
	public String input(String in) {
		return this.serverMachineInfo.input(in);
	}
	public void copyReference(Object o) {
		ServerTCP s = (ServerTCP)o;
		this.serverMachineInfo = s.serverMachineInfo;
		this.scanner = s.scanner;
	}
	public void copyValue(Object o) {
		this.copyReference(o);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isRunning() {
		return this.scanner != null && this.scanner.isRunning();
	}
	
	public boolean initialize(Object o) {
		BasicModels.MachineInfo sm = (BasicModels.MachineInfo)o;
		this.setServerMachineInfo(sm);
		return true;
	}
	public boolean connect() {
		this.disconnect();
		try {
			scanner = new Scanner(
					new ServerSocket(this.serverMachineInfo.getPort()),
					this.serverMachineInfo);
			scanner.start();
			return true;
		} catch(Exception e) {
			BasicEnums.ErrorType.BUILD_SOCKET_FAILED.register(e.toString());
			return false;
		}
	}
	public void disconnect() {
		if(this.scanner != null) {
			this.scanner.disconnect();
		}
	}
	
	public int indexOf(String ip) {
		if(this.scanner == null) {
			return -1;
		}
		for(int i=0; i<this.scanner.getConnections().size(); i++) {
			if(this.scanner.getConnections().get(i).getClientMachineInfo().getIp().equals(ip)) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IServerConnection search(String ip) {
		int idx = indexOf(ip);
		if(idx == -1) {
			return null;
		}
		return this.scanner.getConnections().get(idx);
	}
	
	public int indexOf(long userIndex) {
		if(this.scanner == null) {
			return -1;
		}
		for(int i=0; i<this.scanner.getConnections().size(); i++) {
			if(this.scanner.getConnections().get(i).getUser().getIndex() == userIndex) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IServerConnection search(long userIndex) {
		int idx = indexOf(userIndex);
		if(idx == -1) {
			return null;
		}
		return this.scanner.getConnections().get(idx);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void removeIdleConnections() {
		if(this.scanner != null) {
			this.scanner.removeIdleConnections();;
		}
	}
	public void removeAllConnections() {
		if(this.scanner != null) {
			this.scanner.removeAllConnections();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}



class Scanner extends Thread {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private List<Interfaces.IServerConnection> connections;
	
	private ServerSocket socket;
	private boolean abort;
	private boolean running; // 指的是机器有没有在运行，而不是其中线程有没有在运行。
	
	private long permitIdle;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo) {
		if(serverMachineInfo == null) {
			return false;
		}
		this.serverMachineInfo = serverMachineInfo;
		return true;
	}
	public boolean setConnections(List<Interfaces.IServerConnection> connections) {
		if(connections == null) {
			return false;
		}
		this.connections = connections;
		return true;
	}
	
	public boolean setSocket(ServerSocket socket) {
		if(socket == null) {
			return false;
		}
		this.socket = socket;
		return true;
	}
	public boolean setAbort(boolean abort) {
		this.abort = abort;
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

	public BasicModels.MachineInfo getServerMachineInfo() {
		return this.serverMachineInfo;
	}
	public List<Interfaces.IServerConnection> getConnections() {
		return this.connections;
	}
	
	public ServerSocket getSocket() {
		return this.socket;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isRunning() {
		return this.running;
	}
	
	public long getPermitIdle() {
		return this.permitIdle;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Scanner() {
		initThis();
	}
	public Scanner(ServerSocket socket, BasicModels.MachineInfo serverMachineInfo) {
		initThis();
		setSocket(socket);
		setServerMachineInfo(serverMachineInfo);
	}
	private void initThis() {
		serverMachineInfo = null;
		if(connections == null) {
			connections = new ArrayList<Interfaces.IServerConnection>();
		}
		connections.clear();
		
		socket = null;
		abort = false;
		running = true;
		permitIdle = 1 * 60 * 1000;
		this.setName("TCP Server Scanner");
	}
	public void run() {
		abort = false;
		running = true;
		if(socket == null) {
			running = false;
			return;
		}
		while(!abort && !socket.isClosed()) {
			try {
				Interfaces.IServerConnection connection = new ServerConnection(socket.accept(),serverMachineInfo);
				connections.add(connection);
				connection.connect();
			}catch(Exception e) {
				;
			}
			this.removeIdleConnections();
		}
		running = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void disconnect() {
		
		this.removeAllConnections();
		
		try {
			if(socket != null) {
				socket.close();
			}
		}catch(Exception e) {
			;
		}
		while(running) {
			abort = true;
		}
		try {
			if(socket != null) {
				socket.close();
			}
		}catch(Exception e) {
			;
		}
	}
	public void removeIdleConnections() {
		for(int i=this.connections.size() - 1; i>=0; i--) {
			if(!this.connections.get(i).isRunning()) {
				this.connections.get(i).disconnect();
				this.connections.remove(i);
				continue;
			}
			long ticks1 = Tools.Time.getTicks();
			long ticks2 = this.connections.get(i).getLastOperationTime();
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
}

