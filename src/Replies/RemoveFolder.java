package Replies;

public class RemoveFolder extends Comman implements Interfaces.IReplies {

	public String toString() {
		return this.output();
	}
	
	public String output() {
		return super.output(this.getClass().getSimpleName());
	}
	
}

