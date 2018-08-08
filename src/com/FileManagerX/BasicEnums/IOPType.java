package com.FileManagerX.BasicEnums;

public enum IOPType {

	WRITE_SOUR(true, true),
	WRITE_DEST(true, false),
	READ_SOUR(false, true),
	READ_DEST(false, false),
	;
	
	private boolean w;
	private boolean r;
	private boolean s;
	private boolean d;
	
	private IOPType(boolean isWrite, boolean isSour) {
		w = isWrite;
		r = !w;
		s = isSour;
		d = !s;
	}
	
	public boolean isWrite() {
		return w;
	}
	public boolean isRead() {
		return r;
	}
	public boolean operateSour() {
		return s;
	}
	public boolean operateDest() {
		return d;
	}
	
}
