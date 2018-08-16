package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.*;

public class LoginServer extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private MachineInfo machineInfo;
	private boolean sendReply;
	
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
	public com.FileManagerX.Replies.LoginServer getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.LoginServer()); }
		return (com.FileManagerX.Replies.LoginServer)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginServer() {
		initThis();
	}
	public LoginServer(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.getBasicMessagePackage().setPermitIdle(Long.MAX_VALUE);
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
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		if(this.sendReply) { this.getReply().send(); }
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		if(com.FileManagerX.Globals.Datas.Client.size() > com.FileManagerX.Globals.Configurations.ConnectionLimit) {
			for(com.FileManagerX.Interfaces.IClientConnection con :
				com.FileManagerX.Globals.Datas.OtherServers.getContent()) {
				con.send(this);
			}
			this.sendReply = false;
			return false;
		}
		else {
			this.getReply().setMachineInfo(com.FileManagerX.Globals.Datas.ThisMachine);
			this.sendReply = true;
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
