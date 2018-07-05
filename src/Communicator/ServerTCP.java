package Communicator;

import java.net.*;
import java.util.*;

public class ServerTCP implements Interfaces.IServerScanner {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private Scanner scanner;
	private int Next_ConnectionIndex;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getServerMachineInfo() {
		return serverMachineInfo;
	}
	public List<Interfaces.IServerConnection> getConnections() {
		return this.scanner == null ? null : this.scanner.getConnections();
	}
	public long getPermitIdle() {
		return this.scanner == null ? -1 : this.scanner.getPermitIdle();
	}
	public boolean isRunning() {
		return this.scanner != null && this.scanner.isRunning();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo) {
		if(serverMachineInfo == null) {
			return false;
		}
		this.serverMachineInfo = serverMachineInfo;
		return true;
	}
	public boolean setConnections(List<Interfaces.IServerConnection> connections) {
		if(this.scanner == null) {
			return false;
		}
		this.scanner.setConnections(connections);
		return true;
	}
	public boolean setPermitIdle(long permitIdle) {
		if(this.scanner == null) {
			return false;
		}
		this.scanner.setPermitIdle(permitIdle);
		return true;
	}
	public boolean setNext_ConnectionIndex(int nextIndex) {
		this.Next_ConnectionIndex = nextIndex;
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
		this.serverMachineInfo = Globals.Datas.ServerMachine;
		this.Next_ConnectionIndex = 0;
	}
	public int getNext_ConnectionIndex() {
		return this.Next_ConnectionIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int size() {
		if(this.scanner == null) {
			return 0;
		}
		return this.scanner.getConnections().size();
	}
	public void clear() {
		this.removeAllConnections();
		this.disconnect();
		initThis();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			this.scanner.getConnections().add((Interfaces.IServerConnection)item);
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
	public void sortIncrease() {
		if(this.scanner == null || this.scanner.getConnections() == null) {
			return;
		}
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<Interfaces.IServerConnection>() {
			public int compare(Interfaces.IServerConnection e1, Interfaces.IServerConnection e2) {
				return e1.getIndex() > e2.getIndex() ? 1 : -1;
			}
		};
		Collections.sort(this.scanner.getConnections(), c);
	}
	
	/**
	 * Sort ConnectionIndex
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortDecrease() {
		if(this.scanner == null || this.scanner.getConnections() == null) {
			return;
		}
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<Interfaces.IServerConnection>() {
			public int compare(Interfaces.IServerConnection e1, Interfaces.IServerConnection e2) {
				return e1.getIndex() > e2.getIndex() ? -1 : 1;
			}
		};
		Collections.sort(this.scanner.getConnections(), c);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
			BasicEnums.ErrorType.COMMUNICATOR_BUILD_SOCKED_FAILED.register(e.toString());
			return false;
		}
	}
	public void disconnect() {
		if(this.scanner != null) {
			this.scanner.disconnect();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOf(int index) {
		if(this.scanner == null) {
			return -1;
		}
		for(int i=0; i<this.scanner.getConnections().size(); i++) {
			if(this.scanner.getConnections().get(i).getIndex() == index) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IServerConnection search(int index) {
		int idx = indexOf(index);
		if(idx == -1) {
			return null;
		}
		return this.scanner.getConnections().get(idx);
	}
	public Interfaces.IServerConnection fetch(int index) {
		int idx = indexOf(index);
		if(idx == -1) {
			return null;
		}
		Interfaces.IServerConnection res = this.scanner.getConnections().get(idx);
		this.scanner.getConnections().remove(idx);
		return res;
	}
	public void delete(int index) {
		int idx = indexOf(index);
		if(idx == -1) {
			return;
		}
		this.scanner.getConnections().get(idx).disconnect();
		this.scanner.getConnections().remove(idx);
	}
	
	public int indexOf(String ip, int port) {
		if(this.scanner == null) {
			return -1;
		}
		for(int i=0; i<this.scanner.getConnections().size(); i++) {
			String iip = this.scanner.getConnections().get(i).getClientMachineInfo().getIp();
			int iport = this.scanner.getConnections().get(i).getSocket().getPort();
			if(iip.equals(ip) && iport == port) {
				return i;
			}
		}
		return -1;
	}
	public Interfaces.IServerConnection search(String ip, int port) {
		int idx = indexOf(ip, port);
		if(idx == -1) {
			return null;
		}
		return this.scanner.getConnections().get(idx);
	}
	public Interfaces.IServerConnection fetch(String ip, int port) {
		int idx = indexOf(ip, port);
		if(idx == -1) {
			return null;
		}
		Interfaces.IServerConnection res = this.scanner.getConnections().get(idx);
		this.scanner.getConnections().remove(idx);
		return res;
	}
	public void delete(String ip, int port) {
		int idx = indexOf(ip, port);
		if(idx == -1) {
			return;
		}
		this.scanner.getConnections().get(idx).disconnect();
		this.scanner.getConnections().remove(idx);
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
	private volatile boolean abort;
	private volatile boolean running; // 指的是机器有没有在运行，而不是其中线程有没有在运行。
	
	private volatile long permitIdle;
	
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
		serverMachineInfo = Globals.Datas.ServerMachine;
		if(connections == null) {
			connections = new ArrayList<Interfaces.IServerConnection>();
		}
		connections.clear();
		
		socket = null;
		abort = false;
		running = true;
		permitIdle = 60 * 60 * 1000;
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
				Socket clientSocket = socket.accept();
				Interfaces.IServerConnection connection = Factories.CommunicatorFactory.createServerConnection();
				connection.setSocket(clientSocket);
				connection.setServerMachineInfo(serverMachineInfo);
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
	public synchronized void removeIdleConnections() {
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
	public synchronized void removeAllConnections() {
		for(int i=this.connections.size()-1; i>=0; i--) {
			this.connections.get(i).disconnect();
			this.connections.remove(i);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

