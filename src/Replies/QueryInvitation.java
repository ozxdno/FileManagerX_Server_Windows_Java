package Replies;

public class QueryInvitation extends Comman implements Interfaces.IReplies {

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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryInvitation() {
		initThis();
	}
	private void initThis() {
		this.invitation = new BasicModels.Invitation();
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
		return this.invitation.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryInvitation qf = (QueryInvitation)o;
		this.invitation = qf.invitation;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryInvitation qf = (QueryInvitation)o;
		this.invitation.copyValue(qf.invitation);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
