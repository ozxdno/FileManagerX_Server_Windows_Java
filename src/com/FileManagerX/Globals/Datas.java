package com.FileManagerX.Globals;

public class Datas {

	/**
	 * 错误列表，每天的错误都将保存在：日期.log 的文件中。
	 * 路径为：暂未设置
	 * 
	 */
	public final static com.FileManagerX.BasicCollections.Errors Errors = 
			new com.FileManagerX.BasicCollections.Errors();
	
	public final static com.FileManagerX.BasicCollections.Records Records = 
			new com.FileManagerX.BasicCollections.Records();
	
	/**
	 * 导致程序崩溃的错误
	 * 
	 */
	public final static com.FileManagerX.BasicModels.Error Error = 
			new com.FileManagerX.BasicModels.Error();
	
	/**
	 * 所支持类型，放于 Globals 中方便读取
	 */
	public final static com.FileManagerX.BasicCollections.Supports Supports = 
			new com.FileManagerX.BasicCollections.Supports();
	
	/**
	 * CFG 文件
	 * 
	 */
	public final static com.FileManagerX.FileModels.CFG CFG = 
			new com.FileManagerX.FileModels.CFG(com.FileManagerX.Tools.Pathes.getFile_CFG());
	
	/**
	 * 本台机器的机器信息
	 * 
	 */
	public final static com.FileManagerX.BasicModels.MachineInfo ThisMachine = 
			new com.FileManagerX.BasicModels.MachineInfo();
	
	/**
	 * 用户信息
	 * 
	 */
	public final static com.FileManagerX.BasicModels.User ThisUser = 
			new com.FileManagerX.BasicModels.User();
	
	/**
	 * 服务器的机器信息
	 * 
	 */
	public final static com.FileManagerX.BasicModels.MachineInfo ServerMachine = 
			com.FileManagerX.Factories.ServerFactory.createServerMachine();
	
	/**
	 * 服务器的用户信息
	 * 
	 */
	public final static com.FileManagerX.BasicModels.User ServerUser = 
			com.FileManagerX.Factories.ServerFactory.createServerUser();
	
	/**
	 * 我的设备
	 * 
	 */
	public final static com.FileManagerX.BasicCollections.MachineInfos MyMachines = 
			new com.FileManagerX.BasicCollections.MachineInfos();
	/**
	 * 处于同一网络的机器（与直连稍有不同，直连肯定属于MyNet，但非直连的可访问机器也可属于MyNet）
	 * 
	 */
	public final static com.FileManagerX.MyNet.Net MyNet = 
			new com.FileManagerX.MyNet.Net();
	
	public final static com.FileManagerX.MyNet.Manager NetManager = 
			MyNet.getManager();
	
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
	public final static com.FileManagerX.Interfaces.IDBManager DBManager = 
			com.FileManagerX.Factories.DataBaseFactory.createManager();
	
	/**
	 * 一条专用于连接服务器的通信通道，功能与 DBManager 相似。
	 * 通过各类命令（com.FileManagerX.Interfaces.ICommands）可以访问：
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
	public final static com.FileManagerX.Interfaces.IClientConnection ServerConnection = 
			com.FileManagerX.Factories.CommunicatorFactory.createClientConnection();
	
	/**
	 * 如果这台机器非服务器，则此值无效。（无任何用处）<br>
	 * 如果是一台服务器的话，则连接下一台备用服务器。<br>
	 * 当这台服务器连接达到上限时，连接将转向备用服务器。<br>
	 * 如果备用服务器未运行，则无法创建更多的连接。
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IClientConnection NextServer = 
			com.FileManagerX.Factories.CommunicatorFactory.createClientConnection();
	/**
	 * 所有的本地 Depot 的管理器，Depot 包含如下信息：
	 * 
	 * Folders;
	 * Files;
	 * Pixels;
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IDBManagers DBManagers = 
			com.FileManagerX.Factories.DataBaseFactory.createManagers();
	/**
	 * 扫描器
	 */
	public final static com.FileManagerX.Interfaces.IScanner Scanner = 
			com.FileManagerX.Factories.CommunicatorFactory.createScanner();
	/**
	 * 这台机器向外通信时，作为服务器端使用
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IServerConnections Server = 
			com.FileManagerX.Factories.CommunicatorFactory.createServerConnections();
	/**
	 * 这台机器向外通信时，作为客户端使用
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IClientConnections Client = 
			com.FileManagerX.Factories.CommunicatorFactory.createClientConnections();
	
	/**
	 * 所有操作的列表
	 */
	public final static com.FileManagerX.Operator.Operators Operators =
			new com.FileManagerX.Operator.Operators();
	/**
	 * 所有的命令处理器
	 * 
	 */
	public final static com.FileManagerX.Executor.Executors Executors = 
			new com.FileManagerX.Executor.Executors();
	
	/**
	 * 所有正在运行的线程。
	 */
	public final static com.FileManagerX.Interfaces.IProcessManager Processes =
			com.FileManagerX.Factories.ProcessFactory.createManager();
	
	/**
	 * 一个简易窗体，用于测试各个命令。
	 * 
	 */
	public final static com.FileManagerX.Forms.Test Form_Test = 
			com.FileManagerX.Factories.FormFactory.createTest();
	
	/**
	 * 主线程，控制整个程序的运行逻辑。
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IProcess pMain = 
			com.FileManagerX.Factories.ProcessFactory.createMain();
}
