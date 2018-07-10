package Commands;

public class UpdateFolders extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	private BasicModels.Folder model;
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
	public boolean setModel(BasicModels.Folder model) {
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
	public BasicModels.Folder getModel() {
		return this.model;
	}
	public String getItems() {
		return this.items;
	}
	
	public Replies.UpdateFolders getReply() {
		return (Replies.UpdateFolders)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateFolders() {
		initThis();
	}
	public UpdateFolders(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		this.model = new BasicModels.Folder();
		this.items = "";
		this.setReply(new Replies.UpdateFolders());
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
		UpdateFolders qf = (UpdateFolders)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
		this.model = qf.model;
		this.items = qf.items;
	}
	public void copyValue(Object o) {
		UpdateFolders qf = (UpdateFolders)o;
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
		if(!this.isDestDepotIndexExist()) {
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
			if(this.getReply().getFolders().size() == 0) {
				this.reply();
				return true;
			}
			
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<this.getReply().getFolders().size(); i++) {
				this.getReply().setFolder(this.getReply().getFolders().getContent().get(i));
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
			
			cm.updateFolders(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					model,
					items,
					conditions);
			Replies.UpdateFolders rep = (Replies.UpdateFolders)cm.getReply();
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
			
			if(this.getReply().getFolders().size() == 0) {
				this.reply();
				return true;
			}
			
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			for(int i=0; i<this.getReply().getFolders().size(); i++) {
				this.getReply().setFolder(this.getReply().getFolders().getContent().get(i));
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

	public boolean update() {
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(
				this.getBasicMessagePackage().getDestDepotIndex());
		if(dbm == null) {
			this.getReply().setFailedReason("Not Found DBManager");
			this.getReply().setOK(false);
			return false;
		}
		
		BasicCollections.Folders res = dbm.QueryFolders(conditions);
		if(res == null) {
			this.getReply().setFailedReason("Query BaseFiles in DataBase Failed");
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().getFolders().clear();
		if(!this.isNoUpdate()) {
			for(BasicModels.Folder i : res.getContent()) {
				this.updateValueByItems(i);
				boolean ok = dbm.updataFolder(i);
				if(!ok) {
					this.getReply().getFolders().add(i);
				}
			}
		}
		
		this.getReply().setAmount(this.getReply().getFolders().size());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void updateValueByItems(BasicModels.Folder item) {
		String[] items = Tools.String.split(this.items, ' ');
		
		if(this.isUpdateItem("Index", items)) {
			item.setIndex(model.getIndex());
		}
		if(this.isUpdateItem("Father", items)) {
			item.setFather(model.getFather());
		}
		if(this.isUpdateItem("Machine", items)) {
			item.setMachine(model.getMachine());
		}
		if(this.isUpdateItem("Depot", items)) {
			item.setDepot(model.getDepot());
		}
		if(this.isUpdateItem("DataBase", items)) {
			item.setDataBase(model.getDataBase());
		}
		if(this.isUpdateItem("Url", items)) {
			item.setUrl(model.getUrl());
		}
		if(this.isUpdateItem("Type", items)) {
			item.setType(model.getType());
		}
		if(this.isUpdateItem("State", items)) {
			item.setState(model.getState());
		}
		if(this.isUpdateItem("Modify", items)) {
			item.setModify(model.getModify());
		}
		if(this.isUpdateItem("Length", items)) {
			item.setLength(model.getLength());
		}
		if(this.isUpdateItem("Score", items)) {
			item.setScore(model.getScore());
		}
		if(this.isUpdateItem("Tags", items)) {
			item.setTags(model.getTags());
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
			   !this.isUpdateItem("Father", items) &&
			   !this.isUpdateItem("Machine", items) &&
			   !this.isUpdateItem("Depot", items) &&
			   !this.isUpdateItem("DataBase", items) &&
			   !this.isUpdateItem("Url", items) &&
			   !this.isUpdateItem("Type", items) &&
			   !this.isUpdateItem("State", items) &&
			   !this.isUpdateItem("Modify", items) &&
			   !this.isUpdateItem("Length", items) &&
			   !this.isUpdateItem("Score", items) &&
			   !this.isUpdateItem("Tags", items);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
