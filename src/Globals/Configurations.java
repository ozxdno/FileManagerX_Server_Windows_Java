package Globals;

public final class Configurations {
	
	public static BasicEnums.StartType StartType = BasicEnums.StartType.Client;
	public static BasicEnums.NetType NetType = BasicEnums.NetType.ONE_IN_WWW;
	
	public static boolean IsServer = false;
	public static boolean IsDepot = false;
	public static boolean IsClient = false;
	
	public static boolean ErrorOccurs = false;
	public static boolean Restart = true;
	public static boolean ShowForm = false;
	
	public static long Next_FileIndex = 0;
	public static long Next_MachineIndex = 0;
	public static long Next_UserIndex = 0;
	public static long Next_DepotIndex = 0;
	public static long Next_DataBaseIndex = 0;
	
	public static long This_MachineIndex = 0;
	public static long This_UserIndex = 0;
	
	public static long Server_MachineIndex = 0;
	public static long Server_UserIndex = 0;
	
	/**
	 * ��������ĵȴ�ʱ�䣬���ڴ�ʱ���ڻ�û���յ����ص�����򷵻س�ʱ����
	 */
	public static long TimeForCommandReceive = 2000;
	/**
	 * ��������ĵȴ�ʱ�䣬���ڴ�ʱ����û������ͳ�ȥ���򷵻س�ʱ����
	 */
	public static long TimeForCommandSend = 100;
	/**
	 * �����ʹ����ļ���������ô�ʱ�䡣���ѷϳ���
	 */
	public static long TimeForFileReceive = 5000;
	/**
	 * �ѷϳ�
	 */
	public static long TimeForFileSend = 500;
	/**
	 * �����ļ����ݵĵȴ�ʱ��
	 */
	public static long TimeForFileConnectorLoad = 2000;
	/**
	 * �������ݵ��ļ��ĵȴ�ʱ��
	 */
	public static long TimeForFileConnectorSave = 2000;
	/**
	 * �ڷ�������ǰ�����еȴ����öԷ����㹻ʱ�俪����������
	 */
	public static long TimeForInputFileWait = 1000;
	/**
	 * ���������Ƿ�����
	 */
	public static long TimeForTestReceiveCommand = 500;
	/**
	 * ���������Ƿ�����
	 */
	public static long TimeForTestSendCommand = 100;
	/**
	 * �����ȴ�ʱ��
	 */
	public static long TimeForRestart = 1*60*1000;
	/**
	 * Server Connection �������ʱ��
	 */
	public static long TimeForServerPermitIdle = Long.MAX_VALUE;
	/**
	 * Client Connection �������ʱ��
	 */
	public static long TimeForClientPermitIdle = Long.MAX_VALUE;
	/**
	 * �����Ĵ洢ʱ��
	 */
	public static long TimeForOperatorIdle = 1*60*1000;
	
	public static long TimeForMatchIdle = Long.MAX_VALUE;
	/**
	 * ��������ʱ��
	 */
	public static long TimeForPrintScreen = 1000;
}
