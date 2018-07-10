package Replies;

public class Restart extends Comman implements Interfaces.IReplies {
	
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
		if(this.getBasicMessagePackage().getDestMachineIndex() == connection.getServerMachineInfo().getIndex()) {
			connection.setAbort(true);
		}
		
		return true;
	}
}
