package BasicModels;

public class Error implements Interfaces.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicEnums.ErrorType type;
	private long time;
	private BasicEnums.ErrorLevel level;
	private String reason;
	private String tip;
	private String detail;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicEnums.ErrorType getType() {
		return type;
	}
	public long getTime() {
		return time;
	}
	public String getLongTime() {
		return Tools.Time.ticks2LongTime(time);
	}
	public String getShortTime_Date() {
		return Tools.Time.ticks2ShortTime_Date(time);
	}
	public String getShortTime_Second() {
		return Tools.Time.ticks2ShortTime_Second(time);
	}
	public BasicEnums.ErrorLevel getLevel() {
		return level;
	}
	public String getReason() {
		return reason;
	}
	public String getTip() {
		return tip;
	}
	public String getDetail() {
		return detail;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(BasicEnums.ErrorType type) {
		this.type = type;
		return true;
	}
	public boolean setTime(long time) {
		this.time = time;
		return true;
	}
	public boolean setTime() {
		this.time = Tools.Time.getTicks();
		return true;
	}
	public boolean setLevel(BasicEnums.ErrorLevel level) {
		this.level = level;
		return true;
	}
	public boolean setReason(String reason) {
		if(reason == null) {
			return false;
		}
		this.reason = reason;
		return true;
	}
	public boolean setTip(String tip) {
		if(tip == null) {
			return false;
		}
		this.tip = tip;
		return true;
	}
	public boolean setDetail(String detail) {
		if(detail == null) {
			return false;
		}
		this.detail = detail;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Error() {
		initThis();
	}
	public Error(BasicEnums.ErrorType type) {
		initThis();
		this.setType(type);
		this.setTime();
	}
	private void initThis() {
		type = BasicEnums.ErrorType.NORMAL;
		time = Tools.Time.getTicks();
		level = BasicEnums.ErrorLevel.Normal;
		reason = "";
		tip = "";
		detail = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "[" + this.getLongTime() + "] " + type.toString() + ": " + reason;
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getShortTime_Second());
		c.addToBottom(type.toString());
		c.addToBottom(time);
		c.addToBottom(level.toString());
		c.addToBottom(reason);
		c.addToBottom(tip);
		c.addToBottom(detail);
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		type = BasicEnums.ErrorType.valueOf(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		time = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		level = BasicEnums.ErrorLevel.valueOf(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		reason = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		tip = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		detail = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
		Error e = (Error)o;
		type = e.type;
		time = e.time;
		level = e.level;
		reason = e.reason;
		tip = e.tip;
		detail = e.detail;
	}
	public void copyValue(Object o) {
		Error e = (Error)o;
		type = e.type;
		time = e.time;
		level = e.level;
		reason = new String(e.reason);
		tip = new String(e.tip);
		detail = new String(e.detail);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void print() {
		String time = this.getLongTime();
		String type = this.type.toString();
		String level = this.level.toString();
		System.out.println(time + "|" + type + "|" + level + "|" + tip + "|" + detail);
	}
	public void register() {
		Globals.Datas.Errors.add(this);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
