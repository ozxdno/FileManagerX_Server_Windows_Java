package Factories;

public class ReplyFactory {

	public static final Interfaces.IReplyExecutor createExecutor() {
		return new Replies.Executor();
	}
	
}
