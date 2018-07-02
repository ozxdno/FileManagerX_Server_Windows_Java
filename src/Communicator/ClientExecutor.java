package Communicator;

public class ClientExecutor implements Interfaces.IReplyExecutor {

	private Replies.Executor executor;
	
	public Interfaces.IReplies getReply() {
		return executor.getReply();
	}
	
	public ClientExecutor() {
		initThis();
	}
	private void initThis() {
		executor = new Replies.Executor();
	}
	
	public boolean execute(Interfaces.IConnection connection) {
		return executor.execute(connection);
	}
}
