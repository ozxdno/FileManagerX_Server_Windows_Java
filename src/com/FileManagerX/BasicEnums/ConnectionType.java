package com.FileManagerX.BasicEnums;

public enum ConnectionType {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	UNDEFINE(false, false, false, false,  false, false),
	ANY(false, false, false, false,  false, false),
	X(false, false, false, false,  false, false),
	
	S2X(false, false, false, true,  false, false),
	C2X(false, false, true, false,  false, false),
	X2S(false, true, false, false,  false, false),
	X2C(true, false, false, false,  false, false),
	
	S2X_COMMAND(false, false, false, true,  true, false),
	C2X_COMMAND(false, false, true, false,  true, false),
	X2S_COMMAND(false, true, false, false,  true, false),
	X2C_COMMAND(true, false, false, false,  true, false),
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class Content {
		public boolean x2c;
		public boolean x2s;
		public boolean c2x;
		public boolean s2x;
		public boolean tCmd;
		public boolean tFile;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ConnectionType.Content content = new ConnectionType.Content();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ConnectionType(boolean x2c, boolean x2s, boolean c2x, boolean s2x, boolean tCmd, boolean tFile) {
		this.content.x2c = x2c;
		this.content.x2s = x2s;
		this.content.c2x = c2x;
		this.content.s2x = s2x;
		
		this.content.tCmd = tCmd;
		this.content.tFile = tFile;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ConnectionType.Content getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean contains(ConnectionType type) {
		if(type == null) { return false; }
		
		if(this.equals(UNDEFINE)) { if(!type.equals(UNDEFINE)) { return false; } }
		if(this.content.c2x) { if(!type.content.c2x) { return false; } }
		if(this.content.s2x) { if(!type.content.s2x) { return false; } }
		if(this.content.x2c) { if(!type.content.x2c) { return false; } }
		if(this.content.x2s) { if(!type.content.x2s) { return false; } }
		
		if(this.content.tCmd) { if(!type.content.tCmd) { return false; } }
		if(this.content.tFile) { if(!type.content.tFile) { return false; } }
		return true;
	}
	public ConnectionType specific(ConnectionType.Content content) {
		ConnectionType.Content type = content;
		
		if(equalsContent(type, X2C)) { return X2C; }
		if(equalsContent(type, X2S)) { return X2S; }
		if(equalsContent(type, C2X)) { return C2X; }
		if(equalsContent(type, S2X)) { return S2X; }
		
		if(equalsContent(type, X2C_COMMAND)) { return X2C_COMMAND; }
		if(equalsContent(type, X2S_COMMAND)) { return X2S_COMMAND; }
		if(equalsContent(type, C2X_COMMAND)) { return C2X_COMMAND; }
		if(equalsContent(type, S2X_COMMAND)) { return S2X_COMMAND; }
		
		if(equalsContent(type, TRANSPORT_COMMAND)) { return TRANSPORT_COMMAND; }
		if(equalsContent(type, TRANSPORT_FILE)) { return TRANSPORT_FILE; }
		
		if(equalsContent(type, S2S_COMMAND)) { return S2S_COMMAND; }
		if(equalsContent(type, S2S_FILE)) { return S2S_FILE; }
		if(equalsContent(type, S2C_COMMAND)) { return S2C_COMMAND; }
		if(equalsContent(type, S2C_FILE)) { return S2C_FILE; }
		
		if(equalsContent(type, C2S_COMMAND)) { return C2S_COMMAND; }
		if(equalsContent(type, C2S_FILE)) { return C2S_FILE; }
		if(equalsContent(type, C2C_COMMAND)) { return C2C_COMMAND; }
		if(equalsContent(type, C2C_FILE)) { return C2C_FILE; }
		return X;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ConnectionType and(ConnectionType t1, ConnectionType t2) {
		ConnectionType.Content c = new ConnectionType.Content();
		
		c.c2x = t1.content.c2x || t2.content.c2x;
		c.s2x = t1.content.s2x || t2.content.s2x;
		c.x2c = t1.content.x2c || t2.content.x2c;
		c.x2s = t1.content.x2s || t2.content.x2s;
		c.tCmd = t1.content.tCmd || t2.content.tCmd;
		c.tFile = t1.content.tFile || t2.content.tFile;
		return this.specific(c);
	}
	public ConnectionType exchange() {
		ConnectionType.Content c = new ConnectionType.Content();
		
		c.c2x = this.content.x2c;
		c.s2x = this.content.x2s;
		c.x2c = this.content.c2x;
		c.x2s = this.content.s2x;
		
		c.tCmd = this.content.tCmd;
		c.tFile = this.content.tFile;
		return this.specific(c);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean equalsContent(ConnectionType.Content t1, ConnectionType t2) {
		return t1.c2x == t2.content.c2x &&
			   t1.s2x == t2.content.s2x &&
			   t1.x2c == t2.content.x2c &&
			   t1.x2s == t2.content.x2s &&
			   t1.tCmd == t2.content.tCmd &&
			   t1.tFile == t2.content.tFile;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
