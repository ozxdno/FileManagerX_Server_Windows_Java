package Replies;

public class UpdateFile extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.BaseFile file;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setFile(BasicModels.BaseFile file) {
		if(file == null) {
			return false;
		}
		this.file = file;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.BaseFile getFile() {
		return this.file;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateFile() {
		initThis();
	}
	private void initThis() {
		this.file = new BasicModels.BaseFile();
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
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.file.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateFile qf = (UpdateFile)o;
		this.file = qf.file;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateFile qf = (UpdateFile)o;
		this.file.copyValue(qf.file);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
