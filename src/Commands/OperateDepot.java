package Commands;

public class OperateDepot extends Comman implements Interfaces.ICommands {
	

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DepotManager.Operator operator;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setOperator(DepotManager.Operator operator) {
		if(operator == null) {
			return false;
		}
		this.operator = operator;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DepotManager.Operator getOperator() {
		return this.operator;
	}
	public Replies.OperateDepot getReply() {
		return (Replies.OperateDepot)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public OperateDepot() {
		initThis();
	}
	public OperateDepot(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.operator = new DepotManager.Operator();
		this.setReply(new Replies.OperateDepot());
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
		c.addToBottom(new BasicModels.Config(this.operator.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.operator.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		OperateDepot t = (OperateDepot)o;
		this.operator = t.operator;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		OperateDepot t = (OperateDepot)o;
		this.operator.copyValue(t.operator);
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
			DepotManager.Operator existOp = Globals.Datas.Operators.search(this.operator.getIndex());
			if(existOp != null) {
				this.getReply().setOperator(existOp);
				this.reply();
				return true;
			}
			this.operator.startProcess();
			this.getReply().setOperator(operator);
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
			
			cm.operateDepot(this.getBasicMessagePackage().getDestMachineIndex(), this.operator);
			Replies.OperateDepot rep = (Replies.OperateDepot)cm.getReply();
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
