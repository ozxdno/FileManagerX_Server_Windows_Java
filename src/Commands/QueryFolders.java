package Commands;

public class QueryFolders extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DataBaseManager.QueryConditions getQueryConditions() {
		return this.conditions;
	}
	
	public Replies.QueryFolders getReply() {
		return (Replies.QueryFolders)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryFolders() {
		initThis();
	}
	public QueryFolders(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		super.setReply(new Replies.QueryFolders());
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
		return super.output() + "|" + this.conditions.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.conditions.input(in);
	}
	public void copyReference(Object o) {
		QueryFolders qf = (QueryFolders)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		QueryFolders qf = (QueryFolders)o;
		super.copyValue(o);
		this.conditions.copyValue(qf.conditions);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight_PriorityEnough(BasicEnums.UserPriority.Member)) {
			this.reply();
			return false;
		}
		if(!this.isExistDest_MachineIndex_DepotIndex()) {
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
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(
					this.getBasicMessagePackage().getDestDepotIndex());
			if(dbm == null) {
				this.getReply().setFailedReason("Not Found DBManager");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			BasicCollections.Folders folders = dbm.QueryFolders(conditions);
			if(folders == null) {
				this.getReply().setFailedReason("QueryFolders Failed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			if(folders.size() == 0) {
				this.getReply().setFolder(new BasicModels.Folder());
				this.getReply().setFolders(folders);
				this.getReply().setAmount(0);
				this.reply();
				return true;
			}
			
			this.getReply().setFolders(folders);
			this.getReply().setAmount(folders.size());
			this.getReply().setOK(true);
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<folders.size(); i++) {
				this.getReply().setFolder(folders.getContent().get(i));
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
			
			BasicCollections.Folders folders = cm.queryFolders(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					this.conditions
					);
			Replies.QueryFolders rep = (Replies.QueryFolders)cm.getReply();
			if(rep == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Execute Failed");
				this.getReply().setOK(false);
				return false;
			}
			
			if(folders == null) {
				this.getReply().setFailedReason("QueryFolders Failed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			if(folders.size() == 0) {
				this.getReply().setFolder(new BasicModels.Folder());
				this.getReply().setFolders(folders);
				this.getReply().setAmount(0);
				this.reply();
				return true;
			}
			this.getReply().setFolders(folders);
			this.getReply().setAmount(folders.size());
			this.getReply().setOK(true);
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<folders.size(); i++) {
				this.getReply().setFolder(folders.getContent().get(i));
				st.inputNextItem(this.getReply().output());
			}
			this.replyTotal(st);
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void reply() {
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
		this.getConnection().notify();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
