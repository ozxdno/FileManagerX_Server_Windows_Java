package Factories;

public class CommunicatorFactory {

	public static final Interfaces.IServerScanner createServer() {
		return new Communicator.ServerTCP();
	}
	
	public static final Interfaces.IServerConnection createServerConnection() {
		Interfaces.IServerConnection con = new Communicator.ServerConnection();
		if(Globals.Datas.Server != null) {
			con.setIndex();
			Globals.Datas.Server.add(con);
		}
		return con;
	}
	
	public static final Interfaces.IClientLinker createClient() {
		return new Communicator.ClientTCP();
	}
	
	public static final Interfaces.IClientConnection createClientConnection() {
		Interfaces.IClientConnection con = new Communicator.ClientConnection();
		if(Globals.Datas.Client != null) {
			Globals.Datas.Client.add(con);
			con.setIndex();
		}
		return con;
	}
	public static final Interfaces.IClientConnection createRunningClientConnection(
			BasicModels.MachineInfo serverMachine,
			BasicModels.MachineInfo clientMachine) {
		
		Interfaces.IClientConnection con = createClientConnection();
		con.setServerMachineInfo(serverMachine);
		con.setClientMachineInfo(clientMachine);
		con.setSocket();
		con.connect();
		if(!con.isRunning()) {
			return null;
		}
		
		return con;
	}
	public static final Interfaces.IClientConnection createRunningClientConnection(
			long serverMachine,
			long clientMachine) {
		
		BasicModels.MachineInfo sv = Globals.Datas.ServerMachine;
		BasicModels.MachineInfo ce = Globals.Datas.ThisMachine;
		
		if(serverMachine != sv.getIndex()) {
			sv = Globals.Datas.ServerConnection.getCommandsManager().queryMachine("[&] Index = " + serverMachine);
			if(sv == null) {
				return null;
			}
		}
		if(clientMachine != ce.getIndex()) {
			ce = Globals.Datas.ServerConnection.getCommandsManager().queryMachine("[&] Index = " + clientMachine);
			if(ce == null) {
				return null;
			}
		}
		
		Interfaces.IClientConnection con = createClientConnection();
		con.setServerMachineInfo(sv);
		con.setClientMachineInfo(ce);
		con.setSocket();
		con.connect();
		if(!con.isRunning()) {
			return null;
		}
		
		return con;
	}
	public static final Interfaces.IClientConnection createRunningClientConnection() {
		return createRunningClientConnection(
				Globals.Datas.ServerMachine,
				Globals.Datas.ThisMachine
				);
	}
	
	public static final Interfaces.ICommandExecutor createServerExcutor() {
		return new Communicator.ServerExecutor();
	}
	
	public static final Interfaces.IReplyExecutor createClientExcutor() {
		return new Communicator.ClientExecutor();
	}
	
	public static final Interfaces.ISWRE createSWRE() {
		return new Communicator.SWRE();
	}
	public static final Interfaces.IRESW createRESW() {
		return new Communicator.RESW();
	}
	
	public static final Interfaces.ICommunicatorReceivePart createReceivePart() {
		return new Communicator.ReceivePart();
	}
	
	public static final Interfaces.ICommunicatorSendTotal createSendTotal() {
		return new Communicator.SendTotal();
	}
	
	public static final Interfaces.IFileConnector createFileConnector() {
		return new Communicator.FileConnector();
	}
	public static final Interfaces.ICommandConnector createCommandConnector() {
		return new Communicator.CommandConnector();
	}
	
	public static final Interfaces.IBasicMessagePackage createBasicMessagePackage() {
		return new Communicator.BasicMessagePackage();
	}
	
}
