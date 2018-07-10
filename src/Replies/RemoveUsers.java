package Replies;

public class RemoveUsers extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int amount;
	private BasicModels.User user;
	private BasicCollections.Users users;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	public boolean setUsers(BasicCollections.Users users) {
		if(users == null) {
			return false;
		}
		this.users = users;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getAmount() {
		return this.amount;
	}
	public BasicModels.User getUser() {
		return this.user;
	}
	public BasicCollections.Users getUsers() {
		return this.users;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RemoveUsers() {
		initThis();
	}
	private void initThis() {
		this.amount = 0;
		this.user = new BasicModels.User();
		this.users = new BasicCollections.Users();
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
		c.addToBottom(new BasicModels.Config(this.user.output()));
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
		
		return this.user.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		RemoveUsers qf = (RemoveUsers)o;
		this.amount = qf.amount;
		this.user = qf.user;
		this.users = qf.users;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		RemoveUsers qf = (RemoveUsers)o;
		this.amount = qf.amount;
		this.user.copyValue(qf.user);
		this.users.copyValue(qf.users);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(this.users.size() >= this.amount) {
			return true;
		}
		
		BasicModels.User newUser = new BasicModels.User();
		newUser.copyValue(user);
		this.users.add(newUser);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
