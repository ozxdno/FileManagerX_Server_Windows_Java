package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.UserPriority;

public class RoutePath extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IRoutePathPackage rrp;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setRoutePath(com.FileManagerX.Interfaces.IRoutePathPackage rrp) {
		if(rrp == null) {
			return false;
		}
		this.rrp = rrp;
		return true;
	}
	
	public boolean setThis(com.FileManagerX.Interfaces.IRoutePathPackage rrp) {
		boolean ok = true;
		ok &= this.setRoutePath(rrp);
		return ok;
	}
	public boolean setThis(com.FileManagerX.Interfaces.IRoutePathPackage rrp,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.getBasicMessagePackage().getBroadcast().setType(
				com.FileManagerX.BasicEnums.BroadcastType.TO_ALL);
		ok &= this.setConnection(connection);
		ok &= this.setThis(rrp);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IRoutePathPackage getRoutePath() {
		return this.rrp;
	}
	
	public com.FileManagerX.Replies.RoutePath getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.RoutePath()); }
		return (com.FileManagerX.Replies.RoutePath)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RoutePath() {
		initThis();
	}
	private void initThis() {
		this.rrp = com.FileManagerX.Factories.CommunicatorFactory.createRRP();
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
		c.addToBottom(this.rrp.toConfig());
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
			c = this.rrp.input(c);
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
		RoutePath t = (RoutePath)o;
		this.rrp = t.rrp;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		RoutePath t = (RoutePath)o;
		this.rrp.copyValue(t.rrp);
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
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		com.FileManagerX.Interfaces.IRoutePathPackage rrp = this.getBasicMessagePackage().getRoutePathPackage();
		rrp.setArriveTime();
		
		this.getReply().getRoutePath().copyValue(rrp);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
