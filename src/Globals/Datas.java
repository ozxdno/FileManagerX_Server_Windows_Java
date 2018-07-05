package Globals;

public class Datas {

	/**
	 * �����б�ÿ��Ĵ��󶼽������ڣ�����.log ���ļ��С�
	 * ·��Ϊ����δ����
	 * 
	 */
	public final static BasicCollections.Errors Errors = 
			new BasicCollections.Errors();
	
	/**
	 * ��֧�����ͣ����� Globals �з����ȡ
	 */
	public final static BasicCollections.Supports Supports = 
			new BasicCollections.Supports();
	
	/**
	 * ��̨�����Ļ�����Ϣ
	 * 
	 */
	public final static BasicModels.MachineInfo ThisMachine = 
			new BasicModels.MachineInfo();
	
	/**
	 * �û���Ϣ
	 * 
	 */
	public final static BasicModels.User ThisUser = 
			new BasicModels.User();
	
	/**
	 * �������Ļ�����Ϣ
	 * 
	 */
	public final static BasicModels.MachineInfo ServerMachine = 
			new BasicModels.MachineInfo();
	
	/**
	 * ���������û���Ϣ
	 * 
	 */
	public final static BasicModels.User ServerUser = 
			new BasicModels.User();
	
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
	public final static Interfaces.IDBManager DBManager = 
			Factories.DBManagerFactory.createDBManager();
	
	/**
	 * DBManager ��һ����չ��ʵ�ֿ�������ݷ��ʡ��Լ�һЩ�������ͨ��ʱ�������д��ʽ��
	 * 
	 * 
	 */
	public final static Interfaces.IGAPManager GAPManager = 
			Factories.DBManagerFactory.createGAPManager();
	/**
	 * һ��ר�������ӷ�������ͨ��ͨ���������� DBManager ���ơ�
	 * ͨ���������Interfaces.ICommands�����Է��ʣ�
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
	public final static Interfaces.IClientConnection ServerConnection = 
			Factories.CommunicatorFactory.createClientConnection();
	
	/**
	 * ���еı��� Depot �Ĺ�������Depot ����������Ϣ��
	 * 
	 * Folders;
	 * Files;
	 * Pixels;
	 * 
	 */
	public final static Interfaces.IDBManagers DBManagers = 
			Factories.DBManagerFactory.createDBManagers();
	/**
	 * ��ͬһ�������Ļ���
	 * 
	 */
	public final static java.util.List<Long> LANMachineIndexes = 
			new java.util.ArrayList<Long>();
	
	/**
	 * ��̨��������ͨ��ʱ����Ϊ��������ʹ��
	 * 
	 */
	public final static Interfaces.IServerScanner Server = 
			Factories.CommunicatorFactory.createServer();
	/**
	 * ��̨��������ͨ��ʱ����Ϊ�ͻ���ʹ��
	 * 
	 */
	public final static Interfaces.IClientLinker Client = 
			Factories.CommunicatorFactory.createClient();
	
	/**
	 * һ�����״��壬���ڲ��Ը������
	 * 
	 */
	public final static Main.MainForm Form_Main = 
			Factories.MainFactory.createMainForm();
	
	/**
	 * ���̣߳�������������������߼���
	 * 
	 */
	public final static Interfaces.IProcess pMain = 
			Factories.ProcessFactory.createProcessMain();
}
