package Replies;

public class RemoveFolders extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int amount;
	private BasicModels.Folder folder;
	private BasicCollections.Folders folders;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setFolder(BasicModels.Folder folder) {
		if(folder == null) {
			return false;
		}
		this.folder = folder;
		return true;
	}
	public boolean setFolders(BasicCollections.Folders folders) {
		if(folders == null) {
			return false;
		}
		this.folders = folders;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getAmount() {
		return this.amount;
	}
	public BasicModels.Folder getFolder() {
		return this.folder;
	}
	public BasicCollections.Folders getFolders() {
		return this.folders;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RemoveFolders() {
		initThis();
	}
	private void initThis() {
		this.amount = 0;
		this.folder = new BasicModels.Folder();
		this.folders = new BasicCollections.Folders();
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
		c.addToBottom(new BasicModels.Config(this.folder.output()));
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
		
		return this.folder.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		RemoveFolders qf = (RemoveFolders)o;
		this.amount = qf.amount;
		this.folder = qf.folder;
		this.folders = qf.folders;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		RemoveFolders qf = (RemoveFolders)o;
		this.amount = qf.amount;
		this.folder.copyValue(qf.folder);
		this.folders.copyValue(qf.folders);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(this.folders.size() >= this.amount) {
			return true;
		}
		
		BasicModels.Folder newFolder = new BasicModels.Folder();
		newFolder.copyValue(this.folder);
		this.folders.add(newFolder);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
