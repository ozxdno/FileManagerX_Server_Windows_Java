package com.FileManagerX.Globals;

public final class Datas {

	/**
	 * �����б�ÿ��Ĵ��󶼽������ڣ�����.log ���ļ��С�
	 * ·��Ϊ����δ����
	 * 
	 */
	public final static com.FileManagerX.BasicCollections.Errors Errors = 
			new com.FileManagerX.BasicCollections.Errors();
	
	public final static com.FileManagerX.BasicCollections.Records Records = 
			new com.FileManagerX.BasicCollections.Records();
	
	/**
	 * ���³�������Ĵ���
	 * 
	 */
	public final static com.FileManagerX.BasicModels.Error Error = 
			new com.FileManagerX.BasicModels.Error();
	
	/**
	 * ��֧�����ͣ����� Globals �з����ȡ
	 */
	public final static com.FileManagerX.BasicCollections.Supports Supports = 
			new com.FileManagerX.BasicCollections.Supports();
	/**
	 * ���̬��չ
	 */
	public final static java.util.HashMap<String, Class<?>> Commands =
			new java.util.HashMap<>();
	/**
	 * ��Ӧ��̬��չ
	 */
	public final static java.util.HashMap<String, Class<?>> Replies = 
			new java.util.HashMap<>();
	
	/**
	 * CFG �ļ�
	 * 
	 */
	public final static com.FileManagerX.FileModels.CFG CFG = 
			new com.FileManagerX.FileModels.CFG();
	
	/**
	 * ��̨�����Ļ�����Ϣ
	 * 
	 */
	public final static com.FileManagerX.BasicModels.MachineInfo ThisMachine = 
			com.FileManagerX.Factories.DefaultFactory.createDefaultMachineInfo();
	
	/**
	 * �û���Ϣ
	 * 
	 */
	public final static com.FileManagerX.BasicModels.User ThisUser = 
			new com.FileManagerX.BasicModels.User();
	
	/**
	 * �������Ļ�����Ϣ
	 * 
	 */
	public final static com.FileManagerX.BasicModels.MachineInfo ServerMachine = 
			com.FileManagerX.Factories.AncestorFactory.createAncestorMachine();
	
	/**
	 * ���������û���Ϣ
	 * 
	 */
	public final static com.FileManagerX.BasicModels.User ServerUser = 
			com.FileManagerX.Factories.AncestorFactory.createAncestorUser();
	
	/**
	 * �ҵ��豸
	 * 
	 */
	public final static com.FileManagerX.BasicCollections.MachineInfos MyMachines = 
			new com.FileManagerX.BasicCollections.MachineInfos();
	/**
	 * ����ͬһ����Ļ�������ֱ�����в�ͬ��ֱ���϶�����MyNet������ֱ���Ŀɷ��ʻ���Ҳ������MyNet��
	 * 
	 */
	public final static com.FileManagerX.MyNet.Net MyNet = 
			new com.FileManagerX.MyNet.Net();
	
	public final static com.FileManagerX.MyNet.Manager NetManager = 
			MyNet.getManager();
	
	/**
	 * �������ļ����ڵ����ݿ���в������������±��
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
	 * һ��ר�������ӷ�������ͨ��ͨ���������� DBManager ���ơ�
	 * ͨ���������com.FileManagerX.Interfaces.ICommands�����Է��ʣ�
	 * 
	 * MachineInfo;
	 * DepotInfo;
	 * DataBaseInfo;
	 * Users;
	 * Invitations;
	 * 
	 * ���⻹�У�
	 * 
	 * Folders;
	 * Files;
	 * Pixels;
	 * 
	 * �ȵȡ�
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IClientConnection ServerConnection = 
			com.FileManagerX.Factories.CommunicatorFactory.createClientConnection();
	
	/**
	 * �������õķ����������ӣ��������ϼ���������<br>
	 * <br>
	 * ��������һ���ӷ����� A �ʽ�����һ�������˵�����,<br>
	 * �÷�������ѯ��������������<br>
	 * 
	 */
	public final static com.FileManagerX.Communicator.ClientConnections OtherServers = 
			new com.FileManagerX.Communicator.ClientConnections();
	/**
	 * �÷������Ŀɴ�ͻ���
	 * 
	 */
	public final static com.FileManagerX.BasicCollections.MachineInfos OtherClients =
			new com.FileManagerX.BasicCollections.MachineInfos();
	/**
	 * ���еı��� Depot �Ĺ�������Depot ����������Ϣ��
	 * 
	 * Folders;
	 * Files;
	 * Pixels;
	 * 
	 */
	public final static com.FileManagerX.DataBase.Managers DBManagers = 
			new com.FileManagerX.DataBase.Managers();
	/**
	 * ɨ����
	 */
	public final static com.FileManagerX.Communicator.Scanners Scanners = 
			new com.FileManagerX.Communicator.Scanners();
	/**
	 * ��̨��������ͨ��ʱ����Ϊ��������ʹ��
	 * 
	 */
	public final static com.FileManagerX.Communicator.ServerConnections Server = 
			new com.FileManagerX.Communicator.ServerConnections();
	/**
	 * ��̨��������ͨ��ʱ����Ϊ�ͻ���ʹ��
	 * 
	 */
	public final static com.FileManagerX.Communicator.ClientConnections Client = 
			new com.FileManagerX.Communicator.ClientConnections();
	
	/**
	 * ��Ϣ���ͻ�����
	 * 
	 */
	public final static com.FileManagerX.Communicator.Sender Sender = 
			new com.FileManagerX.Communicator.Sender();
	/**
	 * ��Ϣ���ջ�����
	 * 
	 */
	public final static com.FileManagerX.Communicator.Receiver Receiver =
			new com.FileManagerX.Communicator.Receiver();
	
	/**
	 * ���в������б�
	 */
	public final static com.FileManagerX.Operator.Operators Operators =
			new com.FileManagerX.Operator.Operators();
	/**
	 * ���е��������
	 * 
	 */
	public final static com.FileManagerX.Executor.Executors Executors = 
			new com.FileManagerX.Executor.Executors();
	
	/**
	 * �����������е��̡߳�
	 */
	public final static com.FileManagerX.Processes.Manager<com.FileManagerX.Interfaces.IProcess> Processes =
			new com.FileManagerX.Processes.Manager<>();
	
	/**
	 * һ�����״��壬���ڲ��Ը������
	 * 
	 */
	public final static com.FileManagerX.Forms.Test Form_Test = 
			com.FileManagerX.Factories.FormFactory.createTest();
	
	/**
	 * ���̣߳�������������������߼���
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IProcess pMain = 
			com.FileManagerX.Factories.ProcessFactory.createMain();
}
