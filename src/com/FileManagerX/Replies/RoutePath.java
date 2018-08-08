package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.Config;

public class RoutePath extends BaseReply {

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
		ok &= this.setConnection(connection);
		ok &= this.setThis(rrp);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IRoutePathPackage getRoutePath() {
		return this.rrp;
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
	public String output() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new Config(super.output()));
		c.addToBottom(new Config(rrp.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		return rrp.input(in);
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

	public boolean executeInLocal() {
		if(!this.isOK()) { return false; }
		
		
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}