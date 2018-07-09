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
	public BasicCollections.DepotInfos queryDepots(Object conditions);
	public BasicCollections.DataBaseInfos queryDataBases(Object conditions);
	
	public BasicModels.User qeuryUser(Object conditions);
	public BasicModels.MachineInfo qeuryMachine(Object conditions);
	public BasicModels.DepotInfo queryDepot(Object conditions);
	public BasicModels.DataBaseInfo queryDataBase(Object conditions);
	
	public boolean removeDepots(Object conditions);
	public boolean removeDataBases(Object conditions);
	
	public boolean removeDepot(Object conditions);
	public boolean removeDataBase(Object conditions);
	
	public boolean updateMachine(BasicModels.MachineInfo machine);
	public boolean updateDepot(BasicModels.DepotInfo depot);
	public boolean updateDataBase(BasicModels.DataBaseInfo database);
	
	public boolean loginConnection();
	public boolean loginUser();
	public boolean loginMachine();
	public boolean loginType();
	
	public boolean test();
	public String test(String str);
	public String test(long destMachine, String str);
	
	public boolean closeServer();
	public boolean closeServer(long destMachine);
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}