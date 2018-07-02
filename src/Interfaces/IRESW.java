package Interfaces;

/**
 * 
 * @author ozxdno
 * 
 * R: Receive
 * E: Execute
 * S: Send
 * W: Wait
 *
 */
public interface IRESW {

	public boolean setSendWaitTicks(long sendWaitTicks);
	public boolean setReceiveWaitTicks(long receiveWaitTicks);
	public boolean setConnection(Interfaces.IServerConnection connection);
	
	public long getSendWaitTicks();
	public long getReceiveWaitTicks();
	public Interfaces.IServerConnection getConnection();
	
	public boolean execute();
	public boolean execute(String command);
	public boolean execute(Interfaces.IServerConnection connection);
	public boolean execute(String command, Interfaces.IServerConnection connection);
	
}
