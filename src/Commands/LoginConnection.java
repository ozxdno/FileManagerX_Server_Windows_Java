package Commands;

public class LoginConnection extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BasicEnums.ConnectionType type;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicEnums.ConnectionType getType() {
		return this.type;
	}
	
	public Replies.LoginConnection getReply() {
		return (Replies.LoginConnection)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnectionType(BasicEnums.ConnectionType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginConnection() {
		initThis();
	}
	private void initThis() {
		this.setReply(new Replies.LoginConnection());
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
		c.addToBottom(this.type.toString());
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		try {
			this.type = BasicEnums.ConnectionType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
		} catch(Exception e) {
			return null;
		}
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		LoginConnection t = (LoginConnection)o;
		this.type = t.type;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginConnection t = (LoginConnection)o;
		this.type = t.type;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
			this.reply();
			return false;
		}
		if(!this.isSourMachineIndexExist()) {
			this.reply();
			return false;
		}
		
		this.getConnection().setType(this.type);
		this.reply();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void reply() {
		this.setBasicMessagePackageToReply();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
