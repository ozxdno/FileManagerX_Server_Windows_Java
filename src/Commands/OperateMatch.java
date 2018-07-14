package Commands;

public class OperateMatch extends Comman implements Interfaces.ICommands {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Match.Match match;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setOperateMatch(Match.Match match) {
		if(match == null) {
			return false;
		}
		this.match = match;
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Match.Match getOperateMatch() {
		return this.match;
	}
	
	public Replies.OperateMatch getReply() {
		return (Replies.OperateMatch)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public OperateMatch() {
		initThis();
	}
	public OperateMatch(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.match = new Match.Match();
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
		c.addToBottom(new BasicModels.Config(this.match.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.match.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		OperateMatch t = (OperateMatch)o;
		this.match = t.match;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		OperateMatch t = (OperateMatch)o;
		this.match.copyValue(t.match);
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
			Match.Match existOp = Globals.Datas.Matches.search(this.match.getIndex());
			if(existOp != null) {
				this.getReply().setOperateMatch(existOp);
			}
			else {
				this.match.startProcess();
				this.getReply().setOperateMatch(match);
			}
			
			int sz = this.match.getMatcher().getResult().size();
			if(sz == 0) {
				this.getReply().setFile(new BasicModels.BaseFile());
				this.getReply().setAmount(0);
				this.reply();
				return true;
			}
			
			this.getReply().setFiles(this.match.getMatcher().getResult());
			this.getReply().setAmount(sz);
			this.getReply().setOK(true);
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<sz; i++) {
				this.getReply().setFile(this.match.getMatcher().getResult().getContent().get(i));
				st.inputNextItem(this.getReply().output());
			}
			this.replyTotal(st);
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
			
			cm.operateMatch(this.getBasicMessagePackage().getDestMachineIndex(), this.match);
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
