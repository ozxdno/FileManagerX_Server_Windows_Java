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
	 * 本台机器的机器信息
	 * 
	 */
	public final static BasicModels.MachineInfo ThisMachine = 
			new BasicModels.MachineInfo();
	
	/**
	 * 用户信息
	 * 
	 */
	public final static BasicModels.User ThisUser = 
			new BasicModels.User();
	
	/**
	 * 服务器的机器信息
	 * 
	 */
	public final static BasicModels.MachineInfo ServerMachine = 
			new BasicModels.MachineInfo();
	
	/**
	 * 服务器的用户信息
	 * 
	 */
	public final static BasicModels.User ServerUser = 
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
			Factories.DBManagerFactory.createDBManager();
	
	/**
	 * DBManager 的一个扩展，实现跨电脑数据访问。以及一些与服务器通信时命令的缩写形式。
	 * 
	 * 
	 */
	public final static Interfaces.IGAPManager GAPManager = 
			Factories.DBManagerFactory.createGAPManager();
	/**
	 * 一条专用于连接服务器的通信通道，功能与 DBManager 相似。
	 * 通过各类命令（Interfaces.ICommands）可以访问：
	 * 
	 * MachineInfo;
	 * DepotInfo;
	 * DataBaseInfo;
	 * Users;
	 * Invitations;
	 * 
	 * 此外还有：
	 * 
	 * Folders;
	 * Files;
	 * Pixels;
	 * 
	 * 等等。
	 * 
	 */
	public final static Interfaces.IClientConnection ServerConnection = 
			Factories.CommunicatorFactory.createClientConnection();
	
	/**
	 * 所有的本地 Depot 的管理器，Depot 包含如下信息：
	 * 
	 * Folders;
	 * Files;
	 * Pixels;
	 * 
	 */
	public final static Interfaces.IDBManagers DBManagers = 
			Factories.DBManagerFactory.createDBManagers();
	/**
	 * 在同一局域网的机器
	 * 
	 */
	public final static java.util.List<Long> LANMachineIndexes = 
			new java.util.ArrayList<Long>();
	
	/**
	 * 这台机器向外通信时，作为服务器端使用
	 * 
	 */
	public final static Interfaces.IServerScanner Server = 
			Factories.CommunicatorFactory.createServer();
	/**
	 * 这台机器向外通信时，作为客户端使用
	 * 
	 */
	public final static Interfaces.IClientLinker Client = 
			Factories.CommunicatorFactory.createClient();
	
	/**
	 * 一个简易窗体，用于测试各个命令。
	 * 
	 */
	public final static Main.MainForm Form_Main = 
			Factories.MainFactory.createMainForm();
	
	/**
	 * 主线程，控制整个程序的运行逻辑。
	 * 
	 */
	public final static Interfaces.IProcess pMain = 
			Factories.ProcessFactory.createProcessMain();
}
