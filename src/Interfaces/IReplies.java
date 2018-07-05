package Interfaces;

public interface IReplies extends IPublic {

	public boolean setBasicMessagePackage(Interfaces.IBasicMessagePackage bmp);
	public boolean setOK(boolean ok);
	public boolean setFailedReason(String failedReason);
	
	public Interfaces.IBasicMessagePackage getBasicMessagePackage();
	public boolean isOK();
	public String getFailedReason();
	
	public boolean execute(Interfaces.IConnection connection);
	
}
