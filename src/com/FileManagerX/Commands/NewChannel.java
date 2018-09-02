package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.ConnectionType;
import com.FileManagerX.BasicEnums.UserPriority;
import com.FileManagerX.Factories.CommunicatorFactory;
import com.FileManagerX.Interfaces.IClientConnection;

public class NewChannel extends BaseCommand {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static String FAILED_NOT_BUILD_SOCKET = "Build Socket Failed";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.BasicEnums.SocketType type;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(com.FileManagerX.BasicEnums.SocketType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicEnums.SocketType getType() {
		return this.type;
	}
	
	public com.FileManagerX.Replies.NewChannel getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.NewChannel()); }
		return (com.FileManagerX.Replies.NewChannel)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public NewChannel() {
		initThis();
	}
	public NewChannel(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.type = com.FileManagerX.BasicEnums.SocketType.IPV4_TCP;
	}
	
	public boolean setThis(com.FileManagerX.BasicEnums.SocketType type) {
		boolean ok = true;
		ok &= this.setType(type);
		return ok;
	}
	public boolean setThis(com.FileManagerX.BasicEnums.SocketType type,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(type);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.type.toString());
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
			this.type = com.FileManagerX.BasicEnums.SocketType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof NewChannel) {
			super.copyReference(o);
			NewChannel nc = (NewChannel)o;
			this.type = nc.type;
		}
		if(o instanceof BaseCommand) {
			super.copyReference(o);
		}
	}
	public void copyValue(Object o) {
		if(o instanceof NewChannel) {
			super.copyValue(o);
			NewChannel nc = (NewChannel)o;
			this.type = nc.type;
		}
		if(o instanceof BaseCommand) {
			super.copyValue(o);
		}
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
		if(!this.isLoginMachine()) {
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
		if(!this.isSourMachineIndexExist()) {
			this.getReply().send();
			return false;
		}
		if(!this.isDestMachineIndexExist()) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean executeInLocal() {
		IClientConnection con = CommunicatorFactory.createRunningClientConnection(
				this.getSourMachineInfo(),
				this.getDestMachineInfo(),
				type
				);
		if(con == null) {
			this.getReply().setFailedReason(FAILED_NOT_BUILD_SOCKET);
			this.getReply().setOK(false);
			return false;
		}
		
		con.setServerUser(com.FileManagerX.Globals.Datas.ThisUser);
		con.setClientUser(com.FileManagerX.Globals.Datas.ThisUser);
		con.setType(ConnectionType.UNDEFINE);
		
		String failedReason = con.login();
		boolean ok = failedReason.length() == 0;
		
		this.getReply().setIndex(con.getIndex());
		this.getReply().setFailedReason(failedReason);
		this.getReply().setOK(ok);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
