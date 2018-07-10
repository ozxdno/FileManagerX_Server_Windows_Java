package Replies;

public class UpdateFolder extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.Folder folder;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setFolder(BasicModels.Folder folder) {
		if(folder == null) {
			return false;
		}
		this.folder = folder;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.Folder getFolder() {
		return this.folder;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateFolder() {
		initThis();
	}
	private void initThis() {
		this.folder = new BasicModels.Folder();
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
		c.addToBottom(new BasicModels.Config(this.folder.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.folder.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateFolder qf = (UpdateFolder)o;
		this.folder = qf.folder;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateFolder qf = (UpdateFolder)o;
		this.folder.copyValue(qf.folder);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
