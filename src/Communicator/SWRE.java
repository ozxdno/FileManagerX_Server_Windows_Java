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
	private Interfaces.IConnection connection;
	
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
	public boolean setConnection(Interfaces.IConnection connection) {
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
	public Interfaces.IConnection getConnection() {
		return this.connection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public SWRE() {
		initThis();
	}
	private void initThis() {
		this.sendWaitTicks = Globals.Configurations.TimeForCommandSend;
		this.receiveWaitTicks = Globals.Configurations.TimeForCommandReceive;
		this.connection = Globals.Datas.ServerConnection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IReplies execute() {
		if(connection == null || connection.getExecutor() == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("Connection is NULL or Executor is NULL");
			return null;
		}
		
		this.setPredict();
		
		connection.setActiveExecutor(true);
		connection.setContinueSendString();
		
		/*
		if(!Tools.Time.waitUntilConnectionIdle(this.sendWaitTicks, connection)) {
			BasicEnums.ErrorType.COMMUNICATOR_SEND_FAILED.register("Time out");
			return null;
		}
		*/
		
		//connection.setContinueReceiveString();
		if(!Tools.Time.waitUntilConnectionIdle(this.receiveWaitTicks, connection)) {
			BasicEnums.ErrorType.COMMUNICATOR_RECEIVE_FAILED.register("Time out");
			return null;
		}
		
		//connection.setActiveExecutor(false);
		//connection.setContinueWait();
		Interfaces.IReplies rep = ((Interfaces.IReplyExecutor)connection.getExecutor()).getReply();
		//this.connection.setBusy(false);
		
		return rep;
	}
	
	public Interfaces.IReplies execute(String command) {
		if(connection == null || connection.getExecutor() == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("Connection is NULL or Executor is NULL");
			return null;
		}
		
		// 等待线程准备好
		if(!Tools.Time.waitUntilConnectionIdle(this.sendWaitTicks, connection)) {
			BasicEnums.ErrorType.COMMUNICATOR_SEND_FAILED.register("Time out");
			return null;
		}
		
		connection.setSendString(command);
		return this.execute();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean setPredict() {
		Interfaces.IExecutor exe = this.connection.getExecutor();
		if(!(exe instanceof Interfaces.IReplyExecutor)) {
			return false;
		}
		Interfaces.IReplyExecutor rex = (Interfaces.IReplyExecutor)exe;
		String cmd = Tools.String.getField(connection.getSendString());
		cmd = Tools.String.clearLRSpace(cmd);
		
		rex.setPredict(cmd);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
