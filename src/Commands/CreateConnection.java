package Commands;

public class CreateConnection extends Comman implements Interfaces.ICommands {

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
	
	public Replies.CreateConnection getReply() {
		return (Replies.CreateConnection)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CreateConnection() {
		initThis();
	}
	public CreateConnection(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.machineInfo = new BasicModels.MachineInfo();
		super.setReply(new Replies.CreateConnection());
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
		CreateConnection um = (CreateConnection)o;
		this.machineInfo = um.machineInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		CreateConnection um = (CreateConnection)o;
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
		
		if(this.isArriveDestMachine()) {
			Interfaces.IClientConnection con = Factories.CommunicatorFactory.createRunningClientConnection(
					this.machineInfo,
					Globals.Datas.ThisMachine);
			if(con == null) {
				this.getReply().setFailedReason("Create Client Connection Failed");
				this.getReply().setOK(false);
				this.reply();
				return true;
			}
			
			con.setType(BasicEnums.ConnectionType.TRANSPORT_FILE);
			con.setUser(Globals.Datas.ThisUser);
			boolean ok = con.getCommandsManager().loginConnection();
			if(!ok) {
				this.getReply().setFailedReason("Login Connection Failed");
				this.getReply().setOK(false);
				this.reply();
				return true;
			}
			
			con.getCommandsManager().exchange();
			Replies.Exchange rep = (Replies.Exchange)con.getCommandsManager().getReply();
			if(rep == null) {
				this.getReply().setFailedReason("Exchange Failed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			if(!rep.isOK()) {
				this.getReply().setFailedReason("Exchange Failed");
				this.getReply().setOK(false);
				this.reply();
				return false;
			}
			
			this.getReply().setCreateConnection(con.getIndex());
			this.reply();
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
}
