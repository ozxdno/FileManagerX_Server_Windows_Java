package Commands;

public class UpdateFolder extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.Folder folder;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setFolder(BasicModels.Folder folder) {
		if(folder == null) {
			return false;
		}
		this.folder = folder;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.Folder getFolder() {
		return this.folder;
	}
	
	public Replies.UpdateFolder getReply() {
		return (Replies.UpdateFolder)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateFolder() {
		initThis();
	}
	public UpdateFolder(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.folder = new BasicModels.Folder();
		super.setReply(new Replies.UpdateFolder());
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
		c.addToBottom(new BasicModels.Config(this.folder.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		return this.folder.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateFolder um = (UpdateFolder)o;
		this.folder = um.folder;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateFolder um = (UpdateFolder)o;
		this.folder.copyValue(um.folder);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!super.isLogin()) {
			this.reply();
			return false;
		}
		if(!super.isUserIndexRight()) {
			this.reply();
			return false;
		}
		if(!super.isPasswordRight()) {
			this.reply();
			return false;
		}
		if(!super.isPriorityEnough(BasicEnums.UserPriority.Member)) {
			this.reply();
			return false;
		}
		if(!this.isExistDest_MachineIndex_DepotIndex()) {
			this.reply();
			return true;
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
			
			boolean ok = dbm.updataFolder(folder);
			if(!ok) {
				this.getReply().setOK(false);
				this.getReply().setFailedReason("Update Folder Failed");
				this.reply();
				return false;
			}
			
			this.getReply().setFolder(folder);
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
			
			boolean ok = cm.updateFolder(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					folder
					);
			Replies.UpdateFolder rep = (Replies.UpdateFolder)cm.getReply();
			if(rep == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Execute Failed");
				this.getReply().setOK(false);
				return false;
			}
			if(!ok) {
				this.getReply().setFailedReason("UpdateFile Failed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			this.getReply().setFolder(folder);
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
