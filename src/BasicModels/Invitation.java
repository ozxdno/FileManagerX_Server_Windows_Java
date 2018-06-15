package BasicModels;


public class Invitation implements Tools.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String code;
	private BasicModels.User user;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getCode() {
		return code;
	}
	public BasicModels.User getUser() {
		return user;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setCode(String code) {
		if(code == null) {
			return false;
		}
		this.code = code;
		return true;
	}
	public boolean setUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Invitation() {
		initThis();
	}
	public Invitation(String code, BasicModels.User user) {
		initThis();
		setCode(code);
		setUser(user);
	}
	private void initThis() {
		this.code = "";
		this.user = new BasicModels.User();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.code;
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config(user.output());
		c.setField("Invitation");
		c.addToTop(code);;
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		code = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		return user.input(c.output());
	}
	public void copyReference(Object o) {
		Invitation i = (Invitation)o;
		this.code = i.code;
		this.user.copyReference(i.user);
	}
	public void copyValue(Object o) {
		Invitation i = (Invitation)o;
		this.code = new String(i.code);
		this.user.copyValue(i.user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}