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
	 * ���³�������Ĵ���
	 * 
	 */
	public final static BasicModels.Error Error = 
			new BasicModels.Error();
	
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
	 * ���в������б�
	 */
	public final static DepotManager.Operators Operators =
			new DepotManager.Operators();
	/**
	 * ���е�ƥ������б�
	 */
	public final static Match.Matches Matches = 
			new Match.Matches();
	
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
