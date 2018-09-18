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
		this.content = null;
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
		c.addToBottom_Encode(new String(this.content));
		return c;
	}
	public String toString() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(in);
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
		c = super.input(c);
		if(!c.getIsOK()) { return c; }
		this.content = c.fetchFirstString_Decode().getBytes();
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
		String url = "";
		try {
			String path = com.FileManagerX.Tools.Pathes.TMP_0_SCREEN.getAbsolute();
			String name = com.FileManagerX.Globals.Configurations.This_MachineIndex + ".png";
			url = path + "\\" + name;
			
			java.io.FileOutputStream fos = new java.io.FileOutputStream(new java.io.File(url));
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
