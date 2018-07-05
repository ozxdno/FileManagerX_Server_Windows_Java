package Commands;

/**
 * 
 * Commands.Input.sourMachine EQUALS_TO Replies.Input.destMachine; <BR/>
 * Commands.Input.machineIndex EQUALS_TO Replies.Input.sourMachine;
 * 
 * @author ozxdno
 *
 */
public class Input extends Comman implements Interfaces.ICommands {

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
	
	public Replies.Input getReply() {
		return (Replies.Input)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Input() {
		initThis();
	}
	public Input(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.cover = false;
		this.sourUrl = "";
		this.destUrl = "";
		this.totalBytes = 0;
		this.finishedBytes = 0;
		super.setReply(new Replies.Input());
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
		Input sf = (Input)o;
		this.cover = sf.cover;
		this.sourUrl = sf.sourUrl;
		this.destUrl = sf.destUrl;
		this.totalBytes = sf.totalBytes;
		this.finishedBytes = sf.finishedBytes;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Input sf = (Input)o;
		this.cover = sf.cover;
		this.sourUrl = new String(sf.sourUrl);
		this.destUrl = new String(sf.destUrl);
		this.totalBytes = sf.totalBytes;
		this.finishedBytes = sf.finishedBytes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight_PriorityEnough(BasicEnums.UserPriority.Member)) {
			this.reply();
			return false;
		}
		if(!this.isExistDest_MachineIndex_DepotIndex()) {
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			this.fillFileConnector(this.getConnection().getFileConnector());
			
			Replies.Input rep = this.getReply();
			rep.setSourUrl(this.sourUrl);
			rep.setDestUrl(this.destUrl);
			rep.setCover(this.cover);
			rep.setTotalBytes(this.totalBytes);
			rep.setFinishedBytes(finishedBytes);
			rep.setOK(true);
			this.reply();
			return true;
		}
		else {
			if(this.isSelfToSelf()) {
				this.reply();
				return false;
			}
			
			Interfaces.IFSWRE fswre = Factories.CommunicatorFactory.createFSWRE();
			fswre.setServerMachineIndex(this.getBasicMessagePackage().getDestMachineIndex());
			fswre.setClientMachineIndex(Globals.Configurations.This_MachineIndex);
			fswre.setUserIndex(this.getBasicMessagePackage().getSourUserIndex());
			fswre.setConnection();
			fswre.getConnection().getFileConnector().setConnection(this.getConnection());
			this.getConnection().getFileConnector().setConnection(fswre.getConnection());
			
			Replies.Input rep = (Replies.Input)fswre.execute(this.output());
			if(rep == null) {
				this.getReply().setFailedReason("Reply of FSWRE is NULL");
				this.getReply().setOK(false);
				this.reply();
				return true;
			}
			
			this.fillFileConnector(this.getConnection().getFileConnector());
			this.setReply(rep);
			this.reply();
			return false;
		}
	}
	public void reply() {
		this.setBasicMessagePackageToReply();
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
	}
	public void replyNULL() {
		BasicEnums.ErrorType.EXECUTE_COMMAND_FAILED.register("The Reply from other Connection is NULL");
		this.getReply().setOK(false);
		this.getReply().setFailedReason("The Reply from other Connection is NULL");
		this.reply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void fillFileConnector(Interfaces.IFileConnector fc) {
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
		fc.setIsReadFromLocal(this.isArriveTargetMachine());
		fc.setIsWriteToLocal(this.isArriveTargetMachine());
		
		fc.setState_Active(true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
