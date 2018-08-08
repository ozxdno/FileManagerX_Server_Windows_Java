package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.Config;
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
	public String output() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new Config(super.output()));
		c.addToBottom(new Config(this.machineInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.machineInfo.input(in);
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
		
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Machine);
		MachineInfo exm = (com.FileManagerX.BasicModels.MachineInfo)
				com.FileManagerX.Globals.Datas.DBManager.query
				("[&] Name = '" + this.machineInfo.getName() + "'");
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
