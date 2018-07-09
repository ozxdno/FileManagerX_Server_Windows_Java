package Commands;

public class LoginType extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BasicEnums.ConnectionType type;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicEnums.ConnectionType getType() {
		return this.type;
	}
	
	public Replies.LoginType getReply() {
		return (Replies.LoginType)super.getReply();
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

	public LoginType() {
		initThis();
	}
	private void initThis() {
		this.setReply(new Replies.LoginType());
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
		LoginType t = (LoginType)o;
		this.type = t.type;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginType t = (LoginType)o;
		this.type = t.type;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
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
