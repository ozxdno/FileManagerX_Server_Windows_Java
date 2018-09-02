package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.MachineInfo;

public class RegisterMachine extends BaseCommand {

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
	
	public boolean setThis(MachineInfo machineInfo) {
		return this.setMachineInfo(machineInfo);
	}
	public boolean setThis(MachineInfo machineInfo, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(machineInfo);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	public com.FileManagerX.Replies.RegisterMachine getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.RegisterMachine()); }
		return (com.FileManagerX.Replies.RegisterMachine)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RegisterMachine() {
		initThis();
	}
	public RegisterMachine(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.machineInfo = new MachineInfo();
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
		super.copyReference(o);
		RegisterMachine qf = (RegisterMachine)o;
		this.machineInfo = qf.machineInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		RegisterMachine qf = (RegisterMachine)o;
		this.machineInfo.copyValue(qf.machineInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		if(!this.isLoginUser()) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		if(this.machineInfo.getName().length() == 0) {
			this.getReply().setThis(false, "Empty Name");
			return false;
		}
		
		MachineInfo exm = (com.FileManagerX.BasicModels.MachineInfo)
				com.FileManagerX.Globals.Datas.DBManager.query2(
						"[&] Name = '" + this.machineInfo.getName() + "'",
						com.FileManagerX.DataBase.Unit.Machine
					);
		if(exm != null) {
			this.getReply().setThis(false, "Machine Existed");
			return false;
		}
		
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Machine);
		boolean ok = com.FileManagerX.Globals.Datas.DBManager.update(machineInfo);
		if(!ok) {
			this.getReply().setThis(false, "Update Machine  to DataBase Failed");
			return false;
		}
		
		this.getReply().setThis(machineInfo);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
