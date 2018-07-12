package Commands;

public class LoginIndex extends Comman implements Interfaces.ICommands {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Replies.LoginIndex getReply() {
		return (Replies.LoginIndex)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginIndex() {
		initThis();
	}
	public LoginIndex(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		super.setReply(new Replies.LoginIndex());
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
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight_PriorityEnough(BasicEnums.UserPriority.Admin)) {
			this.reply();
			return false;
		}
		
		this.getReply().setLoginIndex(this.getConnection().getIndex());
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
