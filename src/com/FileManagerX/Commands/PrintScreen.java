package com.FileManagerX.Commands;

public class PrintScreen extends BaseCommand {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_SAVE = "Save to Local Failed";
	public final static String FAILED_READ = "Read from Local Failed";
	
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
	public com.FileManagerX.Replies.PrintScreen getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.PrintScreen()); }
		return (com.FileManagerX.Replies.PrintScreen)super.getReply();
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
		if(o instanceof BaseCommand) {
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
		if(o instanceof BaseCommand) {
			super.copyValue(o);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	public boolean executeInLocal() {
		java.util.List<Byte> bytes = new java.util.ArrayList<>();
		byte[] buffer = new byte[1024];
		String url = "";
		
		try {
			java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); // Size of Screen
			java.awt.Rectangle cutRect = new java.awt.Rectangle(screenSize); // Range of PrintScreen
			java.awt.Robot robot = new java.awt.Robot();
			java.awt.image.BufferedImage image = robot.createScreenCapture(cutRect);
			
			long machine = com.FileManagerX.Globals.Configurations.This_MachineIndex;
			String path = com.FileManagerX.Tools.Pathes.getTMP_ScreenI(machine).getAbsolute();
			String name = com.FileManagerX.Globals.Configurations.This_MachineIndex + ".png";
			url = path + "\\" + name;
			javax.imageio.ImageIO.write(image, "png", new java.io.File(url));
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			this.getReply().setFailedReason(FAILED_SAVE);
			this.getReply().setOK(false);
			return false;
		}
		
		try {
			java.io.FileInputStream fis = new java.io.FileInputStream(new java.io.File(url));
			while(true) {
				int len = fis.read(buffer);
				if(len < 0) { break; }
				for(int i=0; i<len; i++) {
					bytes.add(buffer[i]);
				}
			}
			
			this.content = com.FileManagerX.Tools.List2Array.toByteArray(bytes);
			fis.close();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			this.getReply().setFailedReason(FAILED_READ);
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().setContent(this.content);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
