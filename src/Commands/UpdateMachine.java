package Commands;

public class UpdateMachine extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo machineInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setMachineInfo(BasicModels.MachineInfo machineInfo) {
		if(machineInfo == null) {
			return false;
		}
		this.machineInfo = machineInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	
	public Replies.UpdateMachine getReply() {
		return (Replies.UpdateMachine)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateMachine() {
		initThis();
	}
	public UpdateMachine(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.machineInfo = new BasicModels.MachineInfo();
		super.setReply(new Replies.UpdateMachine());
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
		c.addToBottom(this.getUserIndex());
		c.addToBottom(this.getPassword());
		c.addToBottom(this.getMachineIndex());
		c.addToBottom(this.getDepotIndex());
		c.addToBottom(this.getDataBaseIndex());
		c.addToBottom(new BasicModels.Config(this.machineInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		return this.machineInfo.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateMachine um = (UpdateMachine)o;
		this.machineInfo = um.machineInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateMachine um = (UpdateMachine)o;
		this.machineInfo.copyValue(um.machineInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!super.isConnected()) {
			this.reply();
			return false;
		}
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
		
		if(!Tools.Url.isIp(this.machineInfo.getIp())) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Illegal IP Address");
			this.reply();
			return false;
		}
		
		if(!Globals.Datas.DBManager.updataMachineInfo(machineInfo)) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Update to DataBase Failed");
			this.reply();
			return false;
		}
		
		this.getReply().setMachineInfo(machineInfo);
		this.reply();
		return true;
	}
	public void reply() {
		this.setUserIndexAndPassword();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
