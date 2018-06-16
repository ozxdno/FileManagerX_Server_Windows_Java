package Communicator;

public class ExcuteServerCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private IServerConnection connection;
	private BasicModels.Config cmd;
	
	private BasicEnums.CMDType type;
	private long userIndex;
	private String userPassword;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnection(IServerConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	public boolean setCMD(BasicModels.Config cmd) {
		if(cmd == null) {
			return false;
		}
		this.cmd = cmd;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public IServerConnection getConnection() {
		return this.connection;
	}
	public BasicModels.Config getCMD() {
		return this.cmd;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ExcuteServerCommand() {
		initThis();
	}
	public ExcuteServerCommand(IServerConnection connection, BasicModels.Config cmd) {
		initThis();
		this.setConnection(connection);
		this.setCMD(cmd);
	}
	private void initThis() {
		this.connection = null;
		this.cmd = new BasicModels.Config();
		
		type = BasicEnums.CMDType.Unsupport;
		userIndex = -1;
		userPassword = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String excute() {
		type = BasicEnums.CMDType.valueOf(cmd.fetchFirstString());
		if(!cmd.getIsOK()) {
			return this.getUnsupportCommand();
		}
		userIndex = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.getUnsupportCommand();
		}
		userPassword = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getUnsupportCommand();
		}
		
		// is user exist ? 
		
		if(type == BasicEnums.CMDType.Login) {
			return this.excuteLogin();
		}
		if(type == BasicEnums.CMDType.SendFile) {
			return this.excuteSendFile();
		}
		
		return this.getUnsupportCommand();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private String getUnsupportCommand() {
		return type.toString() + " = " +
				BasicEnums.CMDType.Unsupport.toString();
	}
	
	private String excuteLogin() {
		return "";
	}
	private String excuteSendFile() {
		//if(this.connection.getUser().getPriority().compareTo(BasicEnums.UserPriority.Member) < 0) {
		//	return  "";
		//}
		
		this.connection.setIsFileConnector(true);
		return "Start receive";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
