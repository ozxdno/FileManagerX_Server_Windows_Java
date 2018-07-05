package Commands;

public class UpdateDataBase extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo dbInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		if(dbInfo == null) {
			return false;
		}
		this.dbInfo = dbInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DataBaseInfo getDataBaseInfo() {
		return this.dbInfo;
	}
	
	public Replies.UpdateDataBase getReply() {
		return (Replies.UpdateDataBase)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateDataBase() {
		initThis();
	}
	public UpdateDataBase(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.dbInfo = new BasicModels.DataBaseInfo();
		super.setReply(new Replies.UpdateDataBase());
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
		c.addToBottom(new BasicModels.Config(this.dbInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		return this.dbInfo.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateDataBase um = (UpdateDataBase)o;
		this.dbInfo = um.dbInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateDataBase um = (UpdateDataBase)o;
		this.dbInfo.copyValue(um.dbInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!super.isConnected()) {
			this.reply();
			return false;
		}
		
		return Globals.Configurations.StartType.equals(BasicEnums.StartType.Server) ?
				this.executeInServer() :
				this.executeInDataBase();
	}
	public void reply() {
		this.setBasicMessagePackageToReply();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean executeInServer() {
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
		
		if(!Globals.Datas.DBManager.updataDataBaseInfo(dbInfo)) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Update to DataBase Failed");
			this.reply();
			return false;
		}
		
		this.getReply().setDataBaseInfo(dbInfo);
		this.reply();
		return true;
	}
	private boolean executeInDataBase() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
