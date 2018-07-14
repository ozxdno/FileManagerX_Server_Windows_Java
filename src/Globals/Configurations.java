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
	 * 接收命令的等待时间，若在此时间内还没接收到返回的命令，则返回超时错误。
	 */
	public static long TimeForCommandReceive = 2000;
	/**
	 * 发送命令的等待时间，若在此时间内没把命令发送出去，则返回超时错误。
	 */
	public static long TimeForCommandSend = 100;
	/**
	 * 若发送传送文件的命令，则用此时间。（已废除）
	 */
	public static long TimeForFileReceive = 5000;
	/**
	 * 已废除
	 */
	public static long TimeForFileSend = 500;
	/**
	 * 加载文件数据的等待时间
	 */
	public static long TimeForFileConnectorLoad = 2000;
	/**
	 * 保存数据到文件的等待时间
	 */
	public static long TimeForFileConnectorSave = 2000;
	/**
	 * 在发送数据前，进行等待，让对方有足够时间开启接收器。
	 */
	public static long TimeForInputFileWait = 1000;
	/**
	 * 测试连接是否正常
	 */
	public static long TimeForTestReceiveCommand = 500;
	/**
	 * 测试连接是否正常
	 */
	public static long TimeForTestSendCommand = 100;
	/**
	 * 重启等待时间
	 */
	public static long TimeForRestart = 1*60*1000;
	/**
	 * Server Connection 允许空闲时间
	 */
	public static long TimeForServerPermitIdle = Long.MAX_VALUE;
	/**
	 * Client Connection 允许空闲时间
	 */
	public static long TimeForClientPermitIdle = Long.MAX_VALUE;
	/**
	 * 操作的存储时间
	 */
	public static long TimeForOperatorIdle = 1*60*1000;
	
	public static long TimeForMatchIdle = Long.MAX_VALUE;
	/**
	 * 截屏所需时间
	 */
	public static long TimeForPrintScreen = 1000;
}
