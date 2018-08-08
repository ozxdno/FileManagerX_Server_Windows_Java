package com.FileManagerX.Commands;

public class BaseCommand implements com.FileManagerX.Interfaces.ICommand {

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
		return this.connection.getClientUser().getPriority().isEnough(p);
	}
	public boolean isLevelEnough(com.FileManagerX.BasicEnums.UserLevel level) {
		return this.connection.getClientUser().getLevel().isEnough(level);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isConnected() {
		if(connection == null || !connection.isRunning()) {
			reply.setOK(false);
			reply.setFailedReason("Disconnected");
			return false;
		}
		return true;
	}
	public boolean isLoginUser() {
		if(this.connection.getClientUser() == null || this.connection.getClientUser().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason("No Login User");
			return false;
		}
		return true;
	}
	public boolean isLoginMachine() {
		if(this.connection.getClientMachineInfo() == null || this.connection.getClientMachineInfo().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason("No Login Machine");
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
			reply.setFailedReason("Wrong User Index");
			return false;
		}
		if(this.connection.getClientUser().getIndex() != this.bmp.getSourUserIndex()) {
			reply.setOK(false);
			reply.setFailedReason("Wrong User Index");
			return false;
		}
		return true;
	}
	public boolean isPasswordRight() {
		if(this.bmp.getPassword() == null || this.bmp.getPassword().length() == 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Password");
			return false;
		}
		if(!this.bmp.getPassword().equals(this.connection.getClientUser().getPassword())) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Password");
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
			reply.setFailedReason("Not Exist such SourUser in DataBase");
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
			reply.setFailedReason("Not Exist such SourMachine in DataBase");
			return false;
		}
		return true;
	}
	public boolean isSourDepotIndexExist() {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(bmp.getSourDepotIndex());
		if(dbm == null) {
			reply.setOK(false);
			reply.setFailedReason("Not Exist such SourDepot in DataBase");
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
			reply.setFailedReason("Not Exist such SourDataBase in DataBase");
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
			reply.setFailedReason("Not Exist such DestUser in DataBase");
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
			reply.setFailedReason("Not Exist such DestMachine in DataBase");
			return false;
		}
		return true;
	}
	public boolean isDestDepotIndexExist() {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(bmp.getDestDepotIndex());
		if(dbm == null) {
			reply.setOK(false);
			reply.setFailedReason("Not Exist such DestDepot in DataBase");
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
			reply.setFailedReason("Not Exist such DestDataBase in DataBase");
			return false;
		}
		
		ddatabase = dbm.getDBInfo();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

