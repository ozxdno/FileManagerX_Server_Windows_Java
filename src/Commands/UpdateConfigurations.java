package Commands;

public class UpdateConfigurations extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long Next_FileIndex = 0;
	private long Next_MachineIndex = 0;
	private long Next_UserIndex = 0;
	private long Next_DepotIndex = 0;
	private long Next_DataBaseIndex = 0;
	
	private long This_MachineIndex = 0;
	private long This_UserIndex = 0;
	
	private long Server_MachineIndex = 0;
	private long Server_UserIndex = 0;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setNext_FileIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_FileIndex = nextIndex;
		return true;
	}
	public boolean setNext_MachineIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_MachineIndex = nextIndex;
		return true;
	}
	public boolean setNext_UserIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_UserIndex = nextIndex;
		return true;
	}
	public boolean setNext_DepotIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_DepotIndex = nextIndex;
		return true;
	}
	public boolean setNext_DataBaseIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_DataBaseIndex = nextIndex;
		return true;
	}
	
	public boolean setThis_MachineIndex(long thisIndex) {
		if(thisIndex < 0) {
			return false;
		}
		this.This_MachineIndex = thisIndex;
		return true;
	}
	public boolean setThis_UserIndex(long thisIndex) {
		if(thisIndex < 0) {
			return false;
		}
		this.This_UserIndex = thisIndex;
		return true;
	}
	
	public boolean setServer_MachineIndex(long serverIndex) {
		if(serverIndex < 0) {
			return false;
		}
		this.Server_MachineIndex = serverIndex;
		return true;
	}
	public boolean setServer_UserIndex(long serverIndex) {
		if(serverIndex < 0) {
			return false;
		}
		this.Server_UserIndex = serverIndex;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getNext_FileIndex() {
		return this.Next_FileIndex;
	}
	public long getNext_MachineIndex() {
		return this.Next_MachineIndex;
	}
	public long getNext_UserIndex() {
		return this.Next_UserIndex;
	}
	public long getNext_DepotIndex() {
		return this.Next_DepotIndex;
	}
	public long getNext_DataBaseIndex() {
		return this.Next_DataBaseIndex;
	}
	
	public long getThis_MachineIndex() {
		return this.This_MachineIndex;
	}
	public long getThis_UserIndex() {
		return this.This_UserIndex;
	}
	
	public long getServer_MachineIndex() {
		return this.Server_MachineIndex;
	}
	public long getServer_UserIndex() {
		return this.Server_UserIndex;
	}
	
	public Replies.UpdateConfigurations getReply() {
		return (Replies.UpdateConfigurations)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public UpdateConfigurations() {
		initThis();
	}
	public UpdateConfigurations(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		Next_FileIndex = 0;
		Next_MachineIndex = 0;
		Next_UserIndex = 0;
		Next_DepotIndex = 0;
		Next_DataBaseIndex = 0;
		
		This_MachineIndex = 0;
		This_UserIndex = 0;
		
		Server_MachineIndex = 0;
		Server_UserIndex = 0;
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
		c.addToBottom(this.Next_FileIndex);
		c.addToBottom(this.Next_MachineIndex);
		c.addToBottom(this.Next_UserIndex);
		c.addToBottom(this.Next_DepotIndex);
		c.addToBottom(this.Next_DataBaseIndex);
		c.addToBottom(this.This_MachineIndex);
		c.addToBottom(this.This_UserIndex);
		c.addToBottom(this.Server_MachineIndex);
		c.addToBottom(this.Server_UserIndex);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.Next_FileIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.Next_MachineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.Next_UserIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.Next_DepotIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.Next_DataBaseIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.This_MachineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.This_UserIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.Server_MachineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.Server_UserIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateConfigurations qc = (UpdateConfigurations)o;
		this.Next_FileIndex = qc.Next_FileIndex;
		this.Next_DataBaseIndex = qc.Next_DataBaseIndex;
		this.Next_DepotIndex = qc.Next_DepotIndex;
		this.Next_MachineIndex = qc.Next_MachineIndex;
		this.Next_UserIndex = qc.Next_UserIndex;
		this.Server_MachineIndex = qc.Server_MachineIndex;
		this.Server_UserIndex = qc.Server_UserIndex;
		this.This_MachineIndex = qc.This_MachineIndex;
		this.This_UserIndex = qc.This_UserIndex;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateConfigurations qc = (UpdateConfigurations)o;
		this.Next_FileIndex = qc.Next_FileIndex;
		this.Next_DataBaseIndex = qc.Next_DataBaseIndex;
		this.Next_DepotIndex = qc.Next_DepotIndex;
		this.Next_MachineIndex = qc.Next_MachineIndex;
		this.Next_UserIndex = qc.Next_UserIndex;
		this.Server_MachineIndex = qc.Server_MachineIndex;
		this.Server_UserIndex = qc.Server_UserIndex;
		this.This_MachineIndex = qc.This_MachineIndex;
		this.This_UserIndex = qc.This_UserIndex;
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
		this.setUserIndexAndPassword();
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
		
		if(this.Next_FileIndex > 0) {
			Globals.Configurations.Next_FileIndex = this.Next_FileIndex;
		}
		if(this.Next_MachineIndex > 0) {
			Globals.Configurations.Next_MachineIndex = this.Next_MachineIndex;
		}
		if(this.Next_DepotIndex > 0) {
			Globals.Configurations.Next_DepotIndex = this.Next_DepotIndex;
		}
		if(this.Next_DataBaseIndex > 0) {
			Globals.Configurations.Next_DataBaseIndex = this.Next_DataBaseIndex;
		}
		if(this.Next_UserIndex > 0) {
			Globals.Configurations.Next_UserIndex = this.Next_UserIndex;
		}
		
		this.reply();
		return true;
	}
	private boolean executeInDataBase() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
