package Commands;

public class LoginUser extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String loginName;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setLoginName(String loginName) {
		if(loginName == null) {
			return false;
		}
		this.loginName = loginName;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getLoginName() {
		return this.loginName;
	}
	public Replies.LoginUser getReply() {
		return (Replies.LoginUser)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginUser() {
		initThis();
	}
	public LoginUser(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.loginName = "";
		super.setReply(new Replies.LoginUser());
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
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.getUserIndex());
		c.addToBottom(this.getPassword());
		c.addToBottom(this.getMachineIndex());
		c.addToBottom(this.getDepotIndex());
		c.addToBottom(this.getDataBaseIndex());
		c.addToBottom(this.loginName);
		
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.loginName = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		LoginUser c = (LoginUser)o;
		this.loginName = c.loginName;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginUser c = (LoginUser)o;
		this.loginName = new String(c.loginName);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!super.isConnected()) {
			this.reply();
			return false;
		}
		
		if(this.loginName == null || this.loginName.length() == 0) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Empty Login Name");
			this.reply();
			return false;
		}
		if(this.getPassword() == null || this.getPassword().length() == 0) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Empty Password");
			this.reply();
			return false;
		}
		
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("LoginName");
		qc.setValue("'" + this.loginName + "'");
		BasicModels.User u = Globals.Datas.DBManager.QueryUser(qc);
		if(u == null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Wrong User Index, Not Exist such User Index");
			this.reply();
			return false;
		}
		
		if(!u.getLoginName().equals(this.loginName)) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Wrong Login Name");
			this.reply();
			return false;
		}
		if(!u.getPassword().equals(this.getPassword())) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Wrong Password");
			this.reply();
			return false;
		}
		
		this.setUserIndex(u.getIndex());
		this.getConnection().setUser(u);
		this.reply();
		return true;
	}
	public void reply() {
		this.setUserIndexAndPassword();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
