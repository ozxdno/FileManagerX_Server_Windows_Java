package Replies;

public class LoginUser extends Comman implements Interfaces.IReplies {

	public String toString() {
		return this.output();
	}
	
	public String output() {
		return super.output(this.getClass().getSimpleName());
	}
	
	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		
		return true;
	}
	
}
