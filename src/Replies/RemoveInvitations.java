package Replies;

public class RemoveInvitations extends Comman implements Interfaces.IReplies {

	public String toString() {
		return this.output();
	}
	
	public String output() {
		return super.output(this.getClass().getSimpleName());
	}
	
}
