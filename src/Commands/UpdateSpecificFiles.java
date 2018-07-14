package Commands;

public class UpdateSpecificFiles extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	private BasicModels.BaseFile model;
	private String items;
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
	public boolean setModel(BasicModels.BaseFile model) {
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
	public BasicModels.BaseFile getModel() {
		return this.model;
	}
	public String getItems() {
		return this.items;
	}
	public BasicEnums.FileType getType() {
		return this.type;
	}
	
	public Replies.UpdateSpecificFiles getReply() {
		return (Replies.UpdateSpecificFiles)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateSpecificFiles() {
		initThis();
	}
	public UpdateSpecificFiles(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		this.model = new BasicModels.BaseFile();
		this.items = "";
		this.type = BasicEnums.FileType.Unsupport;
		this.setReply(new Replies.UpdateSpecificFiles());
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
		c.addToBottom(this.type.toString());
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
	
		try {
			BasicModels.Config c = new BasicModels.Config(in);
			this.items = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.type = BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			
			return this.conditions.input(c.output());
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		UpdateSpecificFiles qf = (UpdateSpecificFiles)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
		this.model = qf.model;
		this.items = qf.items;
	}
	public void copyValue(Object o) {
		UpdateSpecificFiles qf = (UpdateSpecificFiles)o;
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
			
			cm.updateSpecificFiles(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					type,
					model,
					items,
					conditions);
			Replies.UpdateSpecificFiles rep = (Replies.UpdateSpecificFiles)cm.getReply();
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

	public boolean update() {
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(
				this.getBasicMessagePackage().getDestDepotIndex());
		if(dbm == null) {
			this.getReply().setFailedReason("Not Found DBManager");
			this.getReply().setOK(false);
			return false;
		}
		
		BasicCollections.BaseFiles res = dbm.QuerySpecificFiles(type, conditions);
		if(res == null) {
			this.getReply().setFailedReason("Query BaseFiles in DataBase Failed");
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().getFiles().clear();
		if(!this.isNoUpdate()) {
			for(BasicModels.BaseFile i : res.getContent()) {
				this.updateValueByItems(i);
				boolean ok = dbm.updataSpecificFile(type, i);
				if(!ok) {
					this.getReply().getFiles().add(i);
				}
			}
		}
		
		this.getReply().setAmount(this.getReply().getFiles().size());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void updateValueByItems(BasicModels.BaseFile item) {
		if(type.equals(BasicEnums.FileType.Picture)) {
			this.updateValueByItems_Picture((FileModels.Picture)item);
			return;
		}
		if(type.equals(BasicEnums.FileType.Gif)) {
			this.updateValueByItems_Gif((FileModels.Gif)item);
			return;
		}
		if(type.equals(BasicEnums.FileType.Music)) {
			this.updateValueByItems_Music((FileModels.Music)item);
			return;
		}
		if(type.equals(BasicEnums.FileType.Video)) {
			this.updateValueByItems_Video((FileModels.Video)item);
			return;
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
		if(type.equals(BasicEnums.FileType.Picture)) {
			return this.isNoUpdate_Picture();
		}
		if(type.equals(BasicEnums.FileType.Gif)) {
			return this.isNoUpdate_Gif();
		}
		if(type.equals(BasicEnums.FileType.Music)) {
			return this.isNoUpdate_Music();
		}
		if(type.equals(BasicEnums.FileType.Video)) {
			return this.isNoUpdate_Video();
		}
		
		return true;
	}
	
	private void updateValueByItems_Picture(FileModels.Picture item) {
		String[] items = Tools.String.split(this.items, ' ');
		FileModels.Picture model = (FileModels.Picture)this.model;
		
		if(this.isUpdateItem("Index", items)) {
			item.setIndex(model.getIndex());
		}
		if(this.isUpdateItem("Height", items)) {
			item.setHeight(model.getHeight());
		}
		if(this.isUpdateItem("Width", items)) {
			item.setWidth(model.getWidth());
		}
		if(this.isUpdateItem("RowPixels", items)) {
			item.setRowPixels(model.getRowPixels());
		}
		if(this.isUpdateItem("ColPixels", items)) {
			item.setColPixels(model.getColPixels());
		}
	}
	private boolean isNoUpdate_Picture() {
		String[] items = Tools.String.split(this.items, ' ');
		
		return !this.isUpdateItem("Index", items) &&
			   !this.isUpdateItem("Height", items) &&
			   !this.isUpdateItem("Width", items) &&
			   !this.isUpdateItem("RowPixels", items) &&
			   !this.isUpdateItem("ColPixels", items);
	}
	
	private void updateValueByItems_Gif(FileModels.Gif item) {
		String[] items = Tools.String.split(this.items, ' ');
		FileModels.Gif model = (FileModels.Gif)this.model;
		
		if(this.isUpdateItem("Index", items)) {
			item.setIndex(model.getIndex());
		}
		if(this.isUpdateItem("Height", items)) {
			item.setHeight(model.getHeight());
		}
		if(this.isUpdateItem("Width", items)) {
			item.setWidth(model.getWidth());
		}
		if(this.isUpdateItem("Lasting", items)) {
			item.setLasting(model.getLasting());
		}
		if(this.isUpdateItem("RowPixels", items)) {
			item.setRowPixels(model.getRowPixels());
		}
		if(this.isUpdateItem("ColPixels", items)) {
			item.setColPixels(model.getColPixels());
		}
	}
	private boolean isNoUpdate_Gif() {
		String[] items = Tools.String.split(this.items, ' ');
		
		return !this.isUpdateItem("Index", items) &&
			   !this.isUpdateItem("Height", items) &&
			   !this.isUpdateItem("Width", items) &&
			   !this.isUpdateItem("Lasting", items) &&
			   !this.isUpdateItem("RowPixels", items) &&
			   !this.isUpdateItem("ColPixels", items);
	}
	
	private void updateValueByItems_Music(FileModels.Music item) {
		String[] items = Tools.String.split(this.items, ' ');
		FileModels.Music model = (FileModels.Music)this.model;
		
		if(this.isUpdateItem("Index", items)) {
			item.setIndex(model.getIndex());
		}
		if(this.isUpdateItem("Author", items)) {
			item.setAuthor(model.getAuthor());
		}
		if(this.isUpdateItem("Singer", items)) {
			item.setSinger(model.getSinger());
		}
		if(this.isUpdateItem("Lasting", items)) {
			item.setLasting(model.getLasting());
		}
		if(this.isUpdateItem("Album", items)) {
			item.setAlbum(model.getAlbum());
		}
	}
	private boolean isNoUpdate_Music() {
		String[] items = Tools.String.split(this.items, ' ');
		
		return !this.isUpdateItem("Index", items) &&
			   !this.isUpdateItem("Author", items) &&
			   !this.isUpdateItem("Singer", items) &&
			   !this.isUpdateItem("Lasting", items) &&
			   !this.isUpdateItem("Album", items);
	}
	
	private void updateValueByItems_Video(FileModels.Video item) {
		String[] items = Tools.String.split(this.items, ' ');
		FileModels.Video model = (FileModels.Video)this.model;
		
		if(this.isUpdateItem("Index", items)) {
			item.setIndex(model.getIndex());
		}
		if(this.isUpdateItem("Height", items)) {
			item.setHeight(model.getHeight());
		}
		if(this.isUpdateItem("Width", items)) {
			item.setWidth(model.getWidth());
		}
		if(this.isUpdateItem("Lasting", items)) {
			item.setLasting(model.getLasting());
		}
		if(this.isUpdateItem("RowPixels", items)) {
			item.setRowPixels(model.getRowPixels());
		}
		if(this.isUpdateItem("ColPixels", items)) {
			item.setColPixels(model.getColPixels());
		}
	}
	private boolean isNoUpdate_Video() {
		String[] items = Tools.String.split(this.items, ' ');
		
		return !this.isUpdateItem("Index", items) &&
			   !this.isUpdateItem("Height", items) &&
			   !this.isUpdateItem("Width", items) &&
			   !this.isUpdateItem("Lasting", items) &&
			   !this.isUpdateItem("RowPixels", items) &&
			   !this.isUpdateItem("ColPixels", items);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
