package com.FileManagerX.Globals;

public final class Configurations {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static com.FileManagerX.BasicEnums.MachineType MachineType =
			com.FileManagerX.BasicEnums.MachineType.TEMPORARY;
	public static com.FileManagerX.BasicEnums.NetType NetType =
			com.FileManagerX.BasicEnums.NetType.ONE_IN_WWW;
	
	public static boolean IsAncestor = false;
	public static boolean IsServer = false;
	public static boolean IsDepot = false;
	public static boolean IsClient = false;
	public static boolean IsTemporary = false;
	
	public static boolean ErrorOccurs = false;
	public static boolean Restart = false;
	public static boolean Close = false;
	public static boolean ShowForm = false;
	public static boolean Record = true;
	public static boolean Refresh = false;
	
	public static long Next_FolderIndex = 0;
	public static long Next_FileIndex = 0;
	public static long Next_MachineIndex = 0;
	public static long Next_UserIndex = 0;
	public static long Next_DepotIndex = 0;
	public static long Next_DataBaseIndex = 0;
	public static long Next_GroupIndex = 0;
	public static long Next_ChatIndex = 0;
	public static long Next_ConnectionIndex = 0;
	public static long Next_ProcessIndex = 0;
	public static long Next_OperatorIndex = 0;
	public static long Next_ExecutorIndex = 0;
	public static long Next_ScannerIndex = 0;
	public static long Next_CFGIndex = 0;
	
	public static long This_MachineIndex = 0;
	public static long This_UserIndex = 0;
	
	public static long Server_MachineIndex = 1;
	public static long Server_UserIndex = 1;
	
	public static long NetDepth = 1;
	
	public static int LimitForConnection = 2;
	public static int LimitForClient = 1000;
	public static int LimitForNetGroup = 100;
	public static int LimitForGroupUser = 100;
	public static int LimitForUserMachine = 100;
	public static int LimitForMachineDepot = 100;
	public static int LimitForIOPBuffer = 2048;
	public static int LimitForConnectionBuffer = 2048;
	public static int LimitForJDBC = 10;
	public static int LimitForQuerySize = 1000;
	
	public static long TimeForPermitIdle_Restart = 1*60*1000;
	public static long TimeForPermitIdle_Transport = 5*1000*3600;
	public static long TimeForPermitIdle_Thread = 5*1000;
	public static long TimeForPermitIdle_Scanner = Long.MAX_VALUE;
	public static long TimeForPermitIdle_Server = 5*1000;
	public static long TimeForPermitIdle_Client = 5*1000;
	public static long TimeForPermitIdle_Executor = 5*1000;
	public static long TimeForPermitIdle_Operator = 5*1000;
		
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized final static long Next_FolderIndex() {
		return ++Next_FolderIndex;
	}
	public synchronized final static long Next_FileIndex() {
		return ++Next_FileIndex;
	}
	public synchronized final static long Next_MachineIndex() {
		return ++Next_MachineIndex;
	}
	public synchronized final static long Next_DepotIndex() {
		return ++Next_DepotIndex;
	}
	public synchronized final static long Next_DataBaseIndex() {
		return ++Next_DataBaseIndex;
	}
	public synchronized final static long Next_UserIndex() {
		return ++Next_UserIndex;
	}
	public synchronized final static long Next_GroupIndex() {
		return ++Next_GroupIndex;
	}
	public synchronized final static long Next_ChatIndex() {
		return ++Next_ChatIndex;
	}
	public synchronized final static long Next_ConnectionIndex() {
		return ++Next_ConnectionIndex;
	}
	public synchronized final static long Next_ProcessIndex() {
		return ++Next_ProcessIndex;
	}
	public synchronized final static long Next_OperatorIndex() {
		return ++Next_OperatorIndex;
	}
	public synchronized final static long Next_ExecutorIndex() {
		return ++Next_ExecutorIndex;
	}
	public synchronized final static long Next_ScannerIndex() {
		return ++Next_ScannerIndex;
	}
	public synchronized final static long Next_CFGIndex() {
		return ++Next_CFGIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized final static long setNext_FolderIndex(long index) {
		Next_FolderIndex = Next_FolderIndex > index ? Next_FolderIndex : index;
		return Next_FolderIndex;
	}
	public synchronized final static long setNext_FileIndex(long index) {
		Next_FileIndex = Next_FileIndex > index ? Next_FileIndex : index;
		return Next_FileIndex;
	}
	public synchronized final static long setNext_MachineIndex(long index) {
		Next_MachineIndex = Next_MachineIndex > index ? Next_MachineIndex : index;
		return Next_MachineIndex;
	}
	public synchronized final static long setNext_DepotIndex(long index) {
		Next_DepotIndex = Next_DepotIndex > index ? Next_DepotIndex : index;
		return Next_DepotIndex;
	}
	public synchronized final static long setNext_DataBaseIndex(long index) {
		Next_DataBaseIndex = Next_DataBaseIndex > index ? Next_DataBaseIndex : index;
		return Next_DataBaseIndex;
	}
	public synchronized final static long setNext_UserIndex(long index) {
		Next_UserIndex = Next_UserIndex > index ? Next_UserIndex : index;
		return Next_UserIndex;
	}
	public synchronized final static long setNext_GroupIndex(long index) {
		Next_GroupIndex = Next_GroupIndex > index ? Next_GroupIndex : index;
		return Next_GroupIndex;
	}
	public synchronized final static long setNext_ChatIndex(long index) {
		Next_ChatIndex = Next_ChatIndex > index ? Next_ChatIndex : index;
		return Next_ChatIndex;
	}
	public synchronized final static long setNext_ConnectionIndex(long index) {
		Next_ConnectionIndex = Next_ConnectionIndex > index ? Next_ConnectionIndex : index;
		return Next_ConnectionIndex;
	}
	public synchronized final static long setNext_ProcessIndex(long index) {
		Next_ProcessIndex = Next_ProcessIndex > index ? Next_ProcessIndex : index;
		return Next_ProcessIndex;
	}
	public synchronized final static long setNext_OperatorIndex(long index) {
		Next_OperatorIndex = Next_OperatorIndex > index ? Next_OperatorIndex : index;
		return Next_OperatorIndex;
	}
	public synchronized final static long setNext_ExecutorIndex(long index) {
		Next_ExecutorIndex = Next_ExecutorIndex > index ? Next_ExecutorIndex : index;
		return Next_ExecutorIndex;
	}
	public synchronized final static long setNext_ScannerIndex(long index) {
		Next_ScannerIndex = Next_ScannerIndex > index ? Next_ScannerIndex : index;
		return Next_ScannerIndex;
	}
	public synchronized final static long setNext_CFGIndex(long index) {
		Next_CFGIndex = Next_CFGIndex > index ? Next_CFGIndex : index;
		return Next_CFGIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
