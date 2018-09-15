package com.FileManagerX.Commands;

public class BaseCommand extends com.FileManagerX.Transport.Transport
	implements com.FileManagerX.Interfaces.ICommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_WRONG_INPUT = "Convert String to Target Object Failed";
	public final static String FAILED_NO_TARGET = "Not Find Dest Machine";
	
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
	
	public boolean setReply(com.FileManagerX.Interfaces.IReply reply) {
		if(reply == null) {
			return false;
		}
		this.reply = reply;
		this.reply.getBasicMessagePackage().copyReference(this.getBasicMessagePackage());
		this.reply.getBasicMessagePackage().swapSourAndDest();
		this.reply.setDestConnection(this.getSourConnection());
		this.reply.copyReversePath(this);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		this.getBasicMessagePackage().setThis_LocalAsSour();
		this.getBasicMessagePackage().setThis_ServerAsDest();
		this.getBasicMessagePackage().setDestDepotIndex(
				com.FileManagerX.Globals.Configurations.Server_MachineIndex
			);
		
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
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
		c = super.input(c);
		if(!c.getIsOK()) { return c; }
		
		return c;
	}
	public void copyReference(Object o) {
		if(o instanceof BaseCommand) {
			super.copyReference(o);
			return;
		}
		if(o instanceof com.FileManagerX.Transport.Transport) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof BaseCommand) {
			super.copyValue(o);
			return;
		}
		if(o instanceof com.FileManagerX.Transport.Transport) {
			super.copyValue(o);
			return;
		}
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isPriorityEnough(com.FileManagerX.BasicEnums.UserPriority p) {
		boolean ok = this.getSourConnection().getClientUser().getPriority().isEnough(p);
		if(!ok) {
			this.getReply().setFailedReason(FAILED_LOW_PRIORITY);
			this.getReply().setOK(false);
			return false;
		}
		return ok;
	}
	public boolean isLevelEnough(com.FileManagerX.BasicEnums.UserLevel level) {
		boolean ok = this.getSourConnection().getClientUser().getLevel().isEnough(level);
		if(!ok) {
			this.getReply().setFailedReason(FAILED_LOW_LEVEL);
			this.getReply().setOK(false);
			return false;
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isConnected() {
		if(this.getSourConnection() == null || !this.getSourConnection().isRunning()) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason(FAILED_NOT_CONNECT);
			return false;
		}
		return true;
	}
	public boolean isLoginUser() {
		if(this.getSourConnection().getClientUser() == null || this.getSourConnection().getClientUser().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_NOT_LOGIN_USER);
			return false;
		}
		return true;
	}
	public boolean isLoginMachine() {
		if(this.getSourConnection().getClientMachineInfo() == null || this.getSourConnection().getClientMachineInfo().getIndex() <= 0) {
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
		if(this.getBasicMessagePackage().getSourUserIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_USER_INDEX);
			return false;
		}
		if(this.getSourConnection().getClientUser().getIndex() != this.getBasicMessagePackage().getSourUserIndex()) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_USER_INDEX);
			return false;
		}
		return true;
	}
	public boolean isPasswordRight() {
		if(this.getBasicMessagePackage().getPassword() == null ||
				this.getBasicMessagePackage().getPassword().length() == 0) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_PASSWORD);
			return false;
		}
		if(!this.getBasicMessagePackage().getPassword().equals(
				this.getSourConnection().getClientUser().getPassword())
				) {
			reply.setOK(false);
			reply.setFailedReason(FAILED_WRONG_PASSWORD);
			return false;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isSourUserExist() {
		if(com.FileManagerX.Globals.Datas.ThisUser.getIndex() == this.getBasicMessagePackage().getSourUserIndex()) {
			suser = com.FileManagerX.Globals.Datas.ThisUser;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.query(
					"[&] Index = " + this.getBasicMessagePackage().getSourUserIndex(),
					suser,
					com.FileManagerX.DataBase.Unit.User
				);
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.User, 
					"[&] index = " + this.getBasicMessagePackage().getSourUserIndex());
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
		if(com.FileManagerX.Globals.Datas.ThisMachine.getIndex() == 
				this.getBasicMessagePackage().getSourMachineIndex()) {
			smachine = com.FileManagerX.Globals.Datas.ThisMachine;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.query(
					"[&] Index = " + this.getBasicMessagePackage().getSourMachineIndex(),
					smachine,
					com.FileManagerX.DataBase.Unit.Machine
				);
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.Machine,
					"[&] Index = " + this.getBasicMessagePackage().getSourMachineIndex());
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
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(
						this.getBasicMessagePackage().getSourDepotIndex()
					);
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
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(this.getBasicMessagePackage().getSourDepotIndex());
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
		if(com.FileManagerX.Globals.Datas.ThisUser.getIndex() == this.getBasicMessagePackage().getDestUserIndex()) {
			duser = com.FileManagerX.Globals.Datas.ThisUser;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.query(
					"[&] Index = " + this.getBasicMessagePackage().getDestUserIndex(),
					duser,
					com.FileManagerX.DataBase.Unit.User
				);
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.User, "[&] Index = " + this.getBasicMessagePackage().getDestUserIndex());
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
		if(com.FileManagerX.Globals.Datas.ThisMachine.getIndex() == this.getBasicMessagePackage().getDestMachineIndex()) {
			dmachine = com.FileManagerX.Globals.Datas.ThisMachine;
			return true;
		}
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.query(
					"[&] index = " + this.getBasicMessagePackage().getDestMachineIndex(),
					dmachine,
					com.FileManagerX.DataBase.Unit.Machine
				);
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(com.FileManagerX.DataBase.Unit.Machine,
					"[&] Index = " + this.getBasicMessagePackage().getDestMachineIndex());
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
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(this.getBasicMessagePackage().getDestDepotIndex());
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
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(this.getBasicMessagePackage().getDestDepotIndex());
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

