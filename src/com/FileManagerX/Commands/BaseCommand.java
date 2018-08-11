package com.FileManagerX.Commands;

public class BaseCommand implements com.FileManagerX.Interfaces.ICommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_WRONG_INPUT = "Convert String to Target Object Failed";
	
	public final static String FAILED_NOT_CONNECT = "Connection is NULL or Not Running";
	public final static String FAILED_NOT_LOGIN_USER = "Not Login User";
	public final static String FAILED_NOT_LOGIN_MACHINE = "Not Login Machine";
	public final static String FAILED_WRONG_USER_INDEX = "User Index is Wrong";
	public final static String FAILED_WRONG_PASSWORD = "Password is Wrong";
	public final static String FAILED_LOW_PRIORITY = "Priority is not Enough";
	public final static String FAILED_LOW_LEVEL = "Level is not Enough";
	public final static String FAILED_NO_SOUR_USER = "Not Found Sour User";
	public final static String FAILED_NO_DEST_USER = "Not Found Dest User";
	public final static String FAILED_NO_SOUR_MACHINE = "Not Found Sour Machine";
	public final static String FAILED_NO_DEST_MACHINE = "Not Found Dest Machine";
	public final static String FAILED_NO_SOUR_DEPOT = "Not Found Sour Depot";
	public final static String FAILED_NO_DEST_DEPOT = "Not Found Dest Depot";
	public final static String FAILED_NO_SOUR_DATABASE = "Not Found Sour DataBase";
	public final static String FAILED_NO_DEST_DATABASE = "Not Found Dest DataBase";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.Interfaces.IBasicMessagePackage bmp;
	private com.FileManagerX.Interfaces.IConnection connection;
	private com.FileManagerX.Interfaces.IReply reply;
	
	private com.FileManagerX.BasicModels.User suser;
	private com.FileManagerX.BasicModels.MachineInfo smachine;
	private com.FileManagerX.BasicModels.DepotInfo sdepot;
	private com.FileManagerX.BasicModels.DataBaseInfo sdatabase;
	private com.FileManagerX.BasicModels.User duser;
	private com.FileManagerX.BasicModels.MachineInfo dmachine;
	private com.FileManagerX.BasicModels.DepotInfo ddepot;
	private com.FileManagerX.BasicModels.DataBaseInfo ddatabase;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setBasicMessagePackage(com.FileManagerX.Interfaces.IBasicMessagePackage bmp) {
		if(bmp == null) {
			return false;
		}
		this.bmp = bmp;
		return true;
	}
	public boolean setConnection(com.FileManagerX.Interfaces.IConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	
	public boolean setReply(com.FileManagerX.Interfaces.IReply reply) {
		if(reply == null) {
			return false;
		}
		this.reply = reply;
		this.reply.getBasicMessagePackage().setThis(connection.getClientConnection());
		this.reply.setConnection(connection);
		this.reply.getBasicMessagePackage().getRoutePathPackage().copyValue(this.bmp.getRoutePathPackage());
		this.reply.getBasicMessagePackage().getRoutePathPackage().reverse();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IBasicMessagePackage getBasicMessagePackage() {
		return this.bmp;
	}
	public com.FileManagerX.Interfaces.IConnection getConnection() {
		return this.connection;
	}
	public com.FileManagerX.Interfaces.IReply getReply() {
		return this.reply;
	}
	
	public com.FileManagerX.BasicModels.User getSourUser() {
		return this.suser;
	}
	public com.FileManagerX.BasicModels.MachineInfo getSourMachineInfo() {
		return this.smachine;
	}
	public com.FileManagerX.BasicModels.DepotInfo getSourDepotInfo() {
		return this.sdepot;
	}
	public com.FileManagerX.BasicModels.DataBaseInfo getSourDataBaseInfo() {
		return this.sdatabase;
	}
	public com.FileManagerX.BasicModels.User getDestUser() {
		return this.duser;
	}
	public com.FileManagerX.BasicModels.MachineInfo getDestMachineInfo() {
		return this.dmachine;
	}
	public com.FileManagerX.BasicModels.DepotInfo getDestDepotInfo() {
		return this.ddepot;
	}
	public com.FileManagerX.BasicModels.DataBaseInfo getDestDataBaseInfo() {
		return this.ddatabase;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BaseCommand() {
		initThis();
	}
	private void initThis() {
		this.bmp = com.FileManagerX.Factories.CommunicatorFactory.createBMP();
		this.connection = null;
		this.reply = null;
		this.suser = null;
		this.smachine = null;
		this.sdepot = null;
		this.sdatabase = null;
		this.duser = null;
		this.dmachine = null;
		this.ddepot = null;
		this.ddatabase = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis() {
		boolean ok = true;
		return ok;
	}
	public boolean setThis(com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.setConnection(connection);
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setThis();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new com.FileManagerX.BasicModels.Config(this.bmp.output()));
		
		return c.output();
	}
	public String input(String in) {
		return this.bmp.input(in);
	}
	public void copyReference(Object o) {
		BaseCommand c = (BaseCommand)o;
		this.bmp = c.bmp;
		this.connection = c.connection;
	}
	public void copyValue(Object o) {
		BaseCommand c = (BaseCommand)o;
		this.bmp.copyValue(c.bmp);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		boolean ok = this.setReply(new com.FileManagerX.Replies.BaseReply());
		ok &= this.executeInLocal();
		return ok;
	}
	public boolean executeInLocal() {
		return true;
	}
	public boolean deliver() {
		return com.FileManagerX.Deliver.Deliver.deliver(this);
	}
	public boolean deliverToServer() {
		return com.FileManagerX.Deliver.Deliver.deliverToServer(this);
	}
	public boolean send() {
		return this.connection == null ? false : this.connection.send(this);
	}
	public com.FileManagerX.Interfaces.IReply receive() {
		return this.connection.getServerConnection().receive(
				this.getBasicMessagePackage().getIndex(),
				this.getBasicMessagePackage().getPermitIdle());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isArriveTargetMachine() {
		return this.isArriveDestMachine();
	}
	public boolean isSourIsServer() {
		return this.bmp.getSourMachineIndex() == com.FileManagerX.Globals.Configurations.Server_MachineIndex;
	}
	public boolean isDestIsServer() {
		return this.bmp.getDestMachineIndex() == com.FileManagerX.Globals.Configurations.Server_MachineIndex;
	}
	public boolean isArriveServer() {
		return com.FileManagerX.Globals.Configurations.IsServer;
	}
	public boolean isDeliver() {
		return !this.isArriveDestMachine();
	}
	public boolean isTimeOut() {
		return com.FileManagerX.Tools.Time.getTicks() - this.getBasicMessagePackage().getSendTime() >
			this.getBasicMessagePackage().getPermitIdle();
	}
	
	public boolean isArriveSourMachine() {
		return this.bmp.getSourMachineIndex() == com.FileManagerX.Globals.Configurations.This_MachineIndex;
	}
	public boolean isArriveDestMachine() {
		return this.bmp.isBroadCast() ||
				this.bmp.getDestMachineIndex() == com.FileManagerX.Globals.Configurations.This_MachineIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isPriorityEnough(com.FileManagerX.BasicEnums.UserPriority p) {
		boolean ok = this.connection.getClientUser().getPriority().isEnough(p);
		if(!ok) {
			this.getReply().setFailedReason(FAILED_LOW_PRIORITY);
			this.getReply().setOK(false);
			return false;
		}
		return ok;
	}
	public boolean isLevelEnough(com.FileManagerX.BasicEnums.UserLevel level) {
		boolean ok = this.connection.getClientUser().getLevel().isEnough(level);
		if(!ok) {
			this.getReply().setFailedReason(FAILED_LOW_LEVEL);
			this.getReply().setOK(false);
			return false;
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isConnected() {
		if(connection == null || !connection.isRunning()) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NOT_CONNECT);
			return false;
		}
		return true;
	}
	public boolean isLoginUser() {
		if(this.connection.getClientUser() == null || this.connection.getClientUser().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NOT_LOGIN_USER);
			return false;
		}
		return true;
	}
	public boolean isLoginMachine() {
		if(this.connection.getClientMachineInfo() == null || this.connection.getClientMachineInfo().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NOT_LOGIN_USER);
			return false;
		}
		return true;
	}
	public boolean isLogin() {
		return this.isLoginUser() && this.isLoginMachine();
	}
	public boolean isUserIndexRight() {
		if(this.bmp.getSourUserIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_USER_INDEX);
			return false;
		}
		if(this.connection.getClientUser().getIndex() != this.bmp.getSourUserIndex()) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_USER_INDEX);
			return false;
		}
		return true;
	}
	public boolean isPasswordRight() {
		if(this.bmp.getPassword() == null || this.bmp.getPassword().length() == 0) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_PASSWORD);
			return false;
		}
		if(!this.bmp.getPassword().equals(this.connection.getClientUser().getPassword())) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_PASSWORD);
			return false;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isSourUserExist() {
		if(com.FileManagerX.Globals.Datas.ThisUser.getIndex() == bmp.getSourUserIndex()) {
			suser = com.FileManagerX.Globals.Datas.ThisUser;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.User);
			suser = (com.FileManagerX.BasicModels.User)
					com.FileManagerX.Globals.Datas.DBManager.query("[&] Index = " + this.bmp.getSourUserIndex());
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.User, "[&] Index = " + this.bmp.getSourUserIndex(), connection);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			suser = (rep == null || !rep.isOK()) ? null : (com.FileManagerX.BasicModels.User)rep.getResult();
		}
		
		if(suser == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_SOUR_USER);
			return false;
		}
		return true;
	}
	public boolean isSourMachineIndexExist() {
		if(com.FileManagerX.Globals.Datas.ThisMachine.getIndex() == bmp.getSourMachineIndex()) {
			smachine = com.FileManagerX.Globals.Datas.ThisMachine;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Machine);
			smachine = (com.FileManagerX.BasicModels.MachineInfo)
					com.FileManagerX.Globals.Datas.DBManager.query("[&] Index = " + this.bmp.getSourMachineIndex());
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.Machine,
					"[&] Index = " + this.bmp.getSourMachineIndex(), connection);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			smachine = (rep == null || !rep.isOK()) ? null : 
				(com.FileManagerX.BasicModels.MachineInfo)rep.getResult();
		}
		
		if(smachine == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_SOUR_MACHINE);
			return false;
		}
		return true;
	}
	public boolean isSourDepotIndexExist() {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(bmp.getSourDepotIndex());
		if(dbm == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_SOUR_DEPOT);
			return false;
		}
		
		sdepot = dbm.getDBInfo().getDepotInfo();
		return true;
	}
	public boolean isSourDataBaseIndexExist() {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(bmp.getSourDepotIndex());
		if(dbm == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_SOUR_DATABASE);
			return false;
		}
		
		sdatabase = dbm.getDBInfo();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isDestUserExist() {
		if(com.FileManagerX.Globals.Datas.ThisUser.getIndex() == bmp.getDestUserIndex()) {
			duser = com.FileManagerX.Globals.Datas.ThisUser;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.User);
			duser = (com.FileManagerX.BasicModels.User)
					com.FileManagerX.Globals.Datas.DBManager.query("[&] Index = " + this.bmp.getDestUserIndex());
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.User, "[&] Index = " + this.bmp.getDestUserIndex(), connection);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			duser = (rep == null || !rep.isOK()) ? null : (com.FileManagerX.BasicModels.User)rep.getResult();
		}
		
		if(duser == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_DEST_USER);
			return false;
		}
		return true;
	}
	public boolean isDestMachineIndexExist() {
		if(com.FileManagerX.Globals.Datas.ThisMachine.getIndex() == bmp.getDestMachineIndex()) {
			dmachine = com.FileManagerX.Globals.Datas.ThisMachine;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Machine);
			dmachine = (com.FileManagerX.BasicModels.MachineInfo)
					com.FileManagerX.Globals.Datas.DBManager.query("[&] Index = " + this.bmp.getDestMachineIndex());
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.Machine,
					"[&] Index = " + this.bmp.getDestMachineIndex(), connection);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			dmachine = (rep == null || !rep.isOK()) ? null : 
				(com.FileManagerX.BasicModels.MachineInfo)rep.getResult();
		}
		
		if(dmachine == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_DEST_MACHINE);
			return false;
		}
		return true;
	}
	public boolean isDestDepotIndexExist() {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(bmp.getDestDepotIndex());
		if(dbm == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_DEST_DEPOT);
			return false;
		}
		
		ddepot = dbm.getDBInfo().getDepotInfo();
		return true;
	}
	public boolean isDestDataBaseIndexExist() {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(bmp.getDestDepotIndex());
		if(dbm == null) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NO_DEST_DATABASE);
			return false;
		}
		
		ddatabase = dbm.getDBInfo();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

