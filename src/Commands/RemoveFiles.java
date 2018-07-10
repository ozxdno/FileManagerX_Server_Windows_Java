package Commands;

public class RemoveFiles extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setQueryConditions(DataBaseManager.QueryConditions conditions) {
		this.conditions = conditions;
		return true;
	}
	public boolean setQueryConditions(String conditions) {
		try {
			DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
			qcs.stringToThis(conditions);
			this.conditions = qcs;
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean setQueryCondition(DataBaseManager.QueryCondition condition) {
		DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
		qcs.add(condition);
		this.conditions = qcs;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DataBaseManager.QueryConditions getQueryConditions() {
		return this.conditions;
	}
	
	public Replies.RemoveFiles getReply() {
		return (Replies.RemoveFiles)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RemoveFiles() {
		initThis();
	}
	public RemoveFiles(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		super.setReply(new Replies.RemoveFiles());
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
		c.addToBottom(new BasicModels.Config(this.conditions.output()).getValue());
		
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.conditions.input(in);
	}
	public void copyReference(Object o) {
		RemoveFiles qf = (RemoveFiles)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		RemoveFiles qf = (RemoveFiles)o;
		super.copyValue(o);
		this.conditions.copyValue(qf.conditions);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected()) {
			this.reply();
			return false;
		}
		if(!this.isLogin()) {
			this.reply();
			return false;
		}
		if(!this.isUserIndexRight()) {
			this.reply();
			return false;
		}
		if(!this.isPasswordRight()) {
			this.reply();
			return false;
		}
		if(!this.isPriorityEnough(BasicEnums.UserPriority.Member)) {
			this.reply();
			return false;
		}
		if(this.conditions == null) {
			super.getReply().setOK(false);
			super.getReply().setFailedReason("Wrong Query Conditions");
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			if(!this.remove()) {
				this.reply();
				return false;
			}
			if(this.getReply().getAmount() == 0) {
				this.reply();
				return true;
			}
			
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<this.getReply().getAmount(); i++) {
				this.getReply().setFile(this.getReply().getFiles().getContent().get(i));
				st.inputNextItem(this.getReply().output());
			}
			
			this.replyTotal(st);
			return true;
		}
		else {
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
			
			cm.removeFiles(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					conditions);
			Replies.RemoveFiles rep = (Replies.RemoveFiles)cm.getReply();
			if(rep == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Execute Failed");
				this.getReply().setOK(false);
				return false;
			}
			
			this.setReply(rep);
			if(!rep.isOK()) {
				this.reply();
				return false;
			}
			
			if(this.getReply().getFiles().size() == 0) {
				this.reply();
				return true;
			}
			
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<this.getReply().getFiles().size(); i++) {
				this.getReply().setFile(this.getReply().getFiles().getContent().get(i));
				st.inputNextItem(this.getReply().output());
			}
			
			this.replyTotal(st);
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

	public boolean remove() {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(
				this.getBasicMessagePackage().getDestDepotIndex());
		if(dbm == null) {
			this.getReply().setFailedReason("Not Found DBManager");
			this.getReply().setOK(false);
			return false;
		}
		
		BasicCollections.BaseFiles res = dbm.QueryFiles(conditions);
		if(res == null) {
			this.getReply().setFailedReason("Remove Files from DataBase Failed");
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().getFiles().clear();
		for(BasicModels.BaseFile i : res.getContent()) {
			boolean ok = dbm.removeFile(i);
			if(!ok) {
				this.getReply().getFiles().add(i);
			}
		}
		this.getReply().setAmount(this.getReply().getFiles().size());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
