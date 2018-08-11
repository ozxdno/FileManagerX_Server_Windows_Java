package com.FileManagerX.Globals;

public final class Configurations {
	
	public static com.FileManagerX.BasicEnums.StartType StartType = com.FileManagerX.BasicEnums.StartType.Client;
	public static com.FileManagerX.BasicEnums.NetType NetType = com.FileManagerX.BasicEnums.NetType.ONE_IN_WWW;
	
	public static boolean IsServer = false;
	public static boolean IsDepot = false;
	public static boolean IsClient = false;
	
	public static boolean ErrorOccurs = false;
	public static boolean Restart = true;
	public static boolean Close = false;
	public static boolean ShowForm = false;
	public static boolean Record = false;
	
	public static long Next_FileIndex = 0;
	public static long Next_MachineIndex = 0;
	public static long Next_UserIndex = 0;
	public static long Next_DepotIndex = 0;
	public static long Next_DataBaseIndex = 0;
	public static long Next_GroupIndex = 0;
	public static long Next_ChatIndex = 0;
	
	public static long This_MachineIndex = 0;
	public static long This_UserIndex = 0;
	
	public static long Server_MachineIndex = 1;
	public static long Server_UserIndex = 1;
	
	public static long DataBaseQueryLimit = 1000;
	public static long MaxConnectionFlow = 2048;
	public static long ConnectionLimit = 100;
	public static long NetDepth = 1;
	
	public static long TimeForCommandReceive = 2000;
	public static long TimeForCommandSend = 100;
	public static long TimeForRestart = 1*60*1000;
	public static long TimeForServerPermitIdle = Long.MAX_VALUE;
	public static long TimeForClientPermitIdle = 5*1000;
	public static long TimeForOperatorIdle = 1*60*1000;
	public static long TimeForExecutorIdle = 1*60*1000;
}
