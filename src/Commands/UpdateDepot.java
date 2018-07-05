package Commands;

public class UpdateDepot extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DepotInfo depotInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(depotInfo == null) {
			return false;
		}
		this.depotInfo = depotInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DepotInfo getDepotInfo() {
		return this.depotInfo;
	}
	
	public Replies.UpdateDepot getReply() {
		return (Replies.UpdateDepot)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateDepot() {
		initThis();
	}
	public UpdateDepot(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.depotInfo = new BasicModels.DepotInfo();
		super.setReply(new Replies.UpdateDepot());
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
		c.addToBottom(new BasicModels.Config(this.depotInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		return this.depotInfo.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateDepot um = (UpdateDepot)o;
		this.depotInfo = um.depotInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateDepot um = (UpdateDepot)o;
		this.depotInfo.copyValue(um.depotInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!super.isConnected()) {
			this.reply();
			return false;
		}
		
		return Globals.Configurations.StartType.equals(BasicEnums.StartType.Server) ?
				this.executeInServer() :
				this.executeInDepot();
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
		
		if(!Globals.Datas.DBManager.updataDepotInfo(depotInfo)) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Update to Depot Failed");
			this.reply();
			return false;
		}
		
		this.getReply().setDepotInfo(depotInfo);
		this.reply();
		return true;
	}
	private boolean executeInDepot() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
