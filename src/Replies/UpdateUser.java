package Replies;

public class UpdateUser extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.User user;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.User getUser() {
		return this.user;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateUser() {
		initThis();
	}
	private void initThis() {
		this.user = new BasicModels.User();
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
		c.addToBottom(new BasicModels.Config(this.user.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.user.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateUser qf = (UpdateUser)o;
		this.user = qf.user;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateUser qf = (UpdateUser)o;
		this.user.copyValue(qf.user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}