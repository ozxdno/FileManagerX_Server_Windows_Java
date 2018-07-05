package Communicator;

public class FileConnector implements Interfaces.IFileConnector {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long sourMachine;
	private long destMachine;
	private long sourDepot;
	private long destDepot;
	
	private boolean input;
	private boolean output;
	private String sourUrl; // also means input url
	private String destUrl; // also means output url
	
	private long finishedBytes;
	private long totalBytes;
	
	private boolean cover;
	private boolean readFromLocal;
	private boolean writeToLocal;
	
	private byte[] sendBytes;
	private byte[] receiveBytes;
	private volatile int sendLength;
	private volatile int receiveLength;
	
	private Interfaces.IConnection connection;
	
	private volatile boolean active;
	private volatile boolean busy;
	private volatile boolean stop;
	
	private java.io.FileOutputStream fos;
	private java.io.FileInputStream fis;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setSourMachine(long index) {
		if(index < 0) {
			return false;
		}
		this.sourMachine = index;
		return true;
	}
	public boolean setDestMachine(long index) {
		if(index < 0) {
			return false;
		}
		this.destMachine = index;
		return true;
	}
	public boolean setSourDepot(long index) {
		if(index < 0) {
			return false;
		}
		this.sourDepot = index;
		return true;
	}
	public boolean setDestDepot(long index) {
		if(index < 0) {
			return false;
		}
		this.destDepot = index;
		return true;
	}

	public boolean setIsInputCommand(boolean isInput) {
		this.input = isInput;
		this.output = !this.input;
		return true;
	}
	public boolean setIsOutputCommand(boolean isOutput) {
		this.output = isOutput;
		this.input = !this.output;
		return true;
	}
	public boolean setSourUrl(String url) {
		if(url == null || url.length() == 0) {
			return false;
		}
		this.sourUrl = url;
		return true;
	}
	public boolean setDestUrl(String url) {
		if(url == null || url.length() == 0) {
			return false;
		}
		this.destUrl = url;
		return true;
	}
	
	public boolean setFinishedBytes(long finishedBytes) {
		if(finishedBytes < 0) {
			return false;
		}
		this.finishedBytes = finishedBytes;
		return true;
	}
	public boolean setTotalBytes(long totalBytes) {
		if(totalBytes < 0) {
			return false;
		}
		this.totalBytes = totalBytes;
		return true;
	}
	
	public boolean setIsCoverExistedFile(boolean isCover) {
		this.cover = isCover;
		return true;
	}
	public boolean setIsReadFromLocal(boolean readFromLocal) {
		this.readFromLocal = readFromLocal;
		return true;
	}
	public boolean setIsWriteToLocal(boolean writeToLocal) {
		this.writeToLocal = writeToLocal;
		return true;
	}
	
	public boolean setSendBytes(byte[] sendBytes) {
		if(sendBytes == null) {
			return false;
		}
		this.sendBytes = sendBytes;
		return true;
	}
	public boolean setReceiveBytes(byte[] receiveBytes) {
		if(receiveBytes == null) {
			return false;
		}
		this.receiveBytes = receiveBytes;
		return true;
	}
	public boolean setSendLength(int length) {
		if(length < 0) {
			return false;
		}
		this.sendLength = length;
		return true;
	}
	public boolean setReceiveLength(int length) {
		if(length < 0) {
			return false;
		}
		this.receiveLength = length;
		return true;
	}
	
