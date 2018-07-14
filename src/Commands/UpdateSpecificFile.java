package Commands;

public class UpdateSpecificFile extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.BaseFile file;
	private BasicEnums.FileType type;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setFile(BasicModels.BaseFile file) {
		if(file == null) {
			return false;
		}
		this.file = file;
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

	public BasicModels.BaseFile getFile() {
		return this.file;
	}
	public BasicEnums.FileType getType() {
		return this.type;
	}
	
	public Replies.UpdateSpecificFile getReply() {
		return (Replies.UpdateSpecificFile)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateSpecificFile() {
		initThis();
	}
	public UpdateSpecificFile(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.file = new BasicModels.BaseFile();
		this.type = BasicEnums.FileType.Unsupport;
		super.setReply(new Replies.UpdateSpecificFile());
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
		c.addToBottom(new BasicModels.Config(this.file.output()));
		c.addToBottom(this.type.toString());
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		in = this.file.input(in);
		if(in == null) {
			return null;
		}
		
		try {
			BasicModels.Config c = new BasicModels.Config(in);
			this.type = BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			
			return c.output();
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateSpecificFile um = (UpdateSpecificFile)o;
		this.file = um.file;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateSpecificFile um = (UpdateSpecificFile)o;
		this.file.copyValue(um.file);
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
			
			boolean ok = dbm.updataSpecificFile(type, file);
			if(!ok) {
				this.getReply().setOK(false);
				this.getReply().setFailedReason("Update File Failed");
				this.reply();
				return false;
			}
			
			this.getReply().setFile(file);
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
			
			boolean ok = cm.updateSpecificFile(
					this.getBasicMessagePackage().getDestMachineIndex(),
					this.getBasicMessagePackage().getDestDepotIndex(),
					type,
					file
					);
			Replies.UpdateSpecificFile rep = (Replies.UpdateSpecificFile)cm.getReply();
			if(rep == null) {
				this.getReply().setFailedReason("The Reply from CommandsManager is NULL, Execute Failed");
				this.getReply().setOK(false);
				return false;
			}
			if(!ok) {
				this.getReply().setFailedReason("UpdateSpecificFile Failed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			this.getReply().setFile(file);
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
