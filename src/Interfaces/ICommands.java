package Interfaces;

public interface ICommands extends IPublic {

	public boolean setBasicMessagePackage(Interfaces.IBasicMessagePackage bmp);
	public boolean setConnection(Interfaces.IConnection connection);
	public boolean setReply(Interfaces.IReplies reply);
	public void setBasicMessagePackageToReply();
	
	public Interfaces.IBasicMessagePackage getBasicMessagePackage();
	public Interfaces.IConnection getConnection();
	public Interfaces.IReplies getReply();
	
	public boolean execute();
}
