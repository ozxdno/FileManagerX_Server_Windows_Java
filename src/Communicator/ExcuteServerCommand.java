package Communicator;

import Interfaces.IServerConnection;

public class ExcuteServerCommand implements Interfaces.IExcuteServerCommand {

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
	public ExcuteServerCommand(IServerConnection connection) {
		initThis();
		this.setConnection(connection);
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
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		userIndex = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		userPassword = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		if(type == BasicEnums.CMDType.Register) {
			return this.excuteRegister();
		}
		if(type == BasicEnums.CMDType.Login) {
			return this.excuteLogin();
		}
		if(type == BasicEnums.CMDType.SendFile) {
			return this.excuteSendFile();
		}
		if(type == BasicEnums.CMDType.ReceiveFile) {
			return this.excuteReceiveFile();
		}
		
		return this.getReplyCommand(BasicEnums.CMDType.Unsupport);
	}
	public String excute(String cmd) {
		this.cmd.input(cmd);
		return this.excute();
	}
	public String excute(BasicModels.Config cmd) {
		this.cmd = cmd;
		return this.excute();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private String excuteRegister() {
		// CMD: Register = userIndex|userPassword|invitationCode|userModel;
		
		String invitationCode = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		BasicModels.User u = new BasicModels.User();
		String res = u.input(cmd.output());
		if(res == null) {
			return this.getReplyCommand(BasicEnums.CMDType.RegisterFailed_WrongInput);
		}
		
		this.userIndex = u.getIndex();
		this.userPassword = u.getPassword();
		if(this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.RegisterFailed_Existed);
		}
		
		if(!this.isInvitation(invitationCode, u)) {
			this.getReplyCommand(BasicEnums.CMDType.RegisterFailed_WrongInvitation);
		}
		
		Globals.Datas.DBManager.updataUser(u);
		this.connection.setUser(u);
		return this.getReplyCommand(BasicEnums.CMDType.Register);
	}
	private String excuteLogin() {
		// CMD: Login = userIndex|userPassword
		if(!this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.UserNotExist);
		}
		
		return this.getReplyCommand(BasicEnums.CMDType.Login);
	}
	private String excuteSendFile() {
		// CMD: SendFile = userIndex|userPassword|url
		String url = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		if(!this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.UserNotExist);
		}
		if(!this.isPriorityEnough(BasicEnums.UserPriority.Guest)) {
			return this.getReplyCommand(BasicEnums.CMDType.PriorityNotEnough);
		}
		
		BasicModels.BaseFile f = new BasicModels.BaseFile(url);
		if(!f.exists()) {
			return this.getReplyCommand(BasicEnums.CMDType.SendFileFailed_NotExist);
		}
		this.connection.setIsFileConnector(true);
		this.connection.setReceiveFile(f);
		return this.getReplyCommand(BasicEnums.CMDType.SendFile);
	}
	private String excuteReceiveFile() {
		// CMD: ReceiveFile = userIndex|userPassword|url
		String url = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		if(!this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.UserNotExist);
		}
		if(!this.isPriorityEnough(BasicEnums.UserPriority.Guest)) {
			return this.getReplyCommand(BasicEnums.CMDType.PriorityNotEnough);
		}
		
		BasicModels.BaseFile f = new BasicModels.BaseFile(url);
		if(!f.exists()) {
			return this.getReplyCommand(BasicEnums.CMDType.ReceiveFileFailed_NotExist);
		}
		this.connection.setIsFileConnector(true);
		this.connection.setSendFile(f);
		return this.getReplyCommand(BasicEnums.CMDType.ReceiveFile);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private String getReplyCommand(BasicEnums.CMDType type) {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(type.toString());
		c.addToBottom(this.userIndex);
		c.addToBottom(this.userPassword);
		return c.output();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public boolean isUserExist() {
		DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("index");
		qc.setEqual(String.valueOf(this.userIndex));
		qcs.add(qc);
		
		BasicModels.User u = Globals.Datas.DBManager.QueryUser(qcs);
		if(u == null) {
			return false;
		}
		
		return u.getIndex() == this.connection.getUser().getIndex() && 
			u.getPassword() == this.userPassword &&
			u.getPassword() == this.connection.getUser().getPassword();
	}
	public boolean isLevelEnough(BasicEnums.UserLevel level) {
		int res = level.compareTo(this.connection.getUser().getLevel());
		return res >= 0;
	}
	public boolean isPriorityEnough(BasicEnums.UserPriority priority) {
		int res = priority.compareTo(this.connection.getUser().getPriority());
		return res >= 0;
	}
	public boolean isInvitation(String invitationCode, BasicModels.User u) {
		DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("code");
		qc.setEqual(invitationCode);
		qcs.add(qc);
		
		BasicModels.Invitation i = Globals.Datas.DBManager.QueryInvitation(qcs);
		if(u == null) {
			return false;
		}
		
		return i.getUser().getPriority() == u.getPriority() &&
			i.getUser().getLevel() == u.getLevel() &&
			i.getUser().getExperience() == u.getExperience() &&
			i.getUser().getCoins() == u.getCoins() &&
			i.getUser().getMoney() == u.getMoney();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
