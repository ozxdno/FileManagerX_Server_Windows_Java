package BasicEnums;

public enum ErrorType {
	NORMAL,
	
	COMMON_FILE_READ_FAILED,
	COMMON_FILE_WRITE_FAILED,
	COMMON_FILE_OPERATE_FAILED,
	COMMON_STREAM_BUILD_FAILED,
	COMMON_STREAM_CLOSE_FAILED,
	COMMON_SET_WRONG_VALUE,
	COMMON_NULL,
	
	COMMANDS_EXECUTE_FAILED,
	
	COMMUNICATOR_BUILD_SOCKED_FAILED,
	COMMUNICATOR_RECEIVE_FAILED,
	COMMUNICATOR_SEND_FAILED,
	COMMUNICATOR_RUNNING_FAILED,
	COMMUNICATOR_CONNECTION_CLOSED,
	COMMUNICATOR_CONNECTION_BUSY,
	
	DB_CONNECT_FAILED,
	DB_DISCONNECT_FAILED,
	DB_OPERATE_FAILED,
	
	CFG_WRONG_CONTENT,
	CFG_NOT_EXIST,
	CFG_NOT_EQUAL,
	
	OTHERS
	;
	
	public BasicModels.Error getError() {
		return this.getError(BasicEnums.ErrorLevel.Warning, "", "");
	}
	public BasicModels.Error getError(BasicEnums.ErrorLevel level) {
		return this.getError(level, "", "");
	}
	public BasicModels.Error getError(String detail) {
		return this.getError(BasicEnums.ErrorLevel.Warning, "", detail);
	}
	public BasicModels.Error getError(BasicEnums.ErrorLevel level, String detail) {
		return this.getError(level, "", detail);
	}
	public BasicModels.Error getError(String reason, String detail) {
		return this.getError(BasicEnums.ErrorLevel.Warning, reason, detail);
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
		
		if(this.equals(ErrorType.COMMON_FILE_READ_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Read File Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMON_FILE_WRITE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Write File Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMON_FILE_OPERATE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Operate File Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMON_SET_WRONG_VALUE)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Use Wrong Value to call Set Function" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMON_NULL)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Value is NULL" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMON_STREAM_BUILD_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Build Stream Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMON_STREAM_CLOSE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Close Stream Failed" : reason);
			e.setDetail(detail);
		}
		
		if(this.equals(ErrorType.COMMANDS_EXECUTE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Command Execute Failed" : reason);
			e.setDetail(detail);
		}
		
		if(this.equals(ErrorType.COMMUNICATOR_BUILD_SOCKED_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Build Socked Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMUNICATOR_RECEIVE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Receive Data Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMUNICATOR_SEND_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Send Data Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMUNICATOR_CONNECTION_CLOSED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Connection Already has been Closed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.COMMUNICATOR_CONNECTION_BUSY)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Connection is Busy" : reason);
			e.setDetail(detail);
		}
		
		if(this.equals(ErrorType.DB_CONNECT_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Connect to DataBase Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.DB_DISCONNECT_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Close DataBase Failed" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.DB_OPERATE_FAILED)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "DataBase Operation Failed" : reason);
			e.setDetail(detail);
		}
		
		if(this.equals(ErrorType.CFG_WRONG_CONTENT)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Content is Wrong" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.CFG_NOT_EXIST)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Item Not Exist" : reason);
			e.setDetail(detail);
		}
		if(this.equals(ErrorType.CFG_NOT_EQUAL)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Value Not Equals to" : reason);
			e.setDetail(detail);
		}
		
		if(this.equals(ErrorType.OTHERS)) {
			e.setType(this);
			e.setLevel(level);
			e.setReason(reason.length() == 0 ? "Unknow Reason Type" : reason);
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
	public void register(String reason, String detail) {
		this.getError(reason, detail).register();
	}
	public void register(BasicEnums.ErrorLevel level, String reason, String detail) {
		this.getError(level, reason, detail).register();
	}
}
