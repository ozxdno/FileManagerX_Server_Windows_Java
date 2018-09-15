package com.FileManagerX.Commands;


public class RegisterUser extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static String FAILED_EMPTY_LOGIN_NAME = "Login Name is Empty";
	public final static String FAILED_EMPTY_PASSWORD = "Password is Empty";
	public final static String FAILED_EMPTY_INVITATION_CODE = "Invitation Code is Empty";
	public final static String FAILED_CODE_OVER_TIME = "Invitation Code is over time";
	public final static String FAILED_CODE_NO_COUNT = "The remain count of this Invitation Code is 0";
	public final static String FAILED_NOT_FOUND_INVITATION = "Wrong Invitation Code";
	public final static String FAILED_USER_EXISTED = "Login Name existed";
	public final static String FAILED_UPDATE_USER = "Update User to DataBase failed";
	
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
		this.loginName = name;
		return true;
	}
	public boolean setPassword(String password) {
		if(password == null) {
			return false;
		}
		if(password.length() == 0) {
			return false;
		}
		this.password = password;
		return true;
	}

	public boolean setThis(String invitationCode, String loginName, String password) {
		boolean ok = true;
		ok &= this.setInvitationCode(invitationCode);
		ok &= this.setLoginName(loginName);
		ok &= this.setPassword(password);
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
			this.getReply().setThis(false, FAILED_EMPTY_INVITATION_CODE);
			return false;
		}
		if(this.loginName.length() == 0) {
			this.getReply().setThis(false, FAILED_EMPTY_LOGIN_NAME);
			return false;
		}
		if(this.password.length() == 0) {
			this.getReply().setThis(false, FAILED_EMPTY_PASSWORD);
			return false;
		}
		
		com.FileManagerX.BasicModels.Invitation i = new com.FileManagerX.BasicModels.Invitation();
		boolean ok = com.FileManagerX.Globals.Datas.DBManager.query(
				"qcs = 1|[&] code = " + this.invitationCode,
				i,
				com.FileManagerX.DataBase.Unit.Invitation
			);
		if(!ok) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason(FAILED_NOT_FOUND_INVITATION);
			return false;
		}
		
		i.setRemainAmount(i.getRemainAmount()-1);
		
		if(i.getRemainAmount() < 0) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason(FAILED_CODE_NO_COUNT);
			return false;
		}
		
		if(com.FileManagerX.Tools.Time.getTicks() > i.getEndTime()) {
			com.FileManagerX.Globals.Datas.DBManager.remove(i, com.FileManagerX.DataBase.Unit.Invitation);
			this.getReply().setOK(false);
			this.getReply().setFailedReason(FAILED_CODE_OVER_TIME);
			return false;
		}
		
		ok = com.FileManagerX.Globals.Datas.DBManager.query(
				"qcs = 1|[&] loginName = " + this.loginName,
				null,
				com.FileManagerX.DataBase.Unit.User
			);
		if(ok) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason(FAILED_USER_EXISTED);
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
		
		ok = com.FileManagerX.Globals.Datas.DBManager.update(u, com.FileManagerX.DataBase.Unit.User);
		if(!ok) {
			this.getReply().setThis(false, FAILED_UPDATE_USER);
			return false;
		}
		
		ok = i.getRemainAmount() <= 0 ?
				com.FileManagerX.Globals.Datas.DBManager.remove(i, com.FileManagerX.DataBase.Unit.Invitation) :
				com.FileManagerX.Globals.Datas.DBManager.update(i, com.FileManagerX.DataBase.Unit.Invitation);
		
		this.getReply().setThis(u);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
