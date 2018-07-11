package DepotManager;

public class Operator extends Thread implements Interfaces.IPublic, Interfaces.IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BasicEnums.OperateType type;
	private long index;
	
	private long depotIndex;
	private String sourUrl;
	private String destUrl;
	private boolean uncheck;
	
	private long bgTicks;
	private long edTicks;
	
	private boolean successed;
	private boolean started;
	private boolean finished;
	private boolean running;
	private boolean abort;
	private boolean stop;
	
	private String reason;
	
	private BasicModels.DepotInfo depotInfo;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(BasicEnums.OperateType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		if(Globals.Datas.Operators != null) {
			this.index = Globals.Datas.Operators.getNextIndex() + 1;
			Globals.Datas.Operators.setNextIndex(this.index);
		}
		return true;
	}
	
	public boolean setDepotIndex(long depotIndex) {
		if(depotIndex < 0) {
			return false;
		}
		this.depotIndex = depotIndex;
		return true;
	}
	public boolean setSourUrl(String url) {
		if(url == null) {
			return false;
		}
		if(url.length() == 0) {
			return false;
		}
		this.sourUrl = url;
		return true;
	}
	public boolean setDestUrl(String url) {
		if(url == null) {
			return false;
		}
		if(url.length() == 0) {
			return false;
		}
		this.destUrl = url;
		return true;
	}
	public boolean setUncheck(boolean uncheck) {
		this.uncheck = uncheck;
		return true;
	}
	
	public boolean setBeginTicks(long ticks) {
		if(ticks < 0) {
			return false;
		}
		this.bgTicks = ticks;
		return true;
	}
	public boolean setBeginTicks() {
		this.bgTicks = Tools.Time.getTicks();
		return true;
	}
	public boolean setEndTicks(long ticks) {
		if(ticks < 0) {
			return false;
		}
		this.edTicks = ticks;
		return true;
	}
	public boolean setEndTicks() {
		this.edTicks = Tools.Time.getTicks();
		return true;
	}
	
	public boolean setSuccessed(boolean successed) {
		this.successed = successed;
		return true;
	}
	public boolean setStarted(boolean started) {
		this.started = started;
		return true;
	}
	public boolean setFinished(boolean finished) {
		this.finished = finished;
		return true;
	}
	public boolean setRunning(boolean running) {
		this.running = running;
		return true;
	}
	public boolean setAbort(boolean abort) {
		this.abort = abort;
		return true;
	}
	public boolean setStop(boolean stop) {
		this.stop = stop;
		return true;
	}
	
	public boolean setReason(String reason) {
		if(reason == null) {
			return false;
		}
		this.reason = reason;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicEnums.OperateType getType() {
		return this.type;
	}
	public long getIndex() {
		return this.index;
	}
	
	public long getDepotIndex() {
		return this.depotIndex;
	}
	public String getSourUrl() {
		return this.sourUrl;
	}
	public String getDestUrl() {
		return this.destUrl;
	}
	public boolean isUncheck() {
		return this.uncheck;
	}
	
	public long getBeginTicks() {
		return this.bgTicks;
	}
	public long getEndTicks() {
		return this.edTicks;
	}
	
	public boolean isSuccessed() {
		return this.successed;
	}
	public boolean isStarted() {
		return this.started;
	}
	public boolean isFinished() {
		return this.finished;
	}
	public boolean isRunning() {
		return this.running;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isStop() {
		return this.stop;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Operator() {
		initThis();
	}
	private void initThis() {
		
		type = BasicEnums.OperateType.IDLE;
		index = 0;
		
		depotIndex = 0;
		sourUrl = "";
		destUrl = "";
		uncheck = false;
		
		bgTicks = 0;
		edTicks = 0;
		
		successed = false;
		started = false;
		this.finished = false;
		this.running = false;
		this.abort = false;
		this.stop = false;
		
		reason = "";
	}
	
	public void run() {
		
		// to collections
		if(Globals.Datas.Operators != null) {
			this.setIndex();
			Globals.Datas.Operators.add(this);
		}
		
		// start
		this.setBeginTicks();
		this.successed = false;
		this.started = true;
		this.finished = false;
		this.running = true;
		this.abort = false;
		this.stop = false;
		
		// operate
		boolean operateOK = this.operate();
		
		// end
		this.setEndTicks();
		this.finished = true;
		this.running = false;
		this.successed = operateOK;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "Operator: " + this.type.toString();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.type.toString());
		c.addToBottom(this.index);
		c.addToBottom(this.depotIndex);
		c.addToBottom(this.sourUrl);
		c.addToBottom(this.destUrl);
		c.addToBottom(this.uncheck);
		c.addToBottom(this.bgTicks);
		c.addToBottom(this.edTicks);
		c.addToBottom(this.successed);
		c.addToBottom(this.started);
		c.addToBottom(this.finished);
		c.addToBottom(this.running);
		c.addToBottom(this.abort);
		c.addToBottom(this.stop);
		c.addToBottom(this.reason);
		
		return c.output();
	}
	public String input(String in) {
		try {
			BasicModels.Config c = new BasicModels.Config(in);
			
			this.type = BasicEnums.OperateType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.depotIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.sourUrl = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.destUrl = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.uncheck = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.bgTicks = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.edTicks = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.successed = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.started = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.finished = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.running = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.abort = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.stop = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.reason = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			
			return c.output();
			
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		Operator op = (Operator)o;
		this.type = op.type;
		this.index = op.index;
		this.depotIndex = op.depotIndex;
		this.sourUrl = op.sourUrl;
		this.destUrl = op.destUrl;
		this.uncheck = op.uncheck;
		this.bgTicks = op.bgTicks;
		this.edTicks = op.edTicks;
		this.successed = op.successed;
		this.started = op.started;
		this.finished = op.finished;
		this.running = op.running;
		this.abort = op.abort;
		this.stop = op.stop;
		this.reason = op.reason;
	}
	public void copyValue(Object o) {
		Operator op = (Operator)o;
		this.type = op.type;
		this.index = op.index;
		this.depotIndex = op.depotIndex;
		this.sourUrl = new String(op.sourUrl);
		this.destUrl = new String(op.destUrl);
		this.uncheck = op.uncheck;
		this.bgTicks = op.bgTicks;
		this.edTicks = op.edTicks;
		this.successed = op.successed;
		this.started = op.started;
		this.finished = op.finished;
		this.running = op.running;
		this.abort = op.abort;
		this.stop = op.stop;
		this.reason = new String(op.reason);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return true;
	}
	
	public boolean startProcess() {
		if(this.finished || (!this.finished && !this.stop && !this.running)) {
			this.setName("Operator: " + type.toString());
			this.start();
			return true;
		}
		if(this.stop) {
			return this.continueProcess();
		}
		if(this.running) {
			return true;
		}
		
		return true;
	}
	public boolean stopProcess() {
		this.stop = true;
		return true;
	}
	public boolean continueProcess() {
		this.stop = false;
		return true;
	}
	public boolean restartProcess() {
		this.abort = true;
		while(this.running);
		return this.startProcess();
	}
	public boolean exitProcess() {
		this.abort = true;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean operate() {
		// depot
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm != null) {
			this.depotInfo = dbm.getDBInfo().getDepotInfo();
		}
		else {
			if(this.depotIndex == Globals.Datas.DBManager.getDBInfo().getDepotIndex()) {
				this.depotInfo = Globals.Datas.DBManager.getDBInfo().getDepotInfo();
			}
			else {
				this.depotInfo = new BasicModels.DepotInfo();
			}
		}
		
		// manager
		Interfaces.IDepotManager dm = this.depotInfo.getManager();
		dm.setUncheck(uncheck);
		
		if(this.type.equals(BasicEnums.OperateType.CHECK_SERVER)) {
			return Globals.Datas.DBManager.getServerChecker().check();
		}
		if(this.type.equals(BasicEnums.OperateType.CHECK_INDEX)) {
			return Globals.Datas.DBManager.getServerChecker().checkIndex();
		}
		
		if(this.type.equals(BasicEnums.OperateType.CHECK_DEPOT)) {
			if(dbm == null) {
				return false;
			}
			else {
				return dbm.getChecker().check();
			}
		}
		if(this.type.equals(BasicEnums.OperateType.CHECK_FOLDERS_FILES)) {
			if(dbm == null) {
				return false;
			}
			else {
				return dbm.getChecker().checkFoldersAndFiles();
			}
		}
		
		if(this.type.equals(BasicEnums.OperateType.OPEN_IN_SYSTEM)) {
			return dm.openInSystem(destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.PRINT_SCREEN)) {
			return dm.printScreen();
		}
		
		if(this.type.equals(BasicEnums.OperateType.COPY_FOLDER)) {
			return dm.copyDirectory(sourUrl, destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.COPY_FILE)) {
			return dm.copyFile(sourUrl, destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.CREATE_FOLDER)) {
			return dm.createFolder(destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.CREATE_FILE)) {
			return dm.createFile(destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.DELETE_FOLDER)) {
			return dm.deleteDirectory(destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.DELETE_FILE)) {
			return dm.deleteFile(destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.DELETE_CONTENT)) {
			return dm.deleteContent(destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.MOVE_FOLDER)) {
			return dm.moveDirectory(sourUrl, destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.MOVE_FILE)) {
			return dm.moveFile(sourUrl, destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.RENAME_FOLDER)) {
			return dm.renameDirectory(sourUrl, destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.RENAME_FILE)) {
			return dm.renameFile(sourUrl, destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.RENAME_FOLDER_WITHOUT_EXTENSION)) {
			return dm.renameDirectory(sourUrl, destUrl);
		}
		if(this.type.equals(BasicEnums.OperateType.RENAME_FILE_WITHOUT_EXTENSION)) {
			return dm.renameFileWithoutExtension(sourUrl, destUrl);
		}
		
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
