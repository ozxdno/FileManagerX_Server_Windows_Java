package com.FileManagerX.Replies;

import com.FileManagerX.Globals.*;

public class LoginConnection extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis() {
		return true;
	}
	public boolean setThis(com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			return false;
		}
		
		Datas.Server.add(this.getConnection().getServerConnection());
		Datas.Client.add(this.getConnection().getClientConnection());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
