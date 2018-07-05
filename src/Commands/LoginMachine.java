package Commands;

public class LoginMachine extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Replies.LoginMachine getReply() {
		return (Replies.LoginMachine)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginMachine() {
		initThis();
	}
	private void initThis() {
		this.setReply(new Replies.LoginMachine());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toString() {
		return this.output();
	}
	public String output() {
		return super.output(this.getClass().getSimpleName());
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
		if(!Globals.Configurations.IsServer) {
			this.getConnection().setClientMachineInfo(this.getSourMachineInfo());
			this.reply();
			return this.getReply().isOK();
		}
		else {
			this.getConnection().setClientMachineInfo(this.getSourMachineInfo());
			this.reply();
			return this.getReply().isOK();
		}
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
