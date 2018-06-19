package Globals;

public class Datas {

	public final static BasicCollections.Errors Errors = new BasicCollections.Errors();
	
	public final static Interfaces.IDBManager DBManager = new DataBaseManager.DBManager();
	public final static Interfaces.IDepotChecker DepotChecker = new DepotChecker.DepotChecker();
	public final static Interfaces.IDepotManager DepotManager = new DepotManager.DepotManager();
	
	public final static Interfaces.IServerScanner Server = new Communicator.ServerTCP();
	public final static Interfaces.IClientLinker Client = new Communicator.ClientTCP();
	
	public final static Processes.InitializeProcess pInitialize = new Processes.InitializeProcess();
	public final static Processes.ExitProcess pExit = new Processes.ExitProcess();
}
