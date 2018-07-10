package Commands;

/**
 * 有些命令可能需要更长的响应时间，所以可以对发送等待和接收等待进行设置。
 * 
 * @author ozxdno
 *
 */
public class TimeForExecute extends Comman implements Interfaces.ICommands {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long receiveWaitTicks;
	private long sendWaitTicks;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setReceiveWaitTicks(long receiveWaitTicks) {
		if(receiveWaitTicks < 0) {
			return false;
		}
		this.receiveWaitTicks = receiveWaitTicks;
		return true;
	}
	public boolean setSendWaitTicks(long sendWaitTicks) {
		if(sendWaitTicks < 0) {
			return false;
		}
		this.sendWaitTicks = sendWaitTicks;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getReceiveWaitTicks() {
		return this.receiveWaitTicks;
	}
	public long getSendWaitTicks() {
		return this.sendWaitTicks;
	}
	
	public Replies.TimeForExecute getReply() {
		return (Replies.TimeForExecute)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TimeForExecute() {
		initThis();
	}
	public TimeForExecute(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.receiveWaitTicks = Globals.Configurations.TimeForCommandReceive;
		this.sendWaitTicks = Globals.Configurations.TimeForCommandSend;
		super.setReply(new Replies.TimeForExecute());
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
		c.addToBottom(this.receiveWaitTicks);
		c.addToBottom(this.sendWaitTicks);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.receiveWaitTicks = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sendWaitTicks = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		TimeForExecute t = (TimeForExecute)o;
		this.receiveWaitTicks = t.receiveWaitTicks;
		this.sendWaitTicks = t.sendWaitTicks;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		TimeForExecute t = (TimeForExecute)o;
		this.receiveWaitTicks = t.receiveWaitTicks;
		this.sendWaitTicks = t.sendWaitTicks;
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
			Interfaces.ICommandsManager cm = cc.execute();
			if(cm == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Connection is Closed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			cm.getSWRE().setReceiveWaitTicks(receiveWaitTicks);
			cm.getSWRE().setSendWaitTicks(sendWaitTicks);
			
			cm.timeForExecute(
					this.getBasicMessagePackage().getDestMachineIndex(),
					receiveWaitTicks,
					sendWaitTicks);
			Replies.TimeForExecute rep = (Replies.TimeForExecute)cm.getReply();
			if(rep == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Execute Failed");
				this.getReply().setOK(false);
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
