package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.MachineInfo;

public class LoginServer extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private MachineInfo machineInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setMachineInfo(MachineInfo machineInfo) {
		if(machineInfo == null) {
			return false;
		}
		this.machineInfo = machineInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginServer() {
		initThis();
	}
	private void initThis() {
		this.machineInfo = new MachineInfo();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(MachineInfo machineInfo) {
		boolean ok = true;
		ok &= this.setThis(machineInfo);
		return ok;
	}
	public boolean setThis(MachineInfo machineInfo, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(machineInfo);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.machineInfo.toConfig());
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
		try {
			if(!c.getIsOK()) { return c; }
			c = super.input(c);
			if(!c.getIsOK()) { return c; }
			c = this.machineInfo.input(c);
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof LoginServer) {
			super.copyReference(o);
			LoginServer qf = (LoginServer)o;
			this.machineInfo = qf.machineInfo;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof LoginServer) {
			super.copyReference(o);
			LoginServer qf = (LoginServer)o;
			this.machineInfo.copyValue(qf.machineInfo);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		
		boolean ok = this.executeInLocal();
		this.store();
		return ok;
	}
	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			return false;
		}
		
		if(!com.FileManagerX.Globals.Configurations.Connected) {
			com.FileManagerX.Globals.Configurations.Server_MachineIndex = this.machineInfo.getIndex();
			com.FileManagerX.Globals.Datas.ServerMachine.copyReference(this.machineInfo);
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
