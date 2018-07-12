package Commands;

public class Exchange extends Comman implements Interfaces.ICommands {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Replies.Exchange getReply() {
		return (Replies.Exchange)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Exchange() {
		initThis();
	}
	public Exchange(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		super.setReply(new Replies.Exchange());
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
		if(!this.isDestMachineIndexExist()) {
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			this.getConnection().setExchange(true);
			this.reply();
			return true;
		}
		else {
			this.getReply().setFailedReason("Unsupport Command in This Machine");
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
