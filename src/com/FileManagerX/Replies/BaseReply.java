package com.FileManagerX.Replies;

public class BaseReply implements com.FileManagerX.Interfaces.IReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IBasicMessagePackage bmp;
	
	private boolean ok;
	private String failedReason;
	
	private com.FileManagerX.Interfaces.IConnection connection;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setBasicMessagePackage(com.FileManagerX.Interfaces.IBasicMessagePackage bmp) {
		if(bmp == null) {
			return false;
		}
		this.bmp = bmp;
		return true;
	}
	public boolean setOK(boolean ok) {
		this.ok = ok;
		return true;
	}
	public boolean setFailedReason(String failedReason) {
		if(failedReason == null) {
			return false;
		}
		this.failedReason = failedReason;
		return true;
	}
	public boolean setConnection(com.FileManagerX.Interfaces.IConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IBasicMessagePackage getBasicMessagePackage() {
		return this.bmp;
	}
	public boolean isOK() {
		return this.ok;
	}
	public String getFailedReason() {
		return this.failedReason;
	}
	public com.FileManagerX.Interfaces.IConnection getConnection() {
		return this.connection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BaseReply() {
		initThis();
	}
	private void initThis() {
		bmp = com.FileManagerX.Factories.CommunicatorFactory.createBMP();
		ok = true;
		failedReason = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setThis(boolean OK, String reason) {
		boolean ok = true;
		ok &= this.setOK(OK);
		ok &= this.setFailedReason(reason);
		return ok;
	}
	public boolean setThis(boolean OK, String reason,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(ok, reason);
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
		c.addToBottom(ok);
		c.addToBottom(failedReason);
		return c.output();
	}
	public String input(String in) {
		in = this.bmp.input(in);
		if(in == null) {
			return null;
		}
		
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			this.ok = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.failedReason = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		BaseReply c = (BaseReply)o;
		this.bmp = c.bmp;
		this.ok = c.ok;
		this.failedReason = c.failedReason;
	}
	public void copyValue(Object o) {
		BaseReply c = (BaseReply)o;
		this.bmp.copyValue(c.bmp);
		this.ok = c.ok;
		this.failedReason = new String(c.failedReason);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		
		ok &= this.executeInLocal();
		ok &= this.store();
		return this.ok;
	}
	public boolean executeInLocal() {
		return true;
	}
	
	public boolean deliver() {
		com.FileManagerX.Interfaces.IClientConnection c = com.FileManagerX.Globals.Datas.Client.search(
				this.bmp.getSourMachineIndex());
		if(c == null) {
			this.setFailedReason("Target Machine is Offline");
			this.setOK(false);
			return false;
		}
		
		boolean ok = c.send(this);
		if(!ok) {
			this.setFailedReason("Deliver Commmand Failed");
			this.setOK(false);
			return false;
		}
		
		return true;
	}
	public boolean deliverToServer() {
		return com.FileManagerX.Deliver.Deliver.deliverToServer(this);
	}
	
	public boolean store() {
		this.getConnection().getServerConnection().store(this);
		return true;
	}
	public boolean send() {
		return this.getConnection().getClientConnection().send(this);
	}
	public com.FileManagerX.Interfaces.IReply receive() {
		return this.connection.getServerConnection().receive(
				this.getBasicMessagePackage().getIndex(),
				this.getBasicMessagePackage().getPermitIdle());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isArriveTargetMachine() {
		return this.bmp.getDestMachineIndex() == com.FileManagerX.Globals.Configurations.This_MachineIndex;
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
		return this.bmp.getDestMachineIndex() == com.FileManagerX.Globals.Configurations.This_MachineIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