	public boolean setConnection(Interfaces.IConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	
	public boolean setState_Active(boolean active) {
		this.active = active;
		return true;
	}
	public boolean setState_Busy(boolean busy) {
		this.busy = busy;
		return true;
	}
	public boolean setState_Stop(boolean stop) {
		this.stop = stop;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getSourMachine() {
		return this.sourMachine;
	}
	public long getDestMachine() {
		return this.destMachine;
	}
	public long getSourDepot() {
		return this.sourDepot;
	}
	public long getDestDepot() {
		return this.destDepot;
	}

	public boolean isInputCommand() {
		return this.input;
	}
	public boolean isOutputCommand() {
		return this.output;
	}
	public String getSourUrl() {
		return this.sourUrl;
	}
	public String getDestUrl() {
		return this.destUrl;
	}
	
	public long getFinishedBytes() {
		return this.finishedBytes;
	}
	public long getTotalBytes() {
		return this.totalBytes;
	}
	
	public boolean isCoverExistedFile() {
		return this.cover;
	}
	public boolean isReadFromLocal() {
		return this.readFromLocal;
	}
	public boolean isWriteToLocal() {
		return this.writeToLocal;
	}
	
	public byte[] getSendBytes() {
		return this.sendBytes;
	}
	public byte[] getReceiveBytes() {
		return this.receiveBytes;
	}
	public int getSendLength() {
		return this.sendLength;
	}
	public int getReceiveLength() {
		return this.receiveLength;
	}
	
	public Interfaces.IConnection getConnection() {
		return this.connection;
	}
	
	public boolean isActive() {
		return this.active;
	}
	public boolean isBusy() {
		return this.busy;
	}
	public boolean isStop() {
		return this.stop;
	}
	public boolean isFinished() {
		return this.finishedBytes >= this.totalBytes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public FileConnector() {
		initThis();
	}
	private void initThis() {
		this.sourMachine = 0;
		this.destMachine = 0;
		this.sourDepot = 0;
		this.destDepot = 0;
		
		this.input = false;
		this.output = false;
		this.sourUrl = "";
		this.destUrl = "";
		
		this.finishedBytes = 0;
		this.totalBytes = 0;
		
		this.cover = false;
		this.readFromLocal = false;
		this.writeToLocal = false;
		
		this.sendBytes = null;
		this.receiveBytes = null;
		this.sendLength = 0;
		this.receiveLength = 0;
		
		this.connection = null;
		
		this.active = false;
		this.busy = false;
		this.stop = false;
		
		this.fos = null;
		this.fis = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		this.receiveLength = 0;
		this.sendLength = 0;
	}
	public String toString() {
		return this.sourUrl + " -> " + this.destUrl;
	}
	public String output() {
		
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.sourMachine);
		c.addToBottom(this.destMachine);
		c.addToBottom(this.sourDepot);
		c.addToBottom(this.destDepot);
		
		c.addToBottom(this.input);
		c.addToBottom(this.output);
		c.addToBottom(this.sourUrl);
		c.addToBottom(this.destUrl);
		
		c.addToBottom(this.finishedBytes);
		c.addToBottom(this.totalBytes);
		
		c.addToBottom(this.cover);
		c.addToBottom(this.readFromLocal);
		c.addToBottom(this.writeToLocal);
		
		c.addToBottom(this.active);
		c.addToBottom(this.busy);
		c.addToBottom(this.stop);
		
		return c.output();
	}
	public String input(String in) {
		
		BasicModels.Config c = new BasicModels.Config(in);
		
		this.sourMachine = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.destMachine = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sourDepot = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.destDepot = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		this.input = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.output = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.sourUrl = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.destUrl = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		this.finishedBytes = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.totalBytes = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		this.cover = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.readFromLocal = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.writeToLocal = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		
		this.active = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.busy = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.stop = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		FileConnector fc = (FileConnector)o;
		this.sourMachine = fc.sourMachine;
		this.destMachine = fc.destMachine;
		this.sourDepot = fc.sourDepot;
		this.destDepot = fc.destDepot;
		
		this.input = fc.input;
		this.output = fc.output;
		this.sourUrl = fc.sourUrl;
		this.destUrl = fc.destUrl;
		
		this.finishedBytes = fc.finishedBytes;
		this.totalBytes = fc.totalBytes;
		
		this.cover = fc.cover;
		this.readFromLocal = fc.readFromLocal;
		this.writeToLocal = fc.writeToLocal;
	}
	public void copyValue(Object o) {
		FileConnector fc = (FileConnector)o;
		this.sourMachine = fc.sourMachine;
		this.destMachine = fc.destMachine;
		this.sourDepot = fc.sourDepot;
		this.destDepot = fc.destDepot;
		
		this.input = fc.input;
		this.output = fc.output;
		this.sourUrl = new String(fc.sourUrl);
		this.destUrl = new String(fc.destUrl);
		
		this.finishedBytes = fc.finishedBytes;
		this.totalBytes = fc.totalBytes;
		
		this.cover = fc.cover;
		this.readFromLocal = fc.readFromLocal;
		this.writeToLocal = fc.writeToLocal;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 我有一个容器，你有一个容器。我的容器收到了东西（Receive），要存起来，那好: <BR/>
	 * 我就保存在我的容器中等你来拿(SendBytes)。<BR/>
	 * 我收到了东西，SendLength != 0.
	 * 
	 */
	public boolean save() {
		if(this.writeToLocal) {
			try {
				if(fos == null) {
					fos = new java.io.FileOutputStream(new java.io.File(this.destUrl));
				}
				fos.write(this.receiveBytes, 0, this.receiveLength);
				fos.flush();
				this.finishedBytes += this.receiveLength;
				if(this.isFinished()) {
					fos.close();
				}
				return true;
			} catch(Exception e) {
				BasicEnums.ErrorType.WRITE_FILE_FAILED.register(e.toString());
				return false;
			}
		} 
		else {
			if(this.connection == null) {
				BasicEnums.ErrorType.UNKNOW.register("The Connection of FileConnector is NULL");
				return false;
			}
			if(!this.connection.isRunning()) {
				BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register();
				return false;
			}
			
			// wait for connection finish fetching data
			if(!Tools.Time.waitUntilFileConnectorLoad(1000, this)) {
				return false;
			}
			
			// copyData
			this.sendBytes = this.receiveBytes.clone();
			this.sendLength = this.receiveLength;
			this.finishedBytes += this.sendLength;
			return true;
		}
	}
	
	/**
	 * 我有一个容器，你有一个容器。你的容器收到了东西（Receive），那好: <BR/>
	 * 我就从你的容器中拿走你收到的东西。<BR/>
	 * 你的容器中的东西保存在 SendBytes中。<BR/>
	 * 我取走你容器的东西，那么，你的容器就空了。SendLength = 0.
	 * 
	 */
	public boolean load() {
		if(this.readFromLocal) {
			try {
				if(fis == null) {
					fis = new java.io.FileInputStream(new java.io.File(this.sourUrl));
				}
				this.sendLength = fis.read(sendBytes);
				this.finishedBytes += this.sendLength;
				if(this.isFinished()) {
					fis.close();
				}
				return true;
			} catch(Exception e) {
				BasicEnums.ErrorType.READ_FILE_FAILED.register(e.toString());
				return false;
			}
			
		}
		else {
			if(this.connection == null) {
				BasicEnums.ErrorType.UNKNOW.register("The Connection of FileConnector is NULL");
				return false;
			}
			if(!this.connection.isRunning()) {
				BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register();
				return false;
			}
			
			// wait for fill bytes
			if(!Tools.Time.waitUntilFileConnectorSave(1000, this)) {
				return false;
			}
			
			// 对方的容器
			Interfaces.IFileConnector opponentFC = this.connection.getFileConnector();
			byte[] opponentData = opponentFC.getSendBytes();
			
			// you can load now.
			if(opponentData.length != this.sendBytes.length) {
				BasicEnums.ErrorType.UNKNOW.register("The Buffer Length of Two FileConnector is not Equal");
				return false;
			}
			for(int i=0; i<this.sendBytes.length; i++) {
				this.sendBytes[i] = opponentData[i];
			}
			this.sendLength = opponentFC.getSendLength();
			this.finishedBytes += this.sendLength;
			opponentFC.setSendLength(0);
			return true;
		}
	}
	public void close() {
		
		if(fos != null) {
			try {
				fos.close();
			} catch(Exception e) {
				BasicEnums.ErrorType.UNKNOW.register(e.toString());
			}
		}
		if(fis != null) {
			try {
				fis.close();
			} catch(Exception e) {
				BasicEnums.ErrorType.UNKNOW.register(e.toString());
			}
		}
		
		initThis();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
