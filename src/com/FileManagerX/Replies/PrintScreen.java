package com.FileManagerX.Replies;

public class PrintScreen extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private byte[] content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setContent(byte[] content) {
		if(content == null) { return false; }
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public byte[] getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public PrintScreen() {
		this.initThis();
	}
	private void initThis() {
		this.content = new byte[0];
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		this.initThis();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.content);
		return c;
	}
	public String toString() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
		c = super.input(c);
		if(!c.getIsOK()) { return c; }
		this.content = c.fetchFirstBytes();
		if(!c.getIsOK()) { return c; }
		return c;
	}
	public void copyReference(Object o) {
		if(o instanceof PrintScreen) {
			super.copyReference(o);
			PrintScreen ps = (PrintScreen)o;
			this.content = ps.content;
			return;
		}
		if(o instanceof BaseReply) {
			super.copyReference(o);
		}
	}
	public void copyValue(Object o) {
		if(o instanceof PrintScreen) {
			super.copyValue(o);
			PrintScreen ps = (PrintScreen)o;
			this.content = ps.content.clone();
			return;
		}
		if(o instanceof BaseReply) {
			super.copyValue(o);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		try {
			long machine = com.FileManagerX.Globals.Configurations.This_MachineIndex;
			com.FileManagerX.Tools.Pathes.URL url = com.FileManagerX.Tools.Pathes.getTMP_ScreenI(machine);
			
			String path = url.getAbsolute();
			String name = this.getBasicMessagePackage().getSourMachineIndex() + ".png";
			String file = path + "\\" + name;
			
			java.io.FileOutputStream fos = new java.io.FileOutputStream(new java.io.File(file));
			fos.write(this.content);
			fos.close();
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
