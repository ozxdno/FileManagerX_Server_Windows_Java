package com.FileManagerX.Replies;

public class LoginConnection extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean executeInLocal() {
		if(!this.isOK()) {
			return false;
		}
		
		com.FileManagerX.Globals.Datas.Server.add(this.getSourConnection().getServerConnection());
		com.FileManagerX.Globals.Datas.Client.add(this.getSourConnection().getClientConnection());
		com.FileManagerX.Interfaces.IClientConnection con =
				this.getSourConnection().getClientConnection();
		
		if(com.FileManagerX.BasicEnums.MachineType.SERVER.equals(con.getServerMachineInfo().getType())) {
			if(con != com.FileManagerX.Globals.Datas.ServerConnection) {
				com.FileManagerX.Globals.Datas.OtherServers.add(con);
			}
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
