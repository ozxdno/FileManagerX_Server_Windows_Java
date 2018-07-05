package Globals;

public final class Configurations {
	
	public static BasicEnums.StartType StartType = BasicEnums.StartType.Client;
	public static boolean IsServer = false;
	public static boolean IsDepot = false;
	public static boolean IsClient = false;
	
	public static long Next_FileIndex = 0;
	public static long Next_MachineIndex = 0;
	public static long Next_UserIndex = 0;
	public static long Next_DepotIndex = 0;
	public static long Next_DataBaseIndex = 0;
	
	public static long This_MachineIndex = 0;
	public static long This_UserIndex = 0;
	
	public static long Server_MachineIndex = 0;
	public static long Server_UserIndex = 0;
}
