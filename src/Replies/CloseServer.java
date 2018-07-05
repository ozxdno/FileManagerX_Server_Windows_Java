package Replies;

public class CloseServer extends Comman implements Interfaces.IReplies {
	
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
		if(this.getBasicMessagePackage().getDestMachineIndex() != Globals.Configurations.This_MachineIndex) {
			return true;
		}
		
		
		connection.setCloseServer(true);
		return true;
	}
}
