package Commands;

public class RegisterUser extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String invitationCode;
	private BasicModels.User user;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setInvitationCode(String invitationCode) {
		if(invitationCode == null) {
			return false;
		}
		this.invitationCode = invitationCode;
		return true;
	}
	public boolean setUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getInvitationCode() {
		return this.invitationCode;
	}
	public BasicModels.User getUser() {
		return this.user;
	}
	public Replies.RegisterUser getReply() {
		return (Replies.RegisterUser)super.getReply();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RegisterUser() {
		initThis();
	}
	public RegisterUser(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		invitationCode = "";
		user = new BasicModels.User();
		super.setReply(new Replies.RegisterUser());
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
		c.addToBottom(new BasicModels.Config(super.output()));
		c.addToBottom(this.invitationCode);
		c.addToBottom(new BasicModels.Config(this.user.output()));
		
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		this.invitationCode = c.fetchFirstString();
		if(!c.getIsOK()) {
			return null;
		}
		return user.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		RegisterUser ru = (RegisterUser)o;
		this.invitationCode = ru.invitationCode;
		this.user = ru.user;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		RegisterUser ru = (RegisterUser)o;
		this.invitationCode = new String(ru.invitationCode);
		this.user.copyValue(ru.user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!super.isConnected()) {
			this.reply();
			return false;
		}
		if(!Globals.Configurations.IsServer) {
			this.getReply().setFailedReason("This Machine is Not a Server, RegisterUser Failed");
			this.getReply().setOK(false);
			this.reply();
			return false;
		}
		
		if(this.invitationCode == null || this.invitationCode.length() == 0) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Empty Invitation Code");
			this.reply();
			return false;
		}
		
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("Code");
		qc.setValue("'" + this.invitationCode + "'");
		BasicModels.Invitation i = Globals.Datas.DBManager.QueryInvitation(qc);
		if(i == null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Not Found such Invitation Code in DataBase. Code: " + this.invitationCode);
			this.reply();
			return false;
		}
		
		qc.setItemName("LoginName");
		qc.setValue("'" + this.user.getLoginName() + "'");
		BasicModels.User u = Globals.Datas.DBManager.QueryUser(qc);
		if(u != null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("User Login Name Existed");
			this.reply();
			return false;
		}
		
		user.setIndex(Globals.Configurations.Next_UserIndex + 1);
		user.setPriority(i.getUser().getPriority());
		user.setLevel(i.getUser().getLevel());
		user.setExperience(i.getUser().getExperience());
		user.setCoins(i.getUser().getCoins());
		user.setMoney(i.getUser().getMoney());
		if(!Globals.Datas.DBManager.updataUser(user)) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Register User to DataBase Failed");
			this.reply();
			return false;
		}
		
		Globals.Datas.DBManager.removeInvitation(i);
		this.getConnection().setUser(user);
		this.getReply().setUser(user);
		this.reply();
		return true;
	}
	public void reply() {
		this.setBasicMessagePackageToReply();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
