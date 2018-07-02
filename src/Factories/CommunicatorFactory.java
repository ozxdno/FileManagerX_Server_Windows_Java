package Factories;

public class CommunicatorFactory {

	public static final Interfaces.IServerScanner createServer() {
		return new Communicator.ServerTCP();
	}
	
	public static final Interfaces.IServerConnection createServerConnection() {
		return new Communicator.ServerConnection();
	}
	
	public static final Interfaces.IClientLinker createClient() {
		return new Communicator.ClientTCP();
	}
	
	public static final Interfaces.IClientConnection createClientConnection() {
		return new Communicator.ClientConnection();
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
}
