package Communicator;

import Interfaces.IServerConnection;

public class ExcuteServerCommand implements Interfaces.IExcuteServerCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private IServerConnection connection;
	private BasicModels.Config cmd;
	private boolean closeServer;
	
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
	public boolean getCloseServer() {
		return this.closeServer;
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
		this.closeServer = false;
		
		type = BasicEnums.CMDType.Unsupport;
		userIndex = -1;
		userPassword = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String excute() {
		try {
			type = BasicEnums.CMDType.valueOf(cmd.getField());
		}catch(Exception e) {
			return this.getReplyCommand(BasicEnums.CMDType.Unsupport);
		}
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.Unsupport);
		}
		
		userIndex = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		userPassword = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		if(type.equals(BasicEnums.CMDType.Register)) {
			return this.excuteRegister();
		}
		if(type.equals(BasicEnums.CMDType.Login)) {
			return this.excuteLogin();
		}
		if(type.equals(BasicEnums.CMDType.SendFile)) {
			return this.excuteSendFile();
		}
		if(type.equals(BasicEnums.CMDType.ReceiveFile)) {
			return this.excuteReceiveFile();
		}
		if(type.equals(BasicEnums.CMDType.AddInvitation_Any)) {
			return this.excuteAddInvitation_Any();
		}
		if(type.equals(BasicEnums.CMDType.CloseServer)) {
			return this.excuteCloseServer();
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
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("LoginName");
		qc.setSign(DataBaseManager.Sign.EQUAL);
		qc.setValue("'" + u.getLoginName() + "'");
		qcs.add(qc);
		BasicModels.User uTest = Globals.Datas.DBManager.QueryUser(qcs);
		if(uTest != null) {
			return this.getReplyCommand(BasicEnums.CMDType.UserExisted);
		}
		
		qc.setItemName("Code");
		qc.setValue("'" + invitationCode + "'");
		BasicModels.Invitation i = Globals.Datas.DBManager.QueryInvitation(qc);
		if(i == null) {
			this.getReplyCommand(BasicEnums.CMDType.WrongInvitationCode);
		}
		
		Globals.Datas.DBManager.removeInvitation(i);
		u.setPriority(i.getUser().getPriority());
		u.setLevel(i.getUser().getLevel());
		u.setExperience(i.getUser().getExperience());
		u.setCoins(i.getUser().getCoins());
		u.setMoney(i.getUser().getMoney());
		
		if(!Globals.Datas.DBManager.updataUser(u)) {
			this.getReplyCommand(BasicEnums.CMDType.UnableAddToDataBase);
		}
		
		this.connection.setUser(u);
		this.userIndex = u.getIndex();
		this.userPassword = u.getPassword();
		return this.getReplyCommand(BasicEnums.CMDType.Register);
	}
	private String excuteLogin() {
		// CMD: Login = userIndex|userPassword|loginName
		String loginName = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("LoginName");
		qc.setSign(DataBaseManager.Sign.EQUAL);
		qc.setValue("'" + loginName + "'");
		qcs.add(qc);
		
		BasicModels.User u = Globals.Datas.DBManager.QueryUser(qcs);
		if(u == null) {
			return this.getReplyCommand(BasicEnums.CMDType.UserNotExist);
		}
		if(!u.getPassword().equals(this.userPassword)) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongPassword);
		}
		
		this.connection.setUser(u);
		this.userIndex = u.getIndex();
		return this.getReplyCommand(BasicEnums.CMDType.Login);
	}
	private String excuteSendFile() {
		// CMD: SendFile = userIndex|userPassword|totalBytes|url
		if(this.connection.getUser() == null) {
			return this.getReplyCommand(BasicEnums.CMDType.NoLogin);
		}
		long totalBytes = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		String url = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		if(!this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.NoLogin);
		}
		if(!this.isPriorityEnough(BasicEnums.UserPriority.Guest)) {
			return this.getReplyCommand(BasicEnums.CMDType.PriorityNotEnough);
		}
		
		boolean permit = false;
		for(int i=0; i<Globals.Datas.DBManagers.getContent().size(); i++) {
			String depot = Globals.Datas.DBManagers.getContent().get(i).getDBInfo().getDepotInfo().getUrl();
			if(url.length() < depot.length()) {
				continue;
			}
			if(url.substring(0, depot.length()).equals(depot)) {
				permit = true;
				break;
			}
		}
		if(!permit) {
			return this.getReplyCommand(BasicEnums.CMDType.UrlNotPrmit);
		}
		
		BasicModels.BaseFile f = new BasicModels.BaseFile(url);
		if(f.exists()) {
			return this.getReplyCommand(BasicEnums.CMDType.FileExisted);
		}
		this.connection.setIsFileConnector(true);
		this.connection.setReceiveFile(f);
		this.connection.setTotalBytes(totalBytes);
		return this.getReplyCommand(BasicEnums.CMDType.SendFile);
	}
	private String excuteReceiveFile() {
		// CMD: ReceiveFile = userIndex|userPassword|machineIndex|depotIndex|fileIndex
		// RET: ReceiveFile = userIndex|userPassword|totalBytes
		if(this.connection.getUser() == null) {
			return this.getReplyCommand(BasicEnums.CMDType.NoLogin);
		}
		
		long machineIndex = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		if(machineIndex != Globals.Configurations.This_MachineIndex) {
			return this.getReplyCommand(BasicEnums.CMDType.FileNotExist);
		}
		
		long depotIndex = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return this.getReplyCommand(BasicEnums.CMDType.FileNotExist);
		}
		
		long fileIndex = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		if(!this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.NoLogin);
		}
		if(!this.isPriorityEnough(BasicEnums.UserPriority.Guest)) {
			return this.getReplyCommand(BasicEnums.CMDType.PriorityNotEnough);
		}
		
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("Index");
		qc.setValue(String.valueOf(fileIndex));
		BasicModels.BaseFile f = dbm.QueryFile(qc);
		if(f == null || f.getType().equals(BasicEnums.FileType.Folder)) {
			return this.getReplyCommand(BasicEnums.CMDType.FileNotExist);
		}
		
		this.connection.setIsFileConnector(true);
		this.connection.setSendFile(f);
		return this.getReplyCommand(BasicEnums.CMDType.ReceiveFile) + "|" + f.getLength();
	}
	private String excuteAddInvitation_Any() {
		// CMD: AddInvitation_Any = userIndex|userPassword|code|priority|level|experience|coins|money
		if(this.connection.getUser() == null) {
			return this.getReplyCommand(BasicEnums.CMDType.NoLogin);
		}
		if(!this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongUser);
		}
		if(!this.isPriorityEnough(BasicEnums.UserPriority.Ozxdno)) {
			return this.getReplyCommand(BasicEnums.CMDType.PriorityNotEnough);
		}
		
		BasicModels.Invitation i = new BasicModels.Invitation();
		i.setUser(new BasicModels.User());
		
		i.setCode(cmd.fetchFirstString());
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		i.getUser().setPriority(BasicEnums.UserPriority.valueOf(cmd.fetchFirstString()));
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		i.getUser().setLevel(BasicEnums.UserLevel.valueOf(cmd.fetchFirstString()));
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		i.getUser().setExperience(cmd.fetchFirstLong());
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		i.getUser().setCoins(cmd.fetchFirstLong());
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		i.getUser().setMoney(cmd.fetchFirstDouble());
		if(!cmd.getIsOK()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongInput);
		}
		
		if(Globals.Datas.DBManager.updataInvitation(i)) {
			return this.getReplyCommand(BasicEnums.CMDType.AddInvitation_Any);
		}
		return this.getReplyCommand(BasicEnums.CMDType.UnableAddToDataBase);
	}
	
	public String excuteCloseServer() {
		//CMD: CloseServer = userIndex|password
		if(this.connection.getUser() == null) {
			return this.getReplyCommand(BasicEnums.CMDType.NoLogin);
		}
		if(!this.isUserExist()) {
			return this.getReplyCommand(BasicEnums.CMDType.WrongUser);
		}
		if(!this.isPriorityEnough(BasicEnums.UserPriority.Admin)) {
			return this.getReplyCommand(BasicEnums.CMDType.PriorityNotEnough);
		}
		
		this.closeServer = true;
		return this.getReplyCommand(BasicEnums.CMDType.CloseServer);
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
		if(this.connection.getUser() == null) {
			return false;
		}
		return this.userIndex == this.connection.getUser().getIndex() && 
			this.userPassword.equals(this.connection.getUser().getPassword());
	}
	public boolean isLevelEnough(BasicEnums.UserLevel level) {
		if(this.connection.getUser() == null) {
			return false;
		}
		int res = level.compareTo(this.connection.getUser().getLevel());
		return res <= 0;
	}
	public boolean isPriorityEnough(BasicEnums.UserPriority priority) {
		if(this.connection.getUser() == null) {
			return false;
		}
		int res = priority.compareTo(this.connection.getUser().getPriority());
		return res <= 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
