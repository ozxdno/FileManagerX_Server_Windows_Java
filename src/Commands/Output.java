package Commands;

public class Output extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long sourMachine; // destMachine is in machineIndex
	private long sourDepot;
	
	private boolean cover;
	private String sourUrl;
	private String destUrl;
	private long totalBytes;
	private long finishedBytes;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setSourMachine(long index) {
		if(index < 0) {
			return false;
		}
		this.sourMachine = index;
		return true;
	}
	public boolean setSourDepot(long index) {
		if(index < 0) {
			return false;
		}
		this.sourDepot = index;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getSourMachine() {
		return this.sourMachine;
	}
	public long getSourDepot() {
		return this.sourDepot;
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
	
	public Replies.Output getReply() {
		return (Replies.Output)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Output() {
		initThis();
	}
	public Output(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.sourMachine = Globals.Configurations.This_MachineIndex;
		this.sourDepot = 0;
		this.cover = false;
		this.sourUrl = "";
		this.destUrl = "";
		this.totalBytes = 0;
		this.finishedBytes = 0;
		super.setReply(new Replies.Output());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.getUserIndex());
		c.addToBottom(this.getPassword());
		c.addToBottom(this.getMachineIndex());
		c.addToBottom(this.getDepotIndex());
		c.addToBottom(this.getDataBaseIndex());
		c.addToBottom(this.sourMachine);
		c.addToBottom(this.sourDepot);
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
		this.sourMachine = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sourDepot = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
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
		Output sf = (Output)o;
		this.sourMachine = sf.sourMachine;
		this.sourDepot = sf.sourDepot;
		this.cover = sf.cover;
		this.sourUrl = sf.sourUrl;
		this.destUrl = sf.destUrl;
		this.totalBytes = sf.totalBytes;
		this.finishedBytes = sf.finishedBytes;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Output sf = (Output)o;
		this.sourMachine = sf.sourMachine;
		this.sourDepot = sf.sourDepot;
		this.cover = sf.cover;
		this.sourUrl = new String(sf.sourUrl);
		this.destUrl = new String(sf.destUrl);
		this.totalBytes = sf.totalBytes;
		this.finishedBytes = sf.finishedBytes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		
		if(!this.isConnected()) {
			this.reply();
			return false;
		}
		if(!this.isLogin()) {
			this.reply();
			return false;
		}
		if(!this.isUserIndexRight()) {
			this.reply();
			return false;
		}
		if(!this.isPasswordRight()) {
			this.reply();
			return false;
		}
		if(!this.isMechineIndexRight()) {
			this.reply();
			return false;
		}
		if(!this.isDepotIndexRight()) {
			this.reply();
			return false;
		}
		
		boolean ok = this.getMachineIndex() == Globals.Configurations.This_MachineIndex ?
				this.executeInLocal() :
				this.excuteInRemote();
		
		Replies.Output rep = this.getReply();
		rep.setSourMachine(this.sourMachine);
		rep.setSourDepot(this.sourDepot);
		rep.setDestMachine(this.getMachineIndex());
		rep.setDestDepot(this.getDepotIndex());
		rep.setSourUrl(this.sourUrl);
		rep.setDestUrl(this.destUrl);
		rep.setCover(this.cover);
		rep.setTotalBytes(this.totalBytes);
		rep.setFinishedBytes(finishedBytes);
		rep.setOK(ok);
		this.reply();
		return true;
	}
	public void reply() {
		this.setUserIndexAndPassword();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean executeInLocal() {
		Interfaces.IFileConnector fc = this.getConnection().getFileConnector();
		fc.setSourMachine(this.sourMachine);
		fc.setDestMachine(this.getMachineIndex());
		fc.setSourDepot(this.sourDepot);
		fc.setDestDepot(this.getDepotIndex());
		
		fc.setIsOutputCommand(true);
		fc.setSourUrl(this.sourUrl);
		fc.setDestUrl(this.destUrl);
		
		fc.setTotalBytes(totalBytes);
		fc.setFinishedBytes(finishedBytes);
		fc.setIsCoverExistedFile(cover);
		fc.setState_Active(true);
		fc.setIsWriteToLocal(this.getMachineIndex() == Globals.Configurations.This_MachineIndex);
		
		return true;
	}
	private boolean excuteInRemote() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
