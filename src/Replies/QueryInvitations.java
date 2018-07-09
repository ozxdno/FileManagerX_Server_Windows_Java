package Replies;

public class QueryInvitations extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int amount;
	private BasicModels.Invitation invitation;
	private BasicCollections.Invitations invitations;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setInvitation(BasicModels.Invitation invitation) {
		if(invitation == null) {
			return false;
		}
		this.invitation = invitation;
		return true;
	}
	public boolean setInvitations(BasicCollections.Invitations invitations) {
		if(invitations == null) {
			return false;
		}
		this.invitations = invitations;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getAmount() {
		return this.amount;
	}
	public BasicModels.Invitation getInvitation() {
		return this.invitation;
	}
	public BasicCollections.Invitations getInvitations() {
		return this.invitations;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryInvitations() {
		initThis();
	}
	private void initThis() {
		this.amount = 0;
		this.invitation = new BasicModels.Invitation();
		this.invitations = new BasicCollections.Invitations();
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
		c.addToBottom(this.amount);
		c.addToBottom(new BasicModels.Config(this.invitation.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		return this.invitation.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryInvitations qf = (QueryInvitations)o;
		this.amount = qf.amount;
		this.invitation = qf.invitation;
		this.invitations = qf.invitations;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryInvitations qf = (QueryInvitations)o;
		this.amount = qf.amount;
		this.invitation.copyValue(qf.invitation);
		this.invitations.copyValue(qf.invitations);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(this.invitations.size() >= this.amount) {
			return true;
		}
		
		BasicModels.Invitation newInvitation = new BasicModels.Invitation();
		newInvitation.copyValue(this.invitation);
		this.invitations.add(newInvitation);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
