package Commands;

public class Test extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String test;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setTestString(String test) {
		if(test == null) {
			return false;
		}
		this.test = test;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getTestString() {
		return this.test;
	}
	
	public Replies.Test getReply() {
		return (Replies.Test)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Test() {
		initThis();
	}
	public Test(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.test = "";
		super.setReply(new Replies.Test());
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
		c.addToBottom(this.test);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		this.test = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Test t = (Test)o;
		this.test = t.test;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Test t = (Test)o;
		this.test = new String(t.test);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			Replies.Test rep = this.getReply();
			rep.setTestString(test);
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
			Replies.Test rep = (Replies.Test)cc.execute();
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
		BasicEnums.ErrorType.EXECUTE_COMMAND_FAILED.register("The Reply from other Connection is NULL");
		this.getReply().setOK(false);
		this.getReply().setFailedReason("The Reply from other Connection is NULL");
		this.reply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
