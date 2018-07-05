package Communicator;

public class CommandConnector implements Interfaces.ICommandConnector {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean executeCommand;
	private boolean executeReply;
	
	private long sourMachine;
	private long destMachine;
	private BasicModels.MachineInfo sourMachineInfo;
	private BasicModels.MachineInfo destMachineInfo;
	
	private String receiveCommand;
	private String sendCommand;
	
	private Interfaces.IConnection sourConnection;
	private Interfaces.IConnection destConnection;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIsExecuteCommand(boolean executeCommand) {
		this.executeCommand = executeCommand;
		this.executeReply = !this.executeCommand;
		return true;
	}
	public boolean setIsExecuteReply(boolean executeReply) {
		this.executeReply = executeReply;
		this.executeCommand = !this.executeReply;
		return true;
	}
	public boolean setSourMachineIndex(long index) {
		if(index < 0) {
			return false;
		}
		this.sourMachine = index;
		return true;
	}
	public boolean setDestMachineIndex(long index) {
		if(index < 0) {
			return false;
		}
		this.destMachine = index;
		return true;
	}
	public boolean setSourMachineInfo(BasicModels.MachineInfo machine) {
		if(machine == null) {
			return false;
		}
		this.sourMachineInfo = machine;
		this.sourMachine = this.sourMachineInfo.getIndex();
		return true;
	}
	public boolean setDestMachineInfo(BasicModels.MachineInfo machine) {
		if(machine == null) {
			return false;
		}
		this.destMachineInfo = machine;
		this.destMachine = this.destMachineInfo.getIndex();
		return true;
	}
	public boolean setSourMachineInfo() {
		BasicModels.MachineInfo machine = Globals.Datas.DBManager.QueryMachineInfo("[&] Index = " + this.sourMachine);
		if(machine == null) {
			return false;
		}
		this.sourMachineInfo = machine;
		this.sourMachine = this.sourMachineInfo.getIndex();
		return true;
	}
	public boolean setDestMachineInfo() {
		BasicModels.MachineInfo machine = Globals.Datas.DBManager.QueryMachineInfo("[&] Index = " + this.destMachine);
		if(machine == null) {
			return false;
		}
		this.destMachineInfo = machine;
		this.destMachine = this.destMachineInfo.getIndex();
		return true;
	}
	public boolean setReceiveCommand(String receiveCommand) {
		if(receiveCommand == null || receiveCommand.length() == 0) {
			return false;
		}
		this.receiveCommand = receiveCommand;
		return true;
	}
	public boolean setSendCommand(String sendCommand) {
		if(sendCommand == null || sendCommand.length() == 0) {
			return false;
		}
		this.sendCommand = sendCommand;
		return true;
	}
	public boolean setSourConnection(Interfaces.IConnection connection) {
		if(connection == null || !connection.isRunning()) {
			return false;
		}
		this.sourConnection = connection;
		return true;
	}
	public boolean setDestConnection(Interfaces.IConnection connection) {
		if(connection == null || !connection.isRunning()) {
			return false;
		}
		this.sourConnection = connection;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isExecuteCommand() {
		return this.executeCommand;
	}
	public boolean isExecuteReply() {
		return this.executeReply;
	}
	public long getSourMachineIndex() {
		return this.sourMachine;
	}
	public long getDestMachineIndex() {
		return this.destMachine;
	}
	public BasicModels.MachineInfo getSourMachineInfo() {
		return this.sourMachineInfo;
	}
	public BasicModels.MachineInfo getDestMachineInfo() {
		return this.destMachineInfo;
	}
	public String getReceiveCommand() {
		return this.receiveCommand;
	}
	public String getSendCommand() {
		return this.sendCommand;
	}
	public Interfaces.IConnection getSourConnection() {
		return this.sourConnection;
	}
	public Interfaces.IConnection getDestConnection() {
		return this.destConnection;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CommandConnector() {
		initThis();
	}
	private void initThis() {
		this.executeCommand = false;
		this.executeReply = false;
		this.sourMachine = Globals.Configurations.This_MachineIndex;
		this.destMachine = 0;
		this.sourMachineInfo = Globals.Datas.ThisMachine;
		this.destMachineInfo = new BasicModels.MachineInfo();
		this.receiveCommand = "";
		this.sendCommand = "";
		this.sourConnection = null;
		this.destConnection = null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.sourMachineInfo.getName() + " -> " + this.destMachineInfo.getName();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		
		c.addToBottom(this.executeCommand);
		c.addToBottom(this.executeReply);
		c.addToBottom(this.sourMachine);
		c.addToBottom(this.destMachine);
		
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		this.executeCommand = c.fetchFirstBoolean();
		this.executeReply = c.fetchFirstBoolean();
		this.sourMachine = c.fetchFirstLong();
		this.destMachine = c.fetchFirstLong();
		
		this.setSourMachineInfo();
		this.setDestMachineInfo();
		
		return c.output();
	}
	public void copyReference(Object o) {
		CommandConnector cc = (CommandConnector)o;
		this.executeCommand = cc.executeCommand;
		this.executeReply = cc.executeReply;
		this.sourMachine = cc.sourMachine;
		this.destMachine = cc.destMachine;
		this.sourMachineInfo = cc.sourMachineInfo;
		this.destMachineInfo = cc.destMachineInfo;
		this.receiveCommand = cc.receiveCommand;
		this.sendCommand = cc.sendCommand;
		this.sourConnection = cc.sourConnection;
		this.destConnection = cc.destConnection;
	}
	public void copyValue(Object o) {
		CommandConnector cc = (CommandConnector)o;
		this.executeCommand = cc.executeCommand;
		this.executeReply = cc.executeReply;
		this.sourMachine = cc.sourMachine;
		this.destMachine = cc.destMachine;
		this.sourMachineInfo.copyValue(cc.sourMachineInfo);
		this.destMachineInfo.copyValue(cc.destMachineInfo);
		this.receiveCommand = cc.receiveCommand;
		this.sendCommand = cc.sendCommand;
		this.sourConnection = cc.sourConnection;
		this.destConnection = cc.destConnection;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Interfaces.IReplies execute() {
		
		if(this.sourMachineInfo == null || this.sourMachine != this.sourMachineInfo.getIndex()) {
			this.setSourMachineInfo(); }
		if(this.destMachineInfo == null || this.destMachine != this.destMachineInfo.getIndex()) {
			this.setDestMachineInfo(); }
		if(this.sourMachineInfo == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("SourMachineInfo is NULL");
			return null;
		}
		if(this.destMachineInfo == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("DestMachineInfo is NULL");
			return null;
		}
		
		if(this.sourConnection == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("SourConnection is NULL");
			return null;
		}
		if(this.destConnection == null) {
			for(int i=0; i<Globals.Datas.Client.getConnections().size(); i++) {
				Interfaces.IConnection con = Globals.Datas.Client.getConnections().get(i);
				if(con.getServerMachineInfo().getName().equals(this.destMachineInfo.getName()) &&
						con.getType().equals(BasicEnums.ConnectionType.TRANSPORT_COMMAND)) {
					this.destConnection = con;
					break;
				}
			}
			if(this.destConnection == null) {
				Interfaces.IConnection con = Factories.CommunicatorFactory.createRunningClientConnection(
						destMachineInfo, 
						sourMachineInfo,
						this.sourConnection.getUser()
						);
				if(con == null) {
					return null;
				}
				Globals.Datas.Client.add(con);
				this.destConnection = con;
			}
		}
		if(this.destConnection  == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("DestConnection is NULL");
			return null;
		}
		if(!this.destConnection.isRunning()) {
			BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register("DestConnection is Closed[Runnig = false]");
			return null;
		}
		
		Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
		swre.setConnection(this.destConnection);
		return swre.execute(sendCommand);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
