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
	 * 
	 */
	public final static BasicModels.MachineInfo ThisMachine = 
			new BasicModels.MachineInfo();
	
	public final static BasicModels.User ThisUser = 
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
			new DataBaseManager.DBManager();
	
	/**
	 * ���еı��� Depot �Ĺ�����
	 * 
	 * 
	 */
	public final static Interfaces.IDBManagers DBManagers = 
			new DataBaseManager.DBManagers();
	
	/**
	 * ��̨��������ͨ��ʱ����Ϊ��������ʹ��
	 * 
	 */
	public final static Interfaces.IServerScanner Server = 
			new Communicator.ServerTCP();
	/**
	 * ��̨��������ͨ��ʱ����Ϊ�ͻ���ʹ��
	 * 
	 */
	public final static Interfaces.IClientLinker Client = 
			new Communicator.ClientTCP();
	
	public final static Processes.InitializeProcess pInitialize = 
			new Processes.InitializeProcess();
	public final static Processes.ExitProcess pExit = 
			new Processes.ExitProcess();
}
