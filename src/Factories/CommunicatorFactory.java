package Factories;

public class CommunicatorFactory {

	public static final Interfaces.IServerScanner createServer() {
		return new Communicator.ServerTCP();
	}
	
	public static final Interfaces.IServerConnection createServerConnection() {
		Interfaces.IServerConnection con = new Communicator.ServerConnection();
		//con.setIndex();
		return con;
	}
	
	public static final Interfaces.IClientLinker createClient() {
		return new Communicator.ClientTCP();
	}
	
	public static final Interfaces.IClientConnection createClientConnection() {
		Interfaces.IClientConnection con = new Communicator.ClientConnection();
		//con.setIndex();
		return con;
	}
	public static final Interfaces.IClientConnection createRunningClientConnection(
			BasicModels.MachineInfo serverMachine,
			BasicModels.MachineInfo clientMachine,
			BasicModels.User user) {
		
		Interfaces.IClientConnection con = createClientConnection();
		con.setServerMachineInfo(serverMachine);
		con.setClientMachineInfo(clientMachine);
		con.setUser(user);
		con.setSocket();
		con.connect();
		if(!con.isRunning()) {
			return null;
		}
		
		Interfaces.ISWRE swre = createSWRE();
		swre.setConnection(con);
		
		Commands.LoginUser lu = new Commands.LoginUser();
		lu.getBasicMessagePackage().setSourUserIndex(user.getIndex());
		lu.getBasicMessagePackage().setPassword(user.getPassword());
		lu.setLoginName(user.getLoginName());
		Replies.LoginUser replu = (Replies.LoginUser)swre.execute(lu.output());
		if(replu == null) {
			return null;
		}
		if(!replu.isOK()) {
			return null;
		}
		
		Commands.LoginMachine lm = new Commands.LoginMachine();
		lm.getBasicMessagePackage().setSourUserIndex(user.getIndex());
		lm.getBasicMessagePackage().setPassword(user.getPassword());
		lm.getBasicMessagePackage().setSourMachineIndex(clientMachine.getIndex());
		Replies.LoginMachine replm = (Replies.LoginMachine)swre.execute(lm.output());
		if(replm == null) {
			return null;
		}
		if(!replm.isOK()) {
			return null;
		}
		
		return con;
	}
	
	public static final Interfaces.IClientConnection createRunningClientConnection(
			long serverMachine,
			long clientMachine,
			long user) {
		
		BasicModels.MachineInfo sv = Globals.Datas.ServerMachine;
		BasicModels.MachineInfo ce = Globals.Datas.ThisMachine;
		BasicModels.User u = Globals.Datas.ThisUser;
		
		if(serverMachine != sv.getIndex()) {
			sv = Globals.Datas.GAPManager.QeuryMachineInfo("[&] Index = " + serverMachine);
			if(sv == null) {
				return null;
			}
		}
		if(clientMachine != ce.getIndex()) {
			ce = Globals.Datas.GAPManager.QeuryMachineInfo("[&] Index = " + clientMachine);
			if(ce == null) {
				return null;
			}
		}
		if(user != u.getIndex()) {
			u = Globals.Datas.GAPManager.QeuryUser("[&] Index = " + user);
			if(u == null) {
				return null;
			}
		}
		
		Interfaces.IClientConnection con = createClientConnection();
		con.setServerMachineInfo(sv);
		con.setClientMachineInfo(ce);
		con.setUser(u);
		con.setSocket();
		con.connect();
		if(!con.isRunning()) {
			return null;
		}
		
		if(!Globals.Datas.GAPManager.loginUser(con)) {
			return null;
		}
		if(!Globals.Datas.GAPManager.loginMachine(con)) {
			return null;
		}
		if(!Globals.Datas.GAPManager.loginConnection(con)) {
			return null;
		}
		
		return con;
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
	public static final Interfaces.IFSWRE createFSWRE() {
		return new Communicator.FSWRE();
	}
	public static final Interfaces.IDSWRE createDSWRE() {
		return new Communicator.DSWRE();
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
