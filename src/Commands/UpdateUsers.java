package Commands;

public class UpdateUsers extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	private BasicModels.User model;
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
	public boolean setModel(BasicModels.User model) {
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
	public BasicModels.User getModel() {
		return this.model;
	}
	public String getItems() {
		return this.items;
	}
	
	public Replies.UpdateUsers getReply() {
		return (Replies.UpdateUsers)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateUsers() {
		initThis();
	}
	public UpdateUsers(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		this.model = new BasicModels.User();
		this.items = "";
		this.setReply(new Replies.UpdateUsers());
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
		UpdateUsers qf = (UpdateUsers)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
		this.model = qf.model;
		this.items = qf.items;
	}
	public void copyValue(Object o) {
		UpdateUsers qf = (UpdateUsers)o;
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
			if(this.getReply().getUsers().size() == 0) {
				this.reply();
				return true;
			}
			
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<this.getReply().getUsers().size(); i++) {
				this.getReply().setUser(this.getReply().getUsers().getContent().get(i));
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
		BasicCollections.Users res = Globals.Datas.DBManager.QueryUsers(conditions);
		if(res == null) {
			this.getReply().setFailedReason("Query Users in DataBase Failed");
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().getUsers().clear();
		if(!this.isNoUpdate()) {
			for(BasicModels.User i : res.getContent()) {
				this.updateValueByItems(i);
				boolean ok = Globals.Datas.DBManager.updataUser(i);
				if(!ok) {
					this.getReply().getUsers().add(i);
				}
			}
		}
		
		this.getReply().setAmount(this.getReply().getUsers().size());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void updateValueByItems(BasicModels.User item) {
		String[] items = Tools.String.split(this.items, ' ');
		
		if(this.isUpdateItem("Index", items)) {
			item.setIndex(model.getIndex());
		}
		if(this.isUpdateItem("LoginName", items)) {
			item.setLoginName(model.getLoginName());
		}
		if(this.isUpdateItem("NickName", items)) {
			item.setNickName(model.getNickName());
		}
		if(this.isUpdateItem("Password", items)) {
			item.setPassword(model.getPassword());
		}
		if(this.isUpdateItem("Email", items)) {
			item.setEmail(model.getEmail());
		}
		if(this.isUpdateItem("Phone", items)) {
			item.setPhone(model.getPhone());
		}
		if(this.isUpdateItem("State", items)) {
			item.setState(model.getState());
		}
		if(this.isUpdateItem("Priority", items)) {
			item.setPriority(model.getPriority());
		}
		if(this.isUpdateItem("Level", items)) {
			item.setLevel(model.getLevel());
		}
		if(this.isUpdateItem("Experience", items)) {
			item.setExperience(model.getExperience());
		}
		if(this.isUpdateItem("PhotoUrl", items)) {
			item.setPhotoUrl(model.getPhotoUrl());
		}
		if(this.isUpdateItem("Coins", items)) {
			item.setCoins(model.getCoins());
		}
		if(this.isUpdateItem("Money", items)) {
			item.setMoney(model.getMoney());
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
		
		return !this.isUpdateItem("Index", items) &&
			   !this.isUpdateItem("LoginName", items) &&
			   !this.isUpdateItem("NickName", items) &&
			   !this.isUpdateItem("Password", items) &&
			   !this.isUpdateItem("Email", items) &&
			   !this.isUpdateItem("Phone", items) &&
			   !this.isUpdateItem("State", items) &&
			   !this.isUpdateItem("Priority", items) &&
			   !this.isUpdateItem("Level", items) &&
			   !this.isUpdateItem("Experience", items) &&
			   !this.isUpdateItem("PhotoUrl", items) &&
			   !this.isUpdateItem("Coins", items) &&
			   !this.isUpdateItem("Money", items);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
