package Commands;

public class QueryDataBases extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setQueryConditions(DataBaseManager.QueryConditions conditions) {
		this.conditions = conditions;
		return true;
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
	
	public Replies.QueryDataBases getReply() {
		return (Replies.QueryDataBases)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryDataBases() {
		initThis();
	}
	public QueryDataBases(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		super.setReply(new Replies.QueryDataBases());
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
		c.addToBottom(this.getUserIndex());
		c.addToBottom(this.getPassword());
		c.addToBottom(this.getMachineIndex());
		c.addToBottom(this.getDepotIndex());
		c.addToBottom(this.getDataBaseIndex());
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
		QueryDataBases qf = (QueryDataBases)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		QueryDataBases qf = (QueryDataBases)o;
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
		
		Replies.QueryDataBases qd = this.getReply();
		qd.setDBInfos(Globals.Datas.DBManager.QueryDataBaseInfos(conditions));
		qd.setAmount(qd.getDBInfos().size());
		qd.setOK(qd.getDBInfos() != null);
		if(!qd.isOK()) {
			super.getReply().setOK(false);
			super.getReply().setFailedReason("Query Failed");
			this.reply();
			return false;
		}
		
		if(qd.getAmount() == 0) {
			this.reply();
			return qd.isOK();
		}
		
		Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
		for(int i=0; i<qd.getDBInfos().size(); i++) {
			qd.setDBtInfo(qd.getDBInfos().getContent().get(i));
			st.inputNextItem(qd.output());
		}
		
		this.replyTotal(st);
		return qd.isOK();
	}
	public void reply() {
		this.setUserIndexAndPassword();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
