package Commands;

public class CloseServer extends Comman implements Interfaces.ICommands {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Replies.CloseServer getReply() {
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
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight_PriorityEnough(BasicEnums.UserPriority.Admin)) {
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			this.getConnection().setCloseServer(true);
			this.reply();
			return true;
		}
		else {
			if(this.isSelfToSelf()) {
				this.reply();
				return false;
			}
			
			Interfaces.ICommandConnector cc = Factories.CommunicatorFactory.createCommandConnector();
			cc.setIsExecuteCommand(true);
			cc.setDestMachineIndex(this.getBasicMessagePackage().getDestMachineIndex());
			cc.setSendCommand(this.output());
			cc.setSourConnection(this.getConnection());
			Replies.CloseServer rep = (Replies.CloseServer)cc.execute();
			if(rep == null) {
				this.replyNULL();
				return false;
			}
			
			this.setReply(rep);
			this.reply();
			return true;
		}
	}
	public void reply() {
		this.setBasicMessagePackageToReply();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	public void replyNULL() {
		this.getReply().setOK(false);
		this.getReply().setFailedReason("The Reply from other Connection is NULL");
		this.reply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
