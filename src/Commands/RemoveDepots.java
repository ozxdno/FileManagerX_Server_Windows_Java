package Commands;

public class RemoveDepots extends Comman implements Interfaces.ICommands {

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
	
	public Replies.RemoveDepots getReply() {
		return (Replies.RemoveDepots)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RemoveDepots() {
		initThis();
	}
	public RemoveDepots(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		super.setReply(new Replies.RemoveDepots());
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
		RemoveDepots qf = (RemoveDepots)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		RemoveDepots qf = (RemoveDepots)o;
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
				this.getReply().setDepotInfo(this.getReply().getDepotInfos().getContent().get(i));
				st.inputNextItem(this.getReply().output());
			}
			
			this.replyTotal(st);
			return true;
		}
		else {
			this.getReply().setFailedReason("Unsupport Command at This Machine");
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

	public boolean remove() {
		
		BasicCollections.DepotInfos res = Globals.Datas.DBManager.QueryDepotInfos(conditions);
		if(res == null) {
			this.getReply().setFailedReason("Remove from DataBase Failed");
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().getDepotInfos().clear();
		for(BasicModels.DepotInfo i : res.getContent()) {
			boolean ok = Globals.Datas.DBManager.removeDepotInfo(i);
			if(!ok) {
				this.getReply().getDepotInfos().add(i);
			}
		}
		this.getReply().setAmount(this.getReply().getDepotInfos().size());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
