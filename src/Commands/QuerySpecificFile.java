package Commands;

public class QuerySpecificFile extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	private BasicEnums.FileType type;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setQueryConditions(DataBaseManager.QueryConditions conditions) {
		this.conditions = conditions;
		return true;
	}
	public boolean setQueryConditions(String conditions) {
		DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
		try {
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
	public boolean setType(BasicEnums.FileType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DataBaseManager.QueryConditions getQueryConditions() {
		return this.conditions;
	}
	public BasicEnums.FileType getType() {
		return this.type;
	}
	
	public Replies.QuerySpecificFile getReply() {
		return (Replies.QuerySpecificFile)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QuerySpecificFile() {
		initThis();
	}
	public QuerySpecificFile(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		this.type = BasicEnums.FileType.Unsupport;
		super.setReply(new Replies.QuerySpecificFile());
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
		c.addToBottom(this.type.toString());
		c.addToBottom(new BasicModels.Config(this.conditions.output()).getValue());
		
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		try {
			BasicModels.Config c = new BasicModels.Config(in);
			this.type = BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			
			return this.conditions.input(in);
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
		
	}
	public void copyReference(Object o) {
		QuerySpecificFile qf = (QuerySpecificFile)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
		this.type = qf.type;
	}
	public void copyValue(Object o) {
		QuerySpecificFile qf = (QuerySpecificFile)o;
		super.copyValue(o);
		this.conditions.copyValue(qf.conditions);
		this.type = qf.type;
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
		if(!this.isExistDest_MachineIndex_DepotIndex()) {
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(
					this.getBasicMessagePackage().getDestDepotIndex());
			if(dbm == null) {
				this.getReply().setFailedReason("Not Found DBManager");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			BasicModels.BaseFile f = dbm.QuerySpecificFile(type, conditions);
			if(f == null) {
				this.getReply().setOK(false);
				this.getReply().setFailedReason("Not Exist");
				this.reply();
				return false;
			}
			
			Replies.QuerySpecificFile qd = this.getReply();
			qd.setFile(f);
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
			
			BasicModels.BaseFile file = cm.querySpecificFile(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					this.type,
					this.conditions
					);
			Replies.QuerySpecificFile rep = (Replies.QuerySpecificFile)cm.getReply();
			if(rep == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Execute Failed");
				this.getReply().setOK(false);
				return false;
			}
			
			if(file == null) {
				this.getReply().setFailedReason("QuerySpecificFile Failed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			Replies.QuerySpecificFile qd = this.getReply();
			qd.setFile(file);
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
