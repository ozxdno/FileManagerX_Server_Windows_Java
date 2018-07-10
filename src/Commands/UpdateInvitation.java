package Commands;

public class UpdateInvitation extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.Invitation invitation;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setInvitation(BasicModels.Invitation invitation) {
		if(invitation == null) {
			return false;
		}
		this.invitation = invitation;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.Invitation getInvitation() {
		return this.invitation;
	}
	
	public Replies.UpdateInvitation getReply() {
		return (Replies.UpdateInvitation)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateInvitation() {
		initThis();
	}
	public UpdateInvitation(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.invitation = new BasicModels.Invitation();
		super.setReply(new Replies.UpdateInvitation());
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
		c.addToBottom(new BasicModels.Config(this.invitation.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		return this.invitation.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateInvitation um = (UpdateInvitation)o;
		this.invitation = um.invitation;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateInvitation um = (UpdateInvitation)o;
		this.invitation.copyValue(um.invitation);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!super.isConnected()) {
			this.reply();
			return false;
		}
		
		return Globals.Configurations.StartType.equals(BasicEnums.StartType.Server) ?
				this.executeInServer() :
				this.executeInInvitation();
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
		
		if(!Globals.Datas.DBManager.updataInvitation(invitation)) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Update to Invitation Failed");
			this.reply();
			return false;
		}
		
		this.getReply().setInvitation(invitation);
		this.reply();
		return true;
	}
	private boolean executeInInvitation() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
