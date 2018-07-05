package Replies;

public class Input extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean cover;
	private String sourUrl;
	private String destUrl;
	private long totalBytes;
	private long finishedBytes;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Input() {
		initThis();
	}
	private void initThis() {
		cover = false;
		this.sourUrl = "";
		this.destUrl = "";
		totalBytes = 0;
		this.finishedBytes = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new BasicModels.Config(super.output()));
		c.addToBottom(this.cover);
		c.addToBottom(this.sourUrl);
		c.addToBottom(this.destUrl);
		c.addToBottom(this.totalBytes);
		c.addToBottom(this.finishedBytes);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.cover = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.sourUrl = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.destUrl = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.totalBytes = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.finishedBytes = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Input op = (Input)o;
		this.cover = op.cover;
		this.sourUrl = op.sourUrl;
		this.destUrl = op.destUrl;
		this.totalBytes = op.totalBytes;
		this.finishedBytes = op.finishedBytes;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Input op = (Input)o;
		this.cover = op.cover;
		this.sourUrl = new String(op.sourUrl);
		this.destUrl = new String(op.destUrl);
		this.totalBytes = op.totalBytes;
		this.finishedBytes = op.finishedBytes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(connection == null) {
			return false;
		}
		
		Interfaces.IFileConnector fc = connection.getFileConnector();
		fc.setSourMachine(this.getBasicMessagePackage().getSourMachineIndex());
		fc.setDestMachine(this.getBasicMessagePackage().getDestMachineIndex());
		fc.setSourDepot(this.getBasicMessagePackage().getSourDepotIndex());
		fc.setDestDepot(this.getBasicMessagePackage().getDestDepotIndex());
		fc.setIsInputCommand(true);
		fc.setSourUrl(this.sourUrl);
		fc.setDestUrl(this.destUrl);
		fc.setTotalBytes(totalBytes);
		fc.setFinishedBytes(finishedBytes);
		fc.setIsCoverExistedFile(cover);
		fc.setIsReadFromLocal(this.isArriveSourMachine());
		fc.setIsWriteToLocal(this.isArriveSourMachine());
		fc.setState_Active(true);
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
