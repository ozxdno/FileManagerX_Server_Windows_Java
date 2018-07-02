package Interfaces;

public interface ICommands extends IPublic {

	public boolean setUserIndex(long userIndex);
	public boolean setPassword(String password);
	public boolean setMachineIndex(long machineIndex);
	public boolean setDepotIndex(long depotIndex);
	public boolean setDataBaseIndex(long dbIndex);
	public boolean setConnection(Interfaces.IConnection connection);
	public boolean setReply(Interfaces.IReplies reply);
	
	public long getUserIndex();
	public String getPassword();
	public long getMachineIndex();
	public long getDepotIndex();
	public long getDataBaseIndex();
	public Interfaces.IConnection getConnection();
	public Interfaces.IReplies getReply();
	
	public boolean execute();
	public void reply();
}
