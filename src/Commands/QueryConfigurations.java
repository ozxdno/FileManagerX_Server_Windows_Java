package Commands;

public class QueryConfigurations extends Comman implements Interfaces.ICommands {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Replies.QueryConfigurations getReply() {
		return (Replies.QueryConfigurations)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryConfigurations() {
		initThis();
	}
	public QueryConfigurations(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		super.setReply(new Replies.QueryConfigurations());
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
		return super.output(this.getClass().getSimpleName());
	}
	public String input(String in) {
		return super.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
	}
	public void copyValue(Object o) {
		super.copyValue(o);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected()) {
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
		
		Replies.QueryConfigurations qc = this.getReply();
		qc.setNext_FileIndex(Globals.Configurations.Next_FileIndex);
		qc.setNext_MachineIndex(Globals.Configurations.Next_MachineIndex);
		qc.setNext_UserIndex(Globals.Configurations.Next_UserIndex);
		qc.setNext_DepotIndex(Globals.Configurations.Next_DepotIndex);
		qc.setNext_DataBaseIndex(Globals.Configurations.Next_DataBaseIndex);
		qc.setThis_MachineIndex(Globals.Configurations.This_MachineIndex);
		qc.setThis_UserIndex(Globals.Configurations.This_UserIndex);
		qc.setServer_MachineIndex(Globals.Configurations.Server_MachineIndex);
		qc.setServer_UserIndex(Globals.Configurations.Server_UserIndex);
		
		this.reply();
		return qc.isOK();
	}
	private boolean executeInDepot() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
