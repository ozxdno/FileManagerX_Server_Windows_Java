package Commands;

public class UpdateUser extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.User user;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.User getUser() {
		return this.user;
	}
	
	public Replies.UpdateUser getReply() {
		return (Replies.UpdateUser)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateUser() {
		initThis();
	}
	public UpdateUser(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.user = new BasicModels.User();
		super.setReply(new Replies.UpdateUser());
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
		c.addToBottom(new BasicModels.Config(this.user.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		return this.user.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateUser um = (UpdateUser)o;
		this.user = um.user;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateUser um = (UpdateUser)o;
		this.user.copyValue(um.user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!super.isConnected()) {
			this.reply();
			return false;
		}
		if(!super.isLogin()) {
			this.reply();
			return false;
		}
		if(!super.isUserIndexRight()) {
			this.reply();
			return false;
		}
		if(!super.isPasswordRight()) {
			this.reply();
			return false;
		}
		if(!super.isPriorityEnough(BasicEnums.UserPriority.Member)) {
			this.reply();
			return false;
		}
		if(this.user.getIndex() != this.getConnection().getUser().getIndex()) {
			this.getReply().setFailedReason("Update User Failed: Index Changed");
			this.getReply().setOK(false);
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			if(!Globals.Datas.DBManager.updataUser(user)) {
				this.getReply().setOK(false);
				this.getReply().setFailedReason("Update to Depot Failed");
				this.reply();
				return false;
			}
			
			this.getReply().setUser(user);
			this.reply();
			return true;
		}
		else {
			this.getReply().setFailedReason("Unsupport Command at This Machine");
			this.getReply().setOK(false);
			this.reply();
			return false;
		}
	}
	public void reply() {
		this.setBasicMessagePackageToReply();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
