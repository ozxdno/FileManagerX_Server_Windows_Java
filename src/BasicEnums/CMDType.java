package BasicEnums;

public enum CMDType {

	CloseServer(new Commands.CloseServer(), new Replies.CloseServer()),
	CloseConnection(new Commands.CloseServer(), new Replies.CloseServer()),
	Restart(new Commands.Restart(), new Replies.Restart()),
	Test(new Commands.Test(), new Replies.Test()),
	;
	
	private Interfaces.ICommands cmd;
	private Interfaces.IReplies rep;
	
	private CMDType(Interfaces.ICommands cmd, Interfaces.IReplies rep) {
		this.cmd = cmd;
		this.rep = rep;
	}
	
	public Interfaces.ICommands getCommand() {
		return this.cmd;
	}
	public Interfaces.IReplies getReply() {
		return this.rep;
	}
	
	public boolean setCommand(Interfaces.ICommands cmd) {
		if(cmd == null) {
			return false;
		}
		this.cmd = cmd;
		return true;
	}
	public boolean setReply(Interfaces.IReplies rep) {
		if(rep == null) {
			return false;
		}
		this.rep = rep;
		return true;
	}
}
