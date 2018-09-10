package com.FileManagerX.Commands;

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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	public com.FileManagerX.Replies.LoginMachine getReply() {
		if(super.getReply() == null) {
			this.setReply(new com.FileManagerX.Replies.LoginMachine());
			this.getReply().copyReversePath(this);
		}
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
	public com.FileManagerX.BasicModels.Config toConfig() {
		Config c = new Config();
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
		
		if(!c.getIsOK()) { return c; }
		c = super.input(c);
		if(!c.getIsOK()) { return c; }
		c = this.machineInfo.input(c);
		if(!c.getIsOK()) { return c; }
		
		return c;
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
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		if(this.getBasicMessagePackage().isDirect()) {
			this.getSourConnection().getServerConnection().setClientMachineInfo(this.machineInfo);
			this.getSourConnection().getClientConnection().setServerMachineInfo(this.machineInfo);
			this.getReply().setMachineInfo(this.machineInfo);
			return true;
		}
		
		String path = com.FileManagerX.Tools.StringUtil.link(
				com.FileManagerX.Tools.List2Array.toLongArray(
						this.getBasicMessagePackage().getRoutePathPackage().getSourMountPath()),
				" "
			);
		
		this.machineInfo.setPath(path);
		boolean ok = com.FileManagerX.Globals.Datas.DBManager.update(
				this.machineInfo,
				com.FileManagerX.DataBase.Unit.Machine
			);
		
		if(!ok) {
			this.getReply().setFailedReason(FAILED_OPERATE_DATABASE);
			this.getReply().setOK(false);
			return false;
		}
		
		this.getSourConnection().getServerConnection().setClientMachineInfo(this.machineInfo);
		this.getSourConnection().getClientConnection().setServerMachineInfo(this.machineInfo);
		this.getReply().setMachineInfo(this.machineInfo);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
