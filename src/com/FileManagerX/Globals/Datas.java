package com.FileManagerX.Globals;

public class Datas {

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
	 * CFG �ļ�
	 * 
	 */
	public final static com.FileManagerX.FileModels.CFG CFG = 
			new com.FileManagerX.FileModels.CFG(com.FileManagerX.Tools.Pathes.getFile_CFG());
	
	/**
	 * ��̨�����Ļ�����Ϣ
	 * 
	 */
	public final static com.FileManagerX.BasicModels.MachineInfo ThisMachine = 
			new com.FileManagerX.BasicModels.MachineInfo();
	
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
			com.FileManagerX.Factories.ServerFactory.createServerMachine();
	
	/**
	 * ���������û���Ϣ
	 * 
	 */
	public final static com.FileManagerX.BasicModels.User ServerUser = 
			com.FileManagerX.Factories.ServerFactory.createServerUser();
	
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
	 * �����̨�����Ƿ����������ֵ��Ч�������κ��ô���<br>
	 * �����һ̨�������Ļ�����������һ̨���÷�������<br>
	 * ����̨���������Ӵﵽ����ʱ�����ӽ�ת���÷�������<br>
	 * ������÷�����δ���У����޷�������������ӡ�
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IClientConnection NextServer = 
			com.FileManagerX.Factories.CommunicatorFactory.createClientConnection();
	/**
	 * ���еı��� Depot �Ĺ�������Depot ����������Ϣ��
	 * 
	 * Folders;
	 * Files;
	 * Pixels;
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IDBManagers DBManagers = 
			com.FileManagerX.Factories.DataBaseFactory.createManagers();
	/**
	 * ɨ����
	 */
	public final static com.FileManagerX.Interfaces.IScanner Scanner = 
			com.FileManagerX.Factories.CommunicatorFactory.createScanner();
	/**
	 * ��̨��������ͨ��ʱ����Ϊ��������ʹ��
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IServerConnections Server = 
			com.FileManagerX.Factories.CommunicatorFactory.createServerConnections();
	/**
	 * ��̨��������ͨ��ʱ����Ϊ�ͻ���ʹ��
	 * 
	 */
	public final static com.FileManagerX.Interfaces.IClientConnections Client = 
			com.FileManagerX.Factories.CommunicatorFactory.createClientConnections();
	
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
	public final static com.FileManagerX.Interfaces.IProcessManager Processes =
			com.FileManagerX.Factories.ProcessFactory.createManager();
	
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
