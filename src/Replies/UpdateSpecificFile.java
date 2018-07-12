package Replies;

public class UpdateSpecificFile extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.BaseFile file;
	private BasicEnums.FileType type;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setFile(BasicModels.BaseFile file) {
		if(file == null) {
			return false;
		}
		this.file = file;
		return true;
	}
	public boolean setType(BasicEnums.FileType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.BaseFile getFile() {
		return this.file;
	}
	public BasicEnums.FileType getType() {
		return this.type;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateSpecificFile() {
		initThis();
	}
	private void initThis() {
		this.file = new BasicModels.BaseFile();
		this.type = BasicEnums.FileType.Unsupport;
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
		c.addToBottom(new BasicModels.Config(this.file.output()));
		c.addToBottom(this.type.toString());
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		in = this.file.input(in);
		if(in == null) {
			return null;
		}
		BasicModels.Config c = new BasicModels.Config(in);
		try {
			this.type = BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			return c.output();
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateSpecificFile qf = (UpdateSpecificFile)o;
		this.file = qf.file;
		this.type = qf.type;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateSpecificFile qf = (UpdateSpecificFile)o;
		this.file.copyValue(qf.file);
		this.type = qf.type;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
