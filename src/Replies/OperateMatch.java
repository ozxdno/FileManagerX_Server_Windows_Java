package Replies;

public class OperateMatch extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int amount;
	private BasicModels.BaseFile file;
	private BasicCollections.BaseFiles files;
	private Match.Match match;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setFile(BasicModels.BaseFile file) {
		if(file == null) {
			return false;
		}
		this.file = file;
		return true;
	}
	public boolean setFiles(BasicCollections.BaseFiles files) {
		if(files == null) {
			return false;
		}
		this.files = files;
		return true;
	}
	public boolean setOperateMatch(Match.Match match) {
		if(match == null) {
			return false;
		}
		this.match = match;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getAmount() {
		return this.amount;
	}
	public BasicModels.BaseFile getFile() {
		return this.file;
	}
	public BasicCollections.BaseFiles getFiles() {
		return this.files;
	}
	public Match.Match getOperateMatch() {
		return this.match;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public OperateMatch() {
		initThis();
	}
	private void initThis() {
		this.amount = 0;
		this.file = new BasicModels.BaseFile();
		this.files = new BasicCollections.BaseFiles();
		this.match = new Match.Match();
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
		c.addToBottom(this.amount);
		c.addToBottom(new BasicModels.Config(this.file.output()));
		c.addToBottom(new BasicModels.Config(this.match.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		in = this.file.input(c.output());
		if(in == null) {
			return null;
		}
		
		return this.match.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		OperateMatch qf = (OperateMatch)o;
		this.amount = qf.amount;
		this.file = qf.file;
		this.files = qf.files;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		OperateMatch qf = (OperateMatch)o;
		this.amount = qf.amount;
		this.file.copyValue(qf.file);
		this.files.copyValue(qf.files);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(this.files.size() >= this.amount) {
			return true;
		}
		
		BasicModels.BaseFile newFile = new BasicModels.BaseFile();
		newFile.copyValue(this.file);
		this.files.add(newFile);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
