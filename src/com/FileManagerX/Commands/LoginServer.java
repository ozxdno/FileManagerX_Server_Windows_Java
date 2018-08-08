package com.FileManagerX.Commands;

/**
 * To get ServerMachineInfo from Server1st. <br>
 * Maybe Connections is so many that one server cannot process them, <br>
 * If over CPU content, <br>
 * this connection will connect to another available server.
 * 
 * 
 * @author ozxdno
 *
 */
public class LoginServer extends BaseCommand {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Replies.LoginServer getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.LoginServer()); }
		return (com.FileManagerX.Replies.LoginServer)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginServer() {
		initThis();
	}
	private void initThis() {
		;
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
		if(com.FileManagerX.Globals.Datas.Client.size() > com.FileManagerX.Globals.Configurations.ConnectionLimit) {
			if(!com.FileManagerX.Globals.Datas.NextServer.isRunning()) {
				this.getReply().setFailedReason("Connection Amount Limited");
				this.getReply().setOK(false);
				this.getReply().getMachineInfo().setIndex(-1);
				return false;
			}
			
			com.FileManagerX.Commands.LoginServer ls = new com.FileManagerX.Commands.LoginServer();
			ls.setThis(com.FileManagerX.Globals.Datas.NextServer);
			ls.send();
			com.FileManagerX.Replies.LoginServer rep = (com.FileManagerX.Replies.LoginServer)ls.receive();
			if(rep == null || !rep.isOK()) {
				this.getReply().setFailedReason("Connection Amount Limited");
				this.getReply().setOK(false);
				this.getReply().getMachineInfo().setIndex(-1);
				return false;
			}
			
			this.getReply().setFailedReason("Connection Amount Limited");
			this.getReply().setOK(false);
			this.getReply().setMachineInfo(rep.getMachineInfo());
			return false;
		}
		else {
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
