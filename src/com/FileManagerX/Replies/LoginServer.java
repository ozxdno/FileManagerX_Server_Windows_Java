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

	public boolean executeInLocal() {
		
		// 没有给出替换建议
		if(this.getFailedReason().equals(com.FileManagerX.Commands.LoginServer.FAILED_FIND_NEXT_SERVER)) {
			this.setStore(false);
			return false;
		}
		
		if(com.FileManagerX.Commands.LoginServer.FAILED_NO_AVAILABLE_SERVER.equals(this.getFailedReason())) {
			this.setStore(true);
			return false;
		}
		
		// 已经存在替换建议
		com.FileManagerX.Interfaces.IReply rep = com.FileManagerX.Globals.Datas.Receiver.searchByKey(
				this.getBasicMessagePackage().getIndex()
			);
		if(rep != null) {
			this.setStore(false);
			return false;
		}
		
		// 是否需要替换
		long preServer = this.getSourConnection().getClientConnection().getServerMachineInfo().getIndex();
		long disServer = this.machineInfo.getIndex();
		this.setStore(true);
		
		// 更新信息
		if(com.FileManagerX.Globals.Configurations.Refresh) {
			com.FileManagerX.Globals.Datas.ServerMachine.copyReference(this.machineInfo);
			com.FileManagerX.Globals.Configurations.Server_MachineIndex = this.machineInfo.getIndex();
		}
		
		if(preServer == disServer) { // 保留
			this.getSourConnection().getServerConnection().setClientMachineInfo(this.machineInfo);
			this.getSourConnection().getClientConnection().setServerMachineInfo(this.machineInfo);
			return true;
		}
		else { // 替换
			this.getSourConnection().getServerConnection().setClientMachineInfo(this.machineInfo);
			this.getSourConnection().getClientConnection().setServerMachineInfo(this.machineInfo);
			this.getSourConnection().getServerConnection().stopProcess();
			this.getSourConnection().getClientConnection().stopProcess();
			this.setOK(false);
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
