package Interfaces;

public interface IReplies extends IPublic {

	public boolean setOK(boolean ok);
	public boolean setUserIndex(long userIndex);
	public boolean setPassword(String password);
	public boolean setFailedReason(String failedReason);
	
	public boolean isOK();
	public long getUserIndex();
	public String getPassword();
	public String getFailedReason();
	
	public boolean execute(Interfaces.IConnection connection);
	
}
