package com.FileManagerX.BasicEnums;

public enum OperateType {

	IDLE,
	
	MATCH,
	
	INPUT,
	OUTPUT,
	THUMBNAIL,
	PRINT_SCREEN,
	OPEN_IN_SYSTEM,
	
	CHECK_SERVER,
	CHECK_SERVER_INDEX,
	
	CHECK_NOT_SERVER,
	CHECK_FOLDERS_FILES,
	
	CREATE_FOLDER,
	CREATE_FILE,
	DELETE_FOLDER,
	DELETE_FILE,
	DELETE_CONTENT,
	COPY_FOLDER,
	COPY_FILE,
	RENAME_FOLDER,
	RENAME_FILE,
	MOVE_FOLDER,
	MOVE_FILE,
	RENAME_FOLDER_WITHOUT_EXTENSION,
	RENAME_FILE_WITHOUT_EXTENSION,
	;
	
	public com.FileManagerX.Operator.Operator getOperator() {
		com.FileManagerX.Operator.Operator op = new com.FileManagerX.Operator.Operator();
		op.setType(this);
		
		if(this.equals(OperateType.INPUT)) {
			op.setAbort(false);
			op.setStop(false);
		}
		
		return op;
	}
	public long register() {
		com.FileManagerX.Operator.Operator op = this.getOperator();
		return op.register() ? op.getIndex() : -1;
	}
}
