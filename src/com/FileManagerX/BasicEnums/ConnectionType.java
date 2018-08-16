package com.FileManagerX.BasicEnums;

public enum ConnectionType {
	
	UNDEFINE(false, false, false, false,  false, false),
	
	X(false, false, false, false,  false, false),
	
	S2X(false, false, false, true,  false, false),
	C2X(false, false, true, false,  false, false),
	X2S(false, true, false, false,  false, false),
	X2C(true, false, false, false,  false, false),
	
	TRANSPORT_COMMAND(false, false, false, false,  true, false),
	TRANSPORT_FILE(false, false, false, false,  false, true),
	
	S2S_COMMAND(false, true, false, true,  true, false),
	S2S_FILE(false, true, false, true,  false, true),
	
	S2C_COMMAND(true, false, false, true,  true, false),
	S2C_FILE(true, false, false, true,  false, true),
	
	C2S_COMMAND(false, true, true, false,  true, false),
	C2S_FILE(false, true, true, false,  false, true),
	
	C2C_COMMAND(true, false, true, false,  true, false),
	C2C_FILE(true, false, true, false,  false, true),
	;
	
	private boolean x2c;
	private boolean x2s;
	private boolean c2x;
	private boolean s2x;
	private boolean tCmd;
	private boolean tFile;
	
	private ConnectionType(boolean x2c, boolean x2s, boolean c2x, boolean s2x,
			boolean tCmd,
			boolean tFile) {
		this.x2c = x2c;
		this.x2s = x2s;
		this.c2x = c2x;
		this.s2x = s2x;
		
		this.tCmd = tCmd;
		this.tFile = tFile;
	}
	
	public boolean x2c() {
		return x2c;
	}
	public boolean x2s() {
		return x2s;
	}
	public boolean c2x() {
		return c2x;
	}
	public boolean s2x() {
		return s2x;
	}
	
	public boolean isTransportCommand() {
		return tCmd;
	}
	public boolean isTransportFile() {
		return tFile;
	}
	
	public boolean contains(ConnectionType type) {
		if(type == null) {
			return false;
		}
		
		if(this.equals(UNDEFINE)) {
			if(!type.equals(UNDEFINE)) { return false; }
		}
		if(this.c2x) {
			if(!type.c2x) { return false; }
		}
		if(this.s2x) {
			if(!type.s2x) { return false; }
		}
		if(this.x2c) {
			if(!type.x2c) { return false; }
		}
		if(this.x2s) {
			if(!type.x2s) { return false; }
		}
		
		if(this.tCmd) {
			if(!type.tCmd) { return false; }
		}
		if(this.tFile) {
			if(!type.tFile) { return false; }
		}
		
		return true;
	}
	
}
