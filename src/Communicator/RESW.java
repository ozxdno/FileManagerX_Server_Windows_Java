package Communicator;


/**
 * A combine of Receive, Execute, Send, Wait.
 * 
 * When you want to send a command through a connection, you can use this.
 * Receive often solved by connection, because connection keep receive forever.
 * It help you to know sending is success or not.
 * It is a part of Server Executor.
 * 
 * @author ozxdno
 * 
 * This Function contains in Command's execute, so it's useless.
 *
 */
public class RESW implements Interfaces.IRESW {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long sendWaitTicks;
	private long receiveWaitTicks;
	private Interfaces.IServerConnection connection;
	
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
	public boolean setConnection(Interfaces.IServerConnection connection) {
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
	public Interfaces.IServerConnection getConnection() {
		return this.connection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RESW() {
		initThis();
	}
	public RESW(Interfaces.IServerConnection connection) {
		initThis();
		this.setConnection(connection);
	}
	private void initThis() {
		this.sendWaitTicks = Globals.Configurations.TimeForCommandSend;
		this.receiveWaitTicks = Globals.Configurations.TimeForCommandReceive;
		this.connection = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(connection == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("Connection is NULL");
			return false;
		}
		if(!Tools.Time.waitUntilConnectionIdle(this.receiveWaitTicks, connection)) {
			BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_BUSY.register("Connection is Busy");
			return false;
		}
		
		connection.setContinueSendString();
		if(!Tools.Time.waitUntilConnectionIdle(sendWaitTicks, connection)) {
			BasicEnums.ErrorType.COMMUNICATOR_SEND_FAILED.register("wait Too long to send Command");
			return false;
		}
		
		return true;
	}
	public boolean execute(String command) {
		if(connection == null) {
			BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register("Connection is NULL");
			return false;
		}
		if(!Tools.Time.waitUntilConnectionIdle(this.receiveWaitTicks, connection)) {
			BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_BUSY.register("Connection is Busy");
			return false;
		}
		
		connection.setSendString(command);
		connection.setContinueSendString();
		if(!Tools.Time.waitUntilConnectionIdle(sendWaitTicks, connection)) {
			BasicEnums.ErrorType.COMMUNICATOR_SEND_FAILED.register("wait Too long to send Command");
			return false;
		}
		
		return true;
	}
	public boolean execute(Interfaces.IServerConnection connection) {
		this.setConnection(connection);
		return this.execute();
	}
	public boolean execute(String command, Interfaces.IServerConnection connection) {
		this.setConnection(connection);
		return this.execute(command);
	}
}
