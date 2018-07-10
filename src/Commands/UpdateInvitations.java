package Commands;

public class UpdateInvitations extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	private BasicModels.Invitation model;
	private String items;
	
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
	public boolean setModel(BasicModels.Invitation model) {
		if(model == null) {
			return false;
		}
		this.model = model;
		return true;
	}
	/**
	 * 
	 * @param items ”√ø’∏Ò∑÷∏Ó
	 * @return
	 */
	public boolean setItems(String items) {
		if(items == null) {
			return false;
		}
		if(items.length() == 0) {
			return false;
		}
		this.items = items;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DataBaseManager.QueryConditions getQueryConditions() {
		return this.conditions;
	}
	public BasicModels.Invitation getModel() {
		return this.model;
	}
	public String getItems() {
		return this.items;
	}
	
	public Replies.UpdateInvitations getReply() {
		return (Replies.UpdateInvitations)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateInvitations() {
		initThis();
	}
	public UpdateInvitations(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		this.model = new BasicModels.Invitation();
		this.items = "";
		this.setReply(new Replies.UpdateInvitations());
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
		c.addToBottom(new BasicModels.Config(model.output()));
		c.addToBottom(items);
		c.addToBottom(new BasicModels.Config(this.conditions.output()).getValue());
		
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		in = this.model.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.items = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return this.conditions.input(c.output());
	}
	public void copyReference(Object o) {
		UpdateInvitations qf = (UpdateInvitations)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
		this.model = qf.model;
		this.items = qf.items;
	}
	public void copyValue(Object o) {
		UpdateInvitations qf = (UpdateInvitations)o;
		super.copyValue(o);
		this.conditions.copyValue(qf.conditions);
		this.model.copyValue(qf.model);
		this.items = new String(qf.items);
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
		if(!this.isDestMachineIndexExist()) {
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
			if(!this.update()) {
				this.reply();
				return false;
			}
			if(this.getReply().getInvitations().size() == 0) {
				this.reply();
				return true;
			}
			
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<this.getReply().getInvitations().size(); i++) {
				this.getReply().setInvitation(this.getReply().getInvitations().getContent().get(i));
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

	public boolean update() {
		BasicCollections.Invitations res = Globals.Datas.DBManager.QueryInvitations(conditions);
		if(res == null) {
			this.getReply().setFailedReason("Query Invitations in DataBase Failed");
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().getInvitations().clear();
		if(!this.isNoUpdate()) {
			for(BasicModels.Invitation i : res.getContent()) {
				this.updateValueByItems(i);
				boolean ok = Globals.Datas.DBManager.updataInvitation(i);
				if(!ok) {
					this.getReply().getInvitations().add(i);
				}
			}
		}
		
		this.getReply().setAmount(this.getReply().getInvitations().size());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void updateValueByItems(BasicModels.Invitation item) {
		String[] items = Tools.String.split(this.items, ' ');
		
		if(this.isUpdateItem("Code", items)) {
			item.setCode(model.getCode());
		}
		if(this.isUpdateItem("Priority", items)) {
			item.getUser().setPriority(model.getUser().getPriority());
		}
		if(this.isUpdateItem("Level", items)) {
			item.getUser().setLevel(model.getUser().getLevel());
		}
		if(this.isUpdateItem("Experience", items)) {
			item.getUser().setExperience(model.getUser().getExperience());
		}
		if(this.isUpdateItem("Coins", items)) {
			item.getUser().setCoins(model.getUser().getCoins());
		}
		if(this.isUpdateItem("Money", items)) {
			item.getUser().setMoney(model.getUser().getMoney());
		}
	}
	private boolean isUpdateItem(String itemName, String[] items) {
		for(int i=0; i<items.length; i++) {
			if(itemName == null || itemName.length() == 0) {
				continue;
			}
			if(itemName.equals(items[i])) {
				return true;
			}
		}
		return false;
	}
	private boolean isNoUpdate() {
		String[] items = Tools.String.split(this.items, ' ');
		
		return !this.isUpdateItem("Code", items) &&
			   !this.isUpdateItem("Priority", items) &&
			   !this.isUpdateItem("Level", items) &&
			   !this.isUpdateItem("Experience", items) &&
			   !this.isUpdateItem("Coins", items) &&
			   !this.isUpdateItem("Money", items);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
