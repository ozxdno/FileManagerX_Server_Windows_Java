package Globals;

public class Datas {

	public final static BasicCollections.Errors Errors = new BasicCollections.Errors();
	
	public final static Communicator.IServerScanner Server = new Communicator.ServerTCP();
	public final static Communicator.IClientLinker Client = new Communicator.ClientTCP();
	
	public final static Processes.InitializeProcess pInitialize = new Processes.InitializeProcess();
	public final static Processes.ExitProcess pExit = new Processes.ExitProcess();
}
