package Globals;

public class Datas {

	/**
	 * 错误列表，每天的错误都将保存在：日期.log 的文件中。
	 * 路径为：暂未设置
	 * 
	 */
	public final static BasicCollections.Errors Errors = 
			new BasicCollections.Errors();
	
	/**
	 * 所支持类型，放于 Globals 中方便读取
	 */
	public final static BasicCollections.Supports Supports = 
			new BasicCollections.Supports();
	
	/**
	 * 
	 */
	public final static BasicModels.MachineInfo ThisMachine = 
			new BasicModels.MachineInfo();
	
	public final static BasicModels.User ThisUser = 
			new BasicModels.User();
	
	/**
	 * 对配置文件所在的数据库进行操作，包含以下表格：
	 * 
	 * MachineInfo;
	 * DepotInfo;
	 * DataBaseInfo;
	 * Users;
	 * Invitations;
	 * 
	 */
	public final static Interfaces.IDBManager DBManager = 
			new DataBaseManager.DBManager();
	
	/**
	 * 所有的本地 Depot 的管理器
	 * 
	 * 
	 */
	public final static Interfaces.IDBManagers DBManagers = 
			new DataBaseManager.DBManagers();
	
	/**
	 * 这台机器向外通信时，作为服务器端使用
	 * 
	 */
	public final static Interfaces.IServerScanner Server = 
			new Communicator.ServerTCP();
	/**
	 * 这台机器向外通信时，作为客户端使用
	 * 
	 */
	public final static Interfaces.IClientLinker Client = 
			new Communicator.ClientTCP();
	
	public final static Processes.InitializeProcess pInitialize = 
			new Processes.InitializeProcess();
	public final static Processes.ExitProcess pExit = 
			new Processes.ExitProcess();
}
