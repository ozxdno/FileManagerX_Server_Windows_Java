package Match;

/**
 * 封装了一个文件匹配的线程，用于一次文件匹配操作
 * 
 * @author ozxdno
 *
 */
public class Match extends Thread implements Interfaces.IPublic, Interfaces.IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BasicEnums.FileType type;
	private long index;
	
	private String matchArgs;
	private Interfaces.IMatcher matcher;
	
	private long bgTicks;
	private long edTicks;
	
	private boolean successed;
	private boolean started;
	private boolean finished;
	private boolean running;
	private boolean abort;
	private boolean stop;
	
	private String reason;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(BasicEnums.FileType type) {
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
		if(Globals.Datas.Matches != null) {
			this.index = Globals.Datas.Matches.getNextIndex() + 1;
			Globals.Datas.Matches.setNextIndex(this.index);
		}
		return true;
	}
	
	public boolean setMatchArgs(String args) {
		this.matchArgs = args;
		return true;
	}
	public boolean setMatcher(Interfaces.IMatcher matcher) {
		if(matcher == null) {
			return false;
		}
		this.matcher = matcher;
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

	public BasicEnums.FileType getType() {
		return this.type;
	}
	public long getIndex() {
		return this.index;
	}
	
	public String getMatchArgs() {
		return this.matchArgs;
	}
	public Interfaces.IMatcher getMatcher() {
		return this.matcher;
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
	
	public Match() {
		initThis();
	}
	private void initThis() {
		
		type = BasicEnums.FileType.Unsupport;
		index = 0;
		
		matchArgs = "";
		matcher= null;
		
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
		if(Globals.Datas.Matches != null) {
			this.setIndex();
			Globals.Datas.Matches.add(this);
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
		return "Match: " + this.type.toString();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.type.toString());
		c.addToBottom(this.index);
		c.addToBottom(this.matchArgs);
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
			
			this.type = BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.matchArgs = c.fetchFirstString();
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
		Match op = (Match)o;
		this.type = op.type;
		this.index = op.index;
		this.matchArgs = op.matchArgs;
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
		Match op = (Match)o;
		this.type = op.type;
		this.index = op.index;
		this.matchArgs = op.matchArgs;
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
			this.setName("Match: " + type.toString());
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
		if(type.equals(BasicEnums.FileType.Picture)) {
			matcher = Factories.MatchFactory.createPictureMacther();
			matcher.setArgs(matchArgs);
		}
		
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
