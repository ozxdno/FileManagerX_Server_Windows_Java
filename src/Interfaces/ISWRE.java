package Interfaces;

/**
 * 
 * @author ozxdno
 * 
 * S: Send
 * W: Wait
 * R: Receive
 * E: Execute
 *
 */
public interface ISWRE {
	
	public boolean setSendWaitTicks(long sendWaitTicks);
	public boolean setReceiveWaitTicks(long receiveWaitTicks);
	public boolean setConnection(Interfaces.IClientConnection connection);
	
	public long getSendWaitTicks();
	public long getReceiveWaitTicks();
	public Interfaces.IClientConnection getConnection();
	
	public Interfaces.IReplies execute();
	public Interfaces.IReplies execute(String command);
}
