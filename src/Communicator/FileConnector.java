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
	private int sendLength;
	private int receiveLength;
	
	private Interfaces.IClientConnection connection;
	
	private boolean active;
	private boolean busy;
	private boolean stop;
	
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
	
	public boolean setConnection(Interfaces.IClientConnection connection) {
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
	
	public Interfaces.IClientConnection getConnection() {
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
		this.close();
	}
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
				BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register("Connection is NULL");
				return false;
			}
			if(!this.connection.isRunning()) {
				BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register();
				return false;
			}
			
			this.connection.getFileConnector().setSendBytes(this.receiveBytes);
			return true;
		}
	}
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
			long waitTicks = 1000;
			long startTick = Tools.Time.getTicks();
			while(Tools.Time.getTicks() - startTick < waitTicks) {
				if(this.sendBytes != null) {
					break;
				}
			}
			if(this.sendBytes == null) {
				BasicEnums.ErrorType.RECEIVE_OVER_TIME.register("Too long to get Send Bytes From Other Connection");
				return false;
			}
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
