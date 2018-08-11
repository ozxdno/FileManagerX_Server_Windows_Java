package com.FileManagerX.FileModels;

public class Video extends com.FileManagerX.BasicModels.BaseFile {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int height;
	private int width;
	private long lasting;
	
	private int[] rowPixels;
	private int[] colPixels;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setHeight(int height) {
		if(height < 0) {
			return false;
		}
		this.height = height;
		return true;
	}
	public boolean setWidth(int width) {
		if(width < 0) {
			return false;
		}
		this.width = width;
		return true;
	}
	public boolean setLasting(long lasting) {
		if(lasting < 0) {
			return false;
		}
		this.lasting = lasting;
		return true;
	}
	public boolean setRowPixels(int[] rowPixels) {
		if(rowPixels == null) {
			return false;
		}
		if(rowPixels.length != com.FileManagerX.Globals.Extras.MatchPicture_PixelAmount) {
			return false;
		}
		this.rowPixels = rowPixels;
		return true;
	}
	public boolean setColPixels(int[] colPixels) {
		if(colPixels == null) {
			return false;
		}
		if(colPixels.length != com.FileManagerX.Globals.Extras.MatchPicture_PixelAmount) {
			return false;
		}
		this.colPixels = colPixels;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getHeight() {
		return this.height;
	}
	public int getWidth() {
		return this.width;
	}
	public long getLasting() {
		return this.lasting;
	}
	public int[] getRowPixels() {
		return this.rowPixels;
	}
	public int[] getColPixels() {
		return this.colPixels;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Video() {
		initThis();
	}
	public Video(String url) {
		super(url);
		initThis();
	}
	public Video(java.io.File file) {
		super(file);
		initThis();
	}
	
	private void initThis() {
		this.height = 0;
		this.width = 0;
		this.rowPixels = new int[com.FileManagerX.Globals.Extras.MatchPicture_PixelAmount];
		this.colPixels = new int[com.FileManagerX.Globals.Extras.MatchPicture_PixelAmount];
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "[" + this.getName() + "] " + width + "X" + height + " " + com.FileManagerX.Tools.Time.ticks2String(lasting, "HH:mm:ss");
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		//c.addToBottom(new com.FileManagerX.BasicModels.Config(super.output()));
		c.addToBottom(this.height);
		c.addToBottom(this.width);
		c.addToBottom(this.lasting);
		c.addToBottom(com.FileManagerX.Tools.String.link(rowPixels, " "));
		c.addToBottom(com.FileManagerX.Tools.String.link(colPixels, " "));
		return c.output();
	}
	public String input(String in) {
		//in = super.input(in);
		if(in == null) {
			return null;
		}
		
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
		this.height = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		this.width = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		this.lasting = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.rowPixels = com.FileManagerX.Tools.String.splitToIntArray(c.fetchFirstString(), ' ');
		if(!c.getIsOK()) { return null; }
		this.colPixels = com.FileManagerX.Tools.String.splitToIntArray(c.fetchFirstString(), ' ');
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Video p = (Video)o;
		this.height = p.height;
		this.width = p.width;
		this.lasting = p.lasting;
		this.rowPixels = p.rowPixels;
		this.colPixels = p.colPixels;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Video p = (Video)o;
		this.height = p.height;
		this.width = p.width;
		this.lasting = p.lasting;
		this.rowPixels = p.rowPixels.clone();
		this.colPixels = p.colPixels.clone();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean load() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}