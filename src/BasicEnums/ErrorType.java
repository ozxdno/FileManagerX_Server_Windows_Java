package BasicEnums;

public enum ErrorType {
	NORMAL,
	COMMON_FILE_READ_FAILED,
	COMMON_FILE_WRITE_FAILED,
	COMMON_FILE_OPERATE_FAILED,
	READ_FILE_FAILED,
	WRITE_FILE_FAILED,
	READ_WRITE_FILE_FAILED,
	FILE_OPERATION_FAILED,
	READ_WRITE_CFG_FAILED,
	WRONG_CFG_CONTENT,
	DELETE_LOG_FAILED,
	CONNECT_SERVER_FAILED,
	RECEIVE_OVER_TIME,
	SEND_OVER_TIME,
	EXECUTE_COMMAND_FAILED,
	BUILD_SOCKET_FAILED,
	SERVER_CONNECTION_STREAM_BUILD_FAILED,
	SERVER_CONNECTION_RUNNING_FAILED,
	CLIENT_CONNECTION_STREAM_BUILD_FAILED,
	CLIENT_CONNECTION_RUNNING_FAILED,
	DB_CONNECT_FAILED,
	DB_DISCONNECT_FAILED,
	DB_OPERATION_FAILED,
	COMMUNICATOR_CONNECTION_CLOSED,
	COMMUNICATOR_OVER_TIME,
	CONNECTION_BUSY,
	DB_CONNECTION_CLOSED,
	WRONG_CONFIGURATIONS_INDEX,
	UNKNOW
	;
	
	public BasicModels.Error getError() {
		return this.getError(BasicEnums.ErrorLevel.Error, "", "");
	}
	public BasicModels.Error getError(BasicEnums.ErrorLevel level) {
		return this.getError(level, "", "");
	}
	public BasicModels.Error getError(String detail) {
		return this.getError(BasicEnums.ErrorLevel.Error, "", detail);
	}
	public BasicModels.Error getError(BasicEnums.ErrorLevel level, String detail) {
		return this.getError(level, "", detail);
	}
	public BasicModels.Error getError(BasicEnums.ErrorLevel level, String reason, String detail) {
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		String tip = "";
		for(int i=1; i<stes.length; i++) {
			if(stes[i].getClassName().equals(this.getClass().getName())) {
				continue;
			}
			if(stes[i].getFileName() == null || stes[i].getLineNumber() < 0) {
				continue;
			}
			tip += "<-" + stes[i].getMethodName() + "[" + stes[i].getFileName() + ":" + stes[i].getLineNumber() + "]";
		}
		if(tip.length() > 2) { tip = tip.substring(2); }
		
		BasicModels.Error e = new BasicModels.Error();
		e.setTip(tip);
		
		if(this.equals(ErrorType.NORMAL)) {
			e.setType(this);
			e.setLevel(BasicEnums.ErrorLevel.Normal);
			e.setReason(reason.length() == 0 ? "Normal" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.READ_FILE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Read File Failed");
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.WRITE_FILE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Write File Failed");
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.READ_WRITE_FILE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Read or Write File Failed");
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.FILE_OPERATION_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("File Operation Failed, such as: Create, Delete, Copy");
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.READ_WRITE_CFG_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Read or Write CFG File Failed");
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.WRONG_CFG_CONTENT)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("the Content of CFG File have mistakes");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.DELETE_LOG_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Delete error log Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.CONNECT_SERVER_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Cannot connect to Server");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.RECEIVE_OVER_TIME)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Too long to Receive Info");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.SEND_OVER_TIME)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Too long to Send Info");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.EXECUTE_COMMAND_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Execute Command Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.BUILD_SOCKET_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Build Socket Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.SERVER_CONNECTION_STREAM_BUILD_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Build InputStream or OutputStream of Server Connection Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.SERVER_CONNECTION_RUNNING_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Error occurs in Server Connection Running");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.CLIENT_CONNECTION_STREAM_BUILD_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Build InputStream or OutputStream of Client Connection Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.CLIENT_CONNECTION_RUNNING_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Error occurs in Client Connection Running");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.DB_CONNECT_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Connect DataBase Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.DB_DISCONNECT_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Disconnect DataBase Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.DB_OPERATION_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Operate DataBase Failed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMUNICATOR_CONNECTION_CLOSED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Connection is Closed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMUNICATOR_OVER_TIME)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Connection Receive/Send/Wait Over Time");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.CONNECTION_BUSY)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Connection is busy");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.DB_CONNECTION_CLOSED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Connection is Closed");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.WRONG_CONFIGURATIONS_INDEX)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("One or More Index in Configurations is Wrong");
			
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.UNKNOW)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason("Unknow");
			e.setDetail(detail);
		}
		
		return e;
	}
	
	public void register() {
		this.getError().register();
	}
	public void register(String detail) {
		this.getError(detail).register();
	}
	public void register(BasicEnums.ErrorLevel level) {
		this.getError(level).register();
	}
	public void register(BasicEnums.ErrorLevel level, String detail) {
		this.getError(level, detail).register();
	}
}
