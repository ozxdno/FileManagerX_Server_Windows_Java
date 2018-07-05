package Interfaces;

public interface IGAPManager {

	public BasicModels.User QeuryUser(Object conditions);
	public BasicModels.MachineInfo QeuryMachineInfo(Object conditions);
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions);
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions);
	
	public boolean loginUser();
	public boolean loginUser(Interfaces.IConnection connection);
	public boolean loginUser(String loginName, String password);
	public boolean loginUser(String loginName, String password, Interfaces.IConnection connection);
	
	public boolean loginMachine();
	public boolean loginMachine(Interfaces.IConnection connection);
	public boolean loginMachine(long machineIndex, Interfaces.IConnection connection);
	public boolean loginMachine(long machineIndex, long userIndex, String password, Interfaces.IConnection connection);
	
	public String test();
	public String test(String str);
	public String test(Interfaces.IConnection connection);
	public String test(String str, Interfaces.IConnection connection);
	public String test(String str, long userIndex, String password, Interfaces.IConnection connection);
}
