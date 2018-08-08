package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.Config;
import com.FileManagerX.BasicModels.MachineInfo;
import com.FileManagerX.Globals.Configurations;
import com.FileManagerX.Globals.Datas;

public class LoginMachine extends BaseReply {

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

	public LoginMachine() {
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
		
		boolean ok = this.executeInLocal();
		this.store();
		return ok;
	}
	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			this.getConnection().disconnect();
			return false;
		}
		
		this.getConnection().getServerConnection().setServerMachineInfo(this.machineInfo);
		this.getConnection().getClientConnection().setClientMachineInfo(this.machineInfo);
		this.getConnection().getServerConnection().setConnectionName();
		this.getConnection().getClientConnection().setConnectionName();
		
		Configurations.This_MachineIndex = this.machineInfo.getIndex();
		Datas.ThisMachine.copyValue(this.machineInfo);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
