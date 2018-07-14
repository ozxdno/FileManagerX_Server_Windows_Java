package Interfaces;

public interface ICommandsManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnection(Interfaces.IClientConnection connection);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IReplies getReply();
	public Interfaces.IClientConnection getConnection();
	public Interfaces.ISWRE getSWRE();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Replies.QueryConfigurations queryConfigurations();
	public BasicCollections.MachineInfos queryMachines(Object conditions);
	public BasicCollections.DepotInfos queryDepots(Object conditions);
	public BasicCollections.DataBaseInfos queryDataBases(Object conditions);
	public BasicCollections.Users queryUsers(Object conditions);
	public BasicCollections.Invitations queryInvitations(Object conditions);
	
	public BasicCollections.Folders queryFolders(long depotIndex, Object conditions);
	public BasicCollections.Folders queryFolders(long machineIndex, long depotIndex, Object conditions);
	
	public BasicCollections.BaseFiles queryFiles(long depotIndex, Object conditions);
	public BasicCollections.BaseFiles queryFiles(long machineIndex, long depotIndex, Object conditions);
	
	public BasicModels.User queryUser(Object conditions);
	public BasicModels.MachineInfo queryMachine(Object conditions);
	public BasicModels.DepotInfo queryDepot(Object conditions);
	public BasicModels.DataBaseInfo queryDataBase(Object conditions);
	public BasicModels.Invitation queryInvitation(Object conditions);
	
	public BasicModels.Folder queryFolder(long depotIndex, Object conditions);
	public BasicModels.Folder queryFolder(long machineIndex, long depotIndex, Object conditions);
	
	public BasicModels.BaseFile queryFile(long depotIndex, Object conditions);
	public BasicModels.BaseFile queryFile(long machineIndex, long depotIndex, Object conditions);
	
	public int querySize(String queryItem);
	public int querySize(long depotIndex, String queryItem);
	public int querySize(long machineIndex, long depotIndex, String queryItem);
	
	public boolean removeDepots(Object conditions);
	public boolean removeDataBases(Object conditions);
	public boolean removeMachines(Object conditions);
	public boolean removeUsers(Object conditions);
	public boolean removeInvitations(Object conditions);
	
	public boolean removeFolders(long depotIndex, Object conditions);
	public boolean removeFolders(long machineIndex, long depotIndex, Object conditions);
	
	public boolean removeFiles(long depotIndex, Object conditions);
	public boolean removeFiles(long machineIndex, long depotIndex, Object conditions);
	
	public boolean removeDepot(Object conditions);
	public boolean removeDataBase(Object conditions);
	public boolean removeMachine(Object conditions);
	public boolean removeUser(Object conditions);
	public boolean removeInvitation(Object conditions);
	
	public boolean removeFolder(long depotIndex, Object conditions);
	public boolean removeFolder(long machineIndex, long depotIndex, Object conditions);
	
	public boolean removeFile(long depotIndex, Object conditions);
	public boolean removeFile(long machineIndex, long depotIndex, Object conditions);
	
	public boolean updateMachines(long machineIndex, String items, Object conditions);
	public boolean updateMachines(BasicModels.MachineInfo model, String items, Object conditions);
	
	public boolean updateDepots(long depotIndex, String items, Object conditions);
	public boolean updateDepots(BasicModels.DepotInfo model, String items, Object conditions);
	
	public boolean updateDataBases(long databaseIndex, String items, Object conditions);
	public boolean updateDataBases(BasicModels.DataBaseInfo model, String items, Object conditions);
	
	public boolean updateUsers(long userIndex, String items, Object conditions);
	public boolean updateUsers(BasicModels.User model, String items, Object conditions);
	
	public boolean updateInvitations(String invitationCode, String items, Object conditions);
	public boolean updateInvitations(BasicModels.Invitation model, String items, Object conditions);
	
	public boolean updateFolders(long depotIndex, long folderIndex, String items, Object conditions);
	public boolean updateFolders(long depotIndex, BasicModels.Folder model, String items, Object conditions);
	public boolean updateFolders(long machineIndex, long depotIndex, long folderIndex, String items, Object conditions);
	public boolean updateFolders(long machineIndex, long depotIndex, BasicModels.Folder model, String items, Object conditions);
	
	public boolean updateFiles(long depotIndex, long fileIndex, String items, Object conditions);
	public boolean updateFiles(long depotIndex, BasicModels.BaseFile model, String items, Object conditions);
	public boolean updateFiles(long machineIndex, long depotIndex, long fileIndex, String items, Object conditions);
	public boolean updateFiles(long machineIndex, long depotIndex, BasicModels.BaseFile model, String items, Object conditions);
	
	public boolean updateMachine(BasicModels.MachineInfo machine);
	public boolean updateDepot(BasicModels.DepotInfo depot);
	public boolean updateDataBase(BasicModels.DataBaseInfo database);
	public boolean updateUser(BasicModels.User user);
	public boolean updateInvitation(BasicModels.Invitation invitation);
	
	public boolean updateFolder(long depotIndex, BasicModels.Folder folder);
	public boolean updateFolder(long machineIndex, long depotIndex, BasicModels.Folder folder);
	
	public boolean updateFile(long depotIndex, BasicModels.BaseFile file);
	public boolean updateFile(long machineIndex, long depotIndex, BasicModels.BaseFile file);
	
	public BasicModels.BaseFile querySpecificFile(long depotIndex, BasicEnums.FileType type, Object conditions);
	public BasicModels.BaseFile querySpecificFile(long machineIndex, long depotIndex, BasicEnums.FileType type, Object conditions);
	public BasicCollections.BaseFiles querySpecificFiles(long depotIndex, BasicEnums.FileType type, Object conditions);
	public BasicCollections.BaseFiles querySpecificFiles(long machineIndex, long depotIndex, BasicEnums.FileType type, Object conditions);
	public boolean removeSpecificFile(long depotIndex, BasicEnums.FileType type, Object conditions);
	public boolean removeSpecificFile(long machineIndex, long depotIndex, BasicEnums.FileType type, Object conditions);
	public boolean removeSpecificFiles(long depotIndex, BasicEnums.FileType type, Object conditions);
	public boolean removeSpecificFiles(long machineIndex, long depotIndex, BasicEnums.FileType type, Object conditions);
	public boolean updateSpecificFile(long depotIndex, BasicEnums.FileType type, BasicModels.BaseFile file);
	public boolean updateSpecificFile(long machineIndex, long depotIndex, BasicEnums.FileType type, BasicModels.BaseFile file);
	public boolean updateSpecificFiles(long depotIndex, BasicEnums.FileType type, BasicModels.BaseFile model, String items, Object conditions);
	public boolean updateSpecificFiles(long machineIndex, long depotIndex, BasicEnums.FileType type, BasicModels.BaseFile model, String items, Object conditions);
	
	public boolean registerUser(String invitationCode, BasicModels.User user);
	
	public boolean loginConnection();
	public boolean loginUser();
	public boolean loginMachine();
	public boolean loginType();
	public boolean loginIndex();
	
	public boolean test();
	public String test(String str);
	public String test(long destMachine, String str);
	
	public boolean closeServer();
	public boolean closeServer(long destMachine);
	
	public boolean restart();
	public boolean restart(long destMachine);
	
	public boolean timeForExecute(long destMachine, long receiveWaitTicks, long sendWaitTicks);
	
	public boolean printScreen();
	public boolean printScreen(long destMachine);
	
	public boolean exchange();
	public boolean createConnection(long destMachine, BasicModels.MachineInfo machineInfo);
	
	public boolean operateDepot(BasicEnums.OperateType operateType, long destDepot, String sourUrl, String destUrl);
	public boolean operateDepot(DepotManager.Operator operator);
	public boolean operateDepot(BasicEnums.OperateType operateType, long destmachine, long destDepot, String sourUrl, String destUrl);
	public boolean operateDepot(long destMachine, DepotManager.Operator operator);
	
	public boolean operateMatch(BasicEnums.FileType type, String args);
	public boolean operateMatch(Match.Match match);
	public boolean operateMatch(long destMachine, BasicEnums.FileType type, String args);
	public boolean operateMatch(long destMachine, Match.Match match);
	
	public boolean input(String sourUrl, String destUrl);
	public boolean input(String sourUrl, String destUrl, boolean cover);
	public boolean input(String sourUrl, String destUrl, long finishedBytes);
	public boolean input(String sourUrl, String destUrl, long finishedBytes, boolean cover);
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl);
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl, boolean cover);
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes);
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes, boolean cover);
	public boolean input(long sourMachine, long destMachine, long sourDepot, long destDepot, String sourUrl, String destUrl, long finishedBytes, boolean cover);
	
	public boolean output(String sourUrl, String destUrl);
	public boolean output(String sourUrl, String destUrl, boolean cover);
	public boolean output(String sourUrl, String destUrl, long finishedBytes);
	public boolean output(String sourUrl, String destUrl, long finishedBytes, boolean cover);
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl);
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl, boolean cover);
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes);
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes, boolean cover);
	public boolean output(long sourMachine, long destMachine, long sourDepot, long destDepot, String sourUrl, String destUrl, long finishedBytes, long totalBytes, boolean cover);
	
	public boolean outputMatchFile(String sourUrl);
	public boolean outputMatchFile(long destMachine, String sourUrl);
	public boolean outputMatchFile(long destMachine, String sourUrl, String destUrl);
	public boolean outputMatchFile(long destMachine, String sourUrl, String destUrl, long totalBytes);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
