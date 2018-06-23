package Interfaces;

public interface IExcuteServerCommand {

	public boolean setConnection(IServerConnection connection);
	public boolean setCMD(BasicModels.Config cmd);
	
	public IServerConnection getConnection();
	public BasicModels.Config getCMD();
	public boolean getCloseServer();
	
	public String excute();
	public String excute(String cmd);
	public String excute(BasicModels.Config cmd);
}
