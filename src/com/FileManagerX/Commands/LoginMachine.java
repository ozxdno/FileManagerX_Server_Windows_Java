package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.BasicModels.*;

public class LoginMachine extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_OPERATE_DATABASE = "Operate DataBase Failed";
	public final static String FAILED_NO_SUCH_MACHINE = "Not Found such Machine";
	
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
	public com.FileManagerX.Replies.LoginMachine getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.LoginMachine()); }
		return (com.FileManagerX.Replies.LoginMachine)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginMachine() {
		initThis();
	}
	public LoginMachine(String command) {
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
		LoginMachine qf = (LoginMachine)o;
		this.machineInfo = qf.machineInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginMachine qf = (LoginMachine)o;
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
		if(!this.isUserIndexRight()) {
			this.getReply().send();
			return false;
		}
		if(!this.isPasswordRight()) {
			this.getReply().send();
			return false;
		}
		if(!this.isPriorityEnough(UserPriority.Member)) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		if(!this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Machine);
			this.machineInfo = (com.FileManagerX.BasicModels.MachineInfo)
					com.FileManagerX.Globals.Datas.DBManager.query("[&] Name = '" + this.machineInfo.getName() + "'");
		}
		if(this.machineInfo == null) {
			this.getReply().setFailedReason(FAILED_OPERATE_DATABASE);
			this.getReply().setOK(false);
			return false;
		}
		if(this.machineInfo.getIndex() <= 0) {
			this.getReply().setFailedReason(FAILED_NO_SUCH_MACHINE);
			this.getReply().setOK(false);
			return false;
		}
		
		this.getConnection().getServerConnection().setClientMachineInfo(this.machineInfo);
		this.getConnection().getClientConnection().setServerMachineInfo(this.machineInfo);
		this.getReply().setMachineInfo(this.machineInfo);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
