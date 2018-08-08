package com.FileManagerX.Commands;


public class RegisterUser extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String invitationCode;
	private String loginName;
	private String password;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setInvitationCode(String code) {
		if(code == null) {
			return false;
		}
		if(code.length() == 0) {
			return false;
		}
		this.invitationCode = code;
		return true;
	}
	public boolean setLoginName(String name) {
		if(name == null) {
			return false;
		}
		if(name.length() == 0) {
			return false;
		}
		this.invitationCode = name;
		return true;
	}
	public boolean setPassword(String password) {
		if(password == null) {
			return false;
		}
		if(password.length() == 0) {
			return false;
		}
		this.invitationCode = password;
		return true;
	}

	public boolean setThis(String invitationCode, String loginName, String password) {
		boolean ok = true;
		ok &= this.setInvitationCode(invitationCode);
		ok &= this.setLoginName(loginName);
		ok &= this.setPassword(password);
		return ok;
	}
	public boolean setThis(String invitationCode, String loginName, String password, 
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(invitationCode, loginName, password);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getInvitationCode() {
		return this.invitationCode;
	}
	public String getLoginName() {
		return this.loginName;
	}
	public String getPassword() {
		return this.password;
	}
	
	public com.FileManagerX.Replies.RegisterUser getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.RegisterUser()); }
		return (com.FileManagerX.Replies.RegisterUser)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RegisterUser() {
		initThis();
	}
	private void initThis() {
		this.invitationCode = "";
		this.loginName = "";
		this.password = "";
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
		if(this.invitationCode.length() == 0) {
			this.getReply().setThis(false, "Empty InvitationCode");
			return false;
		}
		if(this.loginName.length() == 0) {
			this.getReply().setThis(false, "Empty LoginName");
			return false;
		}
		if(this.password.length() == 0) {
			this.getReply().setThis(false, "Empty Password");
			return false;
		}
		
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Invitation);
		com.FileManagerX.BasicModels.Invitation i = (com.FileManagerX.BasicModels.Invitation)
				com.FileManagerX.Globals.Datas.DBManager.query
				("[&] InvitationCode = '" + this.invitationCode + "'");
		if(i == null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason(
					"Not Found such Invitation Code in DataBase. Code: " + this.invitationCode);
			return false;
		}
		
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.User);
		com.FileManagerX.BasicModels.User exu = (com.FileManagerX.BasicModels.User)
				com.FileManagerX.Globals.Datas.DBManager.query
				("[&] LoginName = '" + this.loginName + "'");
		if(exu != null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("User Login Name Existed");
			return false;
		}
		
		com.FileManagerX.BasicModels.User u = i.getUser();
		u.setIndex(-1);
		u.setLoginName(loginName);
		u.setPassword(password);
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.User);
		boolean ok = com.FileManagerX.Globals.Datas.DBManager.update(u);
		if(!ok) {
			this.getReply().setThis(false, "Update User to DataBase Failed");
			return false;
		}
		
		this.getReply().setThis(u);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
