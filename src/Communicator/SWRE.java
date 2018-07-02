package Communicator;

/**
 * A combine of Send, Wait, Receive, Execute.
 * 
 * When you want send a command through a connection, you can use this.
 * It also help you to get Reply message.
 * 
 * @author ozxdno
 *
 */
public class SWRE implements Interfaces.ISWRE {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long sendWaitTicks;
	private long receiveWaitTicks;
	private Interfaces.IClientConnection connection;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setSendWaitTicks(long sendWaitTicks) {
		if(sendWaitTicks < 0) {
			return false;
		}
		this.sendWaitTicks = sendWaitTicks;
		return true;
	}
	public boolean setReceiveWaitTicks(long receiveWaitTicks) {
		if(receiveWaitTicks < 0) {
			return false;
		}
		this.receiveWaitTicks = receiveWaitTicks;
		return true;
	}
	public boolean setConnection(Interfaces.IClientConnection connection) {
		if(connection == null) {
			return false;
		}
		if(!connection.isRunning()) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getSendWaitTicks() {
		return this.sendWaitTicks;
	}
	public long getReceiveWaitTicks() {
		return this.receiveWaitTicks;
	}
	public Interfaces.IClientConnection getConnection() {
		return this.connection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public SWRE() {
		initThis();
	}
	private void initThis() {
		this.sendWaitTicks = 100;
		this.receiveWaitTicks = 1000;
		this.connection = Globals.Datas.ServerConnection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IReplies execute() {
		if(connection == null || connection.getExecutor() == null) {
			BasicEnums.ErrorType.UNKNOW.register("Connection is NULL or Executor is NULL");
			return null;
		}
		
		connection.setActiveExecutor(true);
		connection.setContinueSendString();
		
		if(!Tools.Time.waitUntilConnectionIdle(this.sendWaitTicks, connection)) {
			BasicEnums.ErrorType.SEND_OVER_TIME.register("You can check Your net make sure you and Server in a same LAN");
			return null;
		}
		
		connection.setContinueReceiveString();
		if(!Tools.Time.waitUntilConnectionIdle(this.receiveWaitTicks, connection)) {
			BasicEnums.ErrorType.RECEIVE_OVER_TIME.register("You can check Your net make sure you and Server in a same LAN");
			return null;
		}
		
		//connection.setActiveExecutor(false);
		return ((Interfaces.IReplyExecutor)connection.getExecutor()).getReply();
	}
	
	public Interfaces.IReplies execute(String command) {
		if(connection == null || connection.getExecutor() == null) {
			BasicEnums.ErrorType.UNKNOW.register("Connection is NULL or Executor is NULL");
			return null;
		}
		connection.setSendString(command);
		return this.execute();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}