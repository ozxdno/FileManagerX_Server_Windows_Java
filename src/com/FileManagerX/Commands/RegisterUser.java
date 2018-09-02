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
		c.addToBottom(this.invitationCode);
		c.addToBottom(this.loginName);
		c.addToBottom(this.password);
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
			this.invitationCode = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.loginName = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.password = c.fetchFirstString();
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
			RegisterUser ru = (RegisterUser)o;
			this.invitationCode = ru.invitationCode;
			this.loginName = ru.loginName;
			this.password = ru.password;
		}
		if(o instanceof BaseCommand) {
			super.copyReference(o);
		}
	}
	public void copyValue(Object o) {
		if(o instanceof NewChannel) {
			super.copyValue(o);
			RegisterUser ru = (RegisterUser)o;
			this.invitationCode = ru.invitationCode;
			this.loginName = ru.loginName;
			this.password = ru.password;
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
		
		com.FileManagerX.BasicModels.Invitation i = (com.FileManagerX.BasicModels.Invitation)
				com.FileManagerX.Globals.Datas.DBManager.query2(
						"[&] InvitationCode = '" + this.invitationCode + "'",
						com.FileManagerX.DataBase.Unit.Invitation
					);
		if(i == null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason(
					"Not Found such Invitation Code in DataBase. Code: " + this.invitationCode);
			return false;
		}
		
		com.FileManagerX.BasicModels.User exu = (com.FileManagerX.BasicModels.User)
				com.FileManagerX.Globals.Datas.DBManager.query2(
						"[&] LoginName = '" + this.loginName + "'",
						com.FileManagerX.DataBase.Unit.User
					);
		if(exu != null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("User Login Name Existed");
			return false;
		}
		
		com.FileManagerX.BasicModels.User u = new com.FileManagerX.BasicModels.User();
		u.setIndex(-1);
		u.setLoginName(loginName);
		u.setPassword(password);
		u.setPriority(i.getPriority());
		u.setLevel(i.getLevel());
		u.setExperience(i.getExperience());
		u.setCoins(i.getCoins());
		u.setMoney(i.getMoney());
		
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
