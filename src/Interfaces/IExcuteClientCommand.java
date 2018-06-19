package Interfaces;

public interface IExcuteClientCommand {

	public boolean setConnection(IClientConnection connection);
	public boolean setCMD(BasicModels.Config cmd);
	
	public IClientConnection getConnection();
	public BasicModels.Config getCMD();
	
	public String excute();
	public String excute(String cmd);
	public String excute(BasicModels.Config cmd);
}
