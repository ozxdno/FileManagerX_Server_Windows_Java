package Communicator;

public class ClientTCP implements IClientLinker{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private BasicModels.MachineInfo clientMachineInfo;
	private BasicModels.User user;
	private ClientConnection connection;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo) {
		if(serverMachineInfo == null) {
			return false;
		}
		this.serverMachineInfo = serverMachineInfo;
		return true;
	}
	public boolean setClientMachineInfo(BasicModels.MachineInfo clientMachineInfo) {
		if(clientMachineInfo == null) {
			return false;
		}
		this.clientMachineInfo = clientMachineInfo;
		return true;
	}
	public boolean setUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	public boolean setConnection(ClientConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BasicModels.MachineInfo getServerMachineInfo() {
		return this.serverMachineInfo;
	}
	public BasicModels.MachineInfo getClientMachineInfo() {
		return this.clientMachineInfo;
	}
	public BasicModels.User getUser() {
		return this.user;
	}
	public ClientConnection getConnection() {
		return this.connection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ClientTCP() {
		initThis();
	}
	public ClientTCP(BasicModels.MachineInfo serverMachineInfo, BasicModels.MachineInfo clientMachineInfo) {
		initThis();
		this.setServerMachineInfo(serverMachineInfo);
		this.setClientMachineInfo(clientMachineInfo);
	}
	private void initThis() {
		serverMachineInfo = null;
		clientMachineInfo = null;
		user = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isRunning() {
		if(this.connection == null) {
			return false;
		}
		return this.connection.isRunning();
	}
	
	public boolean initialize(Object infos) {
		BasicModels.MachineInfo[] ms = (BasicModels.MachineInfo[])infos;
		if(ms.length != 2) {
			return false;
		}
		
		this.setServerMachineInfo(ms[0]);
		this.setClientMachineInfo(ms[1]);
		return true;
	}
	
	public boolean connect() {
		disconnect();
		try {
			java.net.InetAddress ip = java.net.InetAddress.getByName(this.serverMachineInfo.getIp());
			int port = this.serverMachineInfo.getPort();
			java.net.Socket s = new java.net.Socket(ip,port);
			this.connection = new ClientConnection();
			this.connection.setServerMachineInfo(serverMachineInfo);
			this.connection.setClientMachineInfo(clientMachineInfo);
			this.connection.setSocket(s);
			this.connection.start();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public void disconnect() {
		if(this.connection == null) {
			return;
		}
		this.connection.disconnect();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
