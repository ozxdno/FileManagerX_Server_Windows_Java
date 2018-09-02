package com.FileManagerX.Communicator;

public class IOPackage implements com.FileManagerX.Interfaces.IIOPackage {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicEnums.IOPType type;
	private boolean uncheck;
	private boolean cover;
	private String sourUrl;
	private String destUrl;
	private long totalBytes;
	private long finishedBytes;
	private long maxFlow;
	private String content;
	
	private Object stream;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(com.FileManagerX.BasicEnums.IOPType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setUncheck(boolean uncheck) {
		this.uncheck = uncheck;
		return true;
	}
	public boolean setCover(boolean cover) {
		this.cover = cover;
		return true;
	}
	public boolean setSourUrl(String url) {
		if(url == null) {
			return false;
		}
		this.sourUrl = url;
		return true;
	}
	public boolean setDestUrl(String url) {
		if(url == null) {
			return false;
		}
		this.destUrl = url;
		return true;
	}
	public boolean setTotalBytes(long totalBytes) {
		if(totalBytes < 0) {
			return false;
		}
		this.totalBytes = totalBytes;
		return true;
	}
	public boolean setFinishedBytes(long finishedBytes) {
		if(finishedBytes < 0) {
			finishedBytes = 0;
		}
		this.finishedBytes = finishedBytes;
		return true;
	}
	public boolean setMaxFlow(long maxFlow) {
		if(maxFlow < 0) {
			maxFlow = 0;
		}
		this.maxFlow = maxFlow;
		return true;
	}
	public boolean setContent(String content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	public boolean setStream(Object stream) {
		if(stream == null) {
			return this.closeStream();
		}
		
		this.stream = stream;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicEnums.IOPType getType() {
		return type;
	}
	public boolean isUncheck() {
		return this.uncheck;
	}
	public boolean isCover() {
		return this.cover;
	}
	public String getSourUrl() {
		return this.sourUrl;
	}
	public String getDestUrl() {
		return this.destUrl;
	}
	public long getTotalBytes() {
		return this.totalBytes;
	}
	public long getFinishedBytes() {
		return this.finishedBytes;
	}
	public long getMaxFlow() {
		return this.maxFlow;
	}
	public String getContent() {
		return this.content;
	}
	
	public Object getStream() {
		return this.stream;
	}
	
	public boolean isFinished() {
		if(this.stream == null) { this.createStream(); }
		if(this.stream == null) { return true; }
		
		return this.finishedBytes == this.totalBytes;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public IOPackage() {
		initThis();
	}
	private void initThis() {
		type = com.FileManagerX.BasicEnums.IOPType.READ_SOUR;
		uncheck = false;
		cover = true;
		this.sourUrl = "";
		this.destUrl = "";
		totalBytes = 0;
		this.finishedBytes = 0;
		this.maxFlow = com.FileManagerX.Globals.Configurations.LimitForIOPBuffer;
		this.content = "";
		
		stream = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.BasicEnums.IOPType type, boolean uncheck, boolean cover,
			String sourUrl, String destUrl,
			long finishedBytes, long maxFlow, String content) {
		boolean ok = true;
		ok &= this.setType(type);
		ok &= this.setUncheck(uncheck);
		ok &= this.setCover(cover);
		ok &= this.setSourUrl(sourUrl);
		ok &= this.setDestUrl(destUrl);
		ok &= this.setFinishedBytes(finishedBytes);
		ok &= this.setMaxFlow(maxFlow);
		ok &= this.setContent(content);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		String direction = "";
		if(this.type.equals(com.FileManagerX.BasicEnums.IOPType.READ_DEST)) {
			direction = "<-";
		}
		if(this.type.equals(com.FileManagerX.BasicEnums.IOPType.READ_SOUR)) {
			direction = "->";
		}
		if(this.type.equals(com.FileManagerX.BasicEnums.IOPType.WRITE_DEST)) {
			direction = "->";
		}
		if(this.type.equals(com.FileManagerX.BasicEnums.IOPType.WRITE_SOUR)) {
			direction = "<-";
		}
		
		return "[" + com.FileManagerX.Tools.Url.getName(sourUrl) +
				direction +
				com.FileManagerX.Tools.Url.getName(destUrl) + "] " +
				this.type.toString() + ": " +
				this.content;
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.type.toString());
		c.addToBottom(this.uncheck);
		c.addToBottom(this.cover);
		c.addToBottom(this.sourUrl);
		c.addToBottom(this.destUrl);
		c.addToBottom(this.totalBytes);
		c.addToBottom(this.finishedBytes);
		c.addToBottom(this.maxFlow);
		c.addToBottom_Encode(this.content);
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return c; }
		try {
			if(!c.getIsOK()) { return c; }
			this.type = com.FileManagerX.BasicEnums.IOPType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.uncheck = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return c; }
			this.cover = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return c; }
			this.sourUrl = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.destUrl = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.totalBytes = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.finishedBytes = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.maxFlow = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.content = c.fetchFirstString_Decode();
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			e.printStackTrace();
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof IOPackage) {
			IOPackage op = (IOPackage)o;
			this.uncheck = op.uncheck;
			//this.type = op.type;
			this.cover = op.cover;
			this.sourUrl = op.sourUrl;
			this.destUrl = op.destUrl;
			this.totalBytes = op.totalBytes;
			//this.finishedBytes = op.finishedBytes;
			this.maxFlow = op.maxFlow;
			this.content = op.content;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof IOPackage) {
			IOPackage op = (IOPackage)o;
			this.uncheck = op.uncheck;
			//this.type = op.type;
			this.cover = op.cover;
			this.sourUrl = new String(op.sourUrl);
			this.destUrl = new String(op.destUrl);
			this.totalBytes = op.totalBytes;
			//this.finishedBytes = op.finishedBytes;
			this.content = new String(op.content);
			this.maxFlow = op.maxFlow;
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean createStream() {
		if(stream != null) {
			return true;
		}
		
		if(type.isWrite()) {
			try {
				java.io.File f = new java.io.File(type.operateSour() ? this.sourUrl : this.destUrl);
				if(f.exists() && f.isFile() && !this.cover) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_EXISTED.register();
					return false;
				}
				boolean append = this.finishedBytes != 0;
				java.io.FileOutputStream fos = new java.io.FileOutputStream(f, append);
				stream = fos;
				return true;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(e.toString());
				return false;
			}
		}
		else {
			try {
				java.io.File f = new java.io.File(type.operateSour() ? this.sourUrl : this.destUrl);
				if(f.exists() && f.isFile() && !this.cover) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_EXISTED.register();
					return false;
				}
				this.totalBytes = f.length();
				java.io.FileInputStream fis = new java.io.FileInputStream(f);
				fis.skip(this.finishedBytes);
				stream = fis;
				return true;
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(e.toString());
				return false;
			}
		}
	}
	public boolean closeStream() {
		try {
			if(stream == null) {
				;
			}
			else if(stream instanceof java.io.FileInputStream) {
				java.io.FileInputStream s = (java.io.FileInputStream)stream;
				s.close();
				String url = this.type.operateSour() ? this.sourUrl : this.destUrl;
				com.FileManagerX.Tools.Update.addSingleFile(url);
			}
			else if(stream instanceof java.io.FileOutputStream) {
				java.io.FileOutputStream s = (java.io.FileOutputStream)stream;
				s.close();
			}
		} catch(Exception e) {
			;
		}
		
		this.stream = null;
		return true;
	}
	public boolean execute() {
		if(this.type.isWrite()) {
			return this.write();
		}
		if(this.type.isRead()) {
			return this.read();
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean write() {
		if(this.stream == null) { this.createStream(); }
		if(this.stream == null) { return false; }
		
		try {
			java.io.FileOutputStream s = (java.io.FileOutputStream)stream;
			byte[] bytes = this.content.getBytes();
			s.write(bytes);
			s.flush();
			this.finishedBytes += bytes.length;
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(e.toString());
			return false;
		}
	}
	private boolean read() {
		if(this.stream == null) { this.createStream(); }
		if(this.stream == null) { return false; }
		
		try {
			java.io.FileInputStream s = (java.io.FileInputStream)stream;
			int max = (int) (this.maxFlow);
			
			byte[] readBytes = new byte[max];
			int readLen = s.read(readBytes);
			
			this.content = new String(readBytes, 0, readLen);
			this.finishedBytes += readLen;
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
