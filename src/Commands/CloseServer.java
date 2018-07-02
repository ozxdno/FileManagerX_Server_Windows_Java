package Commands;

public class CloseServer extends Comman implements Interfaces.ICommands {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Replies.CloseServer getReplies() {
		return (Replies.CloseServer)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CloseServer() {
		initThis();
	}
	public CloseServer(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		super.setReply(new Replies.CloseServer());
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
		return super.output(this.getClass().getSimpleName());
	}
	public String input(String in) {
		return super.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
	}
	public void copyValue(Object o) {
		super.copyValue(o);
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
		if(!super.isPriorityEnough(BasicEnums.UserPriority.Admin)) {
			this.reply();
			return false;
		}
		
		super.getConnection().setCloseServer(true);
		this.reply();
		return true;
	}
	public void reply() {
		super.setUserIndexAndPassword();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
