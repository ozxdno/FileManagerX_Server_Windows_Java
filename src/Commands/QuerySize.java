package Commands;

public class QuerySize extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String queryItem;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setQueryItem(String queryItem) {
		if(queryItem == null) {
			return false;
		}
		this.queryItem = queryItem;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getQueryItem() {
		return this.queryItem;
	}
	
	public Replies.QuerySize getReply() {
		return (Replies.QuerySize)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QuerySize() {
		initThis();
	}
	public QuerySize(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.queryItem = "";
		super.setReply(new Replies.QuerySize());
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
		c.addToBottom(this.queryItem);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		this.queryItem = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QuerySize t = (QuerySize)o;
		this.queryItem = t.queryItem;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QuerySize t = (QuerySize)o;
		this.queryItem = new String(t.queryItem);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
			this.reply();
			return false;
		}
		if(!this.isDestMachineIndexExist()) {
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			if(this.queryItem == null) {
				this.getReply().setFailedReason("Wrong Query Item, queryItem = NULL");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			if(this.queryItem.length() == 0) {
				this.getReply().setFailedReason("Wrong Query Item, queryItem = Empty");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			if(this.queryItem.equals("User")) {
				BasicCollections.Users users = Globals.Datas.DBManager.QueryUsers("");
				if(users == null) {
					this.getReply().setFailedReason("Query User Failed");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				this.getReply().setSize(users.size());
				this.reply();
				return true;
			}
			else if(this.queryItem.equals("Invitation")) {
				BasicCollections.Invitations res = Globals.Datas.DBManager.QueryInvitations("");
				if(res == null) {
					this.getReply().setFailedReason("Query Invitation Failed");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				this.getReply().setSize(res.size());
				this.reply();
				return true;
			}
			else if(this.queryItem.equals("Machine")) {
				BasicCollections.MachineInfos res = Globals.Datas.DBManager.QueryMachineInfos("");
				if(res == null) {
					this.getReply().setFailedReason("Query Machine Failed");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				this.getReply().setSize(res.size());
				this.reply();
				return true;
			}
			else if(this.queryItem.equals("Depot")) {
				BasicCollections.DepotInfos res = Globals.Datas.DBManager.QueryDepotInfos("");
				if(res == null) {
					this.getReply().setFailedReason("Query Depot Failed");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				this.getReply().setSize(res.size());
				this.reply();
				return true;
			}
			else if(this.queryItem.equals("DataBase")) {
				BasicCollections.DataBaseInfos res = Globals.Datas.DBManager.QueryDataBaseInfos("");
				if(res == null) {
					this.getReply().setFailedReason("Query DataBase Failed");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				this.getReply().setSize(res.size());
				this.reply();
				return true;
			}
			else if(this.queryItem.equals("Folder")) {
				if(!this.isDestDepotIndexExist()) {
					this.reply();
					return false;
				}
				
				Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(
						this.getBasicMessagePackage().getDestDepotIndex());
				if(dbm == null) {
					this.getReply().setFailedReason("Not Found DBManager");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				
				BasicCollections.Folders res = dbm.QueryFolders("");
				if(res == null) {
					this.getReply().setFailedReason("Query Folder Failed");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				this.getReply().setSize(res.size());
				this.reply();
				return true;
			}
			else if(this.queryItem.equals("File")) {
				if(!this.isDestDepotIndexExist()) {
					this.reply();
					return false;
				}
				
				Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(
						this.getBasicMessagePackage().getDestDepotIndex());
				if(dbm == null) {
					this.getReply().setFailedReason("Not Found DBManager");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				
				BasicCollections.BaseFiles res = dbm.QueryFiles("");
				if(res == null) {
					this.getReply().setFailedReason("Query File Failed");
					this.getReply().setOK(false);
					this.reply();
					return false;
				}
				this.getReply().setSize(res.size());
				this.reply();
				return true;
			}
			
			this.getReply().setFailedReason("Unsupport Query Item");
			this.getReply().setOK(false);
			this.reply();
			return false;
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
			
			cm.querySize(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					this.queryItem);
			Replies.QuerySize rep = (Replies.QuerySize)cm.getReply();
			if(rep == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Execute Failed");
				this.getReply().setOK(false);
				return false;
			}
			
			this.setReply(rep);
			this.reply();
			return this.getReply().isOK();
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
