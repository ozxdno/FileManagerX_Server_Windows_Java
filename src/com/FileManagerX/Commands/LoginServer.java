package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.*;

public class LoginServer extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_NO_AVAILABLE_SERVER = "Not found available server";
	public final static String FAILED_FIND_NEXT_SERVER = "Try to find another server";
	
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
		if(o instanceof LoginServer) {
			super.copyReference(o);
			LoginServer qf = (LoginServer)o;
			this.machineInfo = qf.machineInfo;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof LoginServer) {
			super.copyValue(o);
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
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		if(this.getBasicMessagePackage().getProcess() <= 0) {
			this.getBasicMessagePackage().setProcess(this.getSourConnection().getProcessIndex());
		}
		
		int current = com.FileManagerX.Globals.Datas.Client.size();
		int limit = com.FileManagerX.Globals.Configurations.LimitForConnection;
		
		if(current >= limit) {
			if(this.getBasicMessagePackage().isDirect()) {
				this.getReply().setFailedReason(FAILED_NO_AVAILABLE_SERVER);
				this.getReply().setOK(false);
				return false;
			}
			
			com.FileManagerX.Interfaces.IRoutePathPackage rpp = this.getBasicMessagePackage().getRoutePathPackage();
			com.FileManagerX.Interfaces.IClientConnection con = com.FileManagerX.Globals.Datas.ServerConnection;
			long sm = con.getServerMachineInfo().getIndex();
			boolean found = false;
			
			if(con.isRunning() && !rpp.visited(sm)) {
				com.FileManagerX.Commands.LoginServer clone = new com.FileManagerX.Commands.LoginServer();
				clone.copyValue(this);
				clone.setSourConnection(this.getSourConnection());
				clone.setDestConnection(con);
				clone.getBasicMessagePackage().setDestMachineIndex(sm);
				clone.send();
				found = true;
			}
			
			com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it =
					com.FileManagerX.Globals.Datas.OtherServers.getIterator();
			while(it.hasNext()) {
				con = it.getNext();
				sm = con.getServerMachineInfo().getIndex();
				
				if(con.isRunning() && !rpp.visited(sm)) {
					com.FileManagerX.Commands.LoginServer clone = new com.FileManagerX.Commands.LoginServer();
					clone.copyValue(this);
					clone.setSourConnection(this.getSourConnection());
					clone.setDestConnection(con);
					clone.getBasicMessagePackage().setDestMachineIndex(sm);
					clone.send();
					found = true;
				}
			}
			this.getReply().setFailedReason(found ? FAILED_FIND_NEXT_SERVER : FAILED_NO_AVAILABLE_SERVER);
			this.getReply().setOK(false);
			return false;
		}
		else {
			this.getReply().setMachineInfo(com.FileManagerX.Globals.Datas.ThisMachine);
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
