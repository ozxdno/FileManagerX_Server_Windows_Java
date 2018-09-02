package com.FileManagerX.FileModels;

public class Music extends com.FileManagerX.BasicModels.File {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String author;
	private String singer;
	private String album;
	private long lasting;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAuthor(String author) {
		if(author == null) {
			return false;
		}
		this.author = author;
		return true;
	}
	public boolean setSinger(String singer) {
		if(singer == null) {
			return false;
		}
		this.singer = singer;
		return true;
	}
	public boolean setAlbum(String album) {
		if(album == null) {
			return false;
		}
		this.album = album;
		return true;
	}
	public boolean setLasting(long lasting) {
		if(lasting < 0) {
			return false;
		}
		this.lasting = lasting;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getAuthor() {
		return this.author;
	}
	public String getSinger() {
		return this.singer;
	}
	public String getAlbum() {
		return this.album;
	}
	public long getLasting() {
		return this.lasting;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Music() {
		initThis();
	}
	private void initThis() {
		this.author = "";
		this.album = "";
		this.singer = "";
		this.lasting = 0;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "[" + this.getName() + "] " + com.FileManagerX.Tools.Time.ticks2String(lasting, "mm:ss");
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.author);
		c.addToBottom(this.singer);
		c.addToBottom(this.album);
		c.addToBottom(this.lasting);
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
		this.author = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.singer = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.album = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		this.lasting = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		
		return c;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof Music) {
			super.copyReference(o);
			Music p = (Music)o;
			this.author = p.author;
			this.singer = p.singer;
			this.album = p.album;
			this.lasting = p.lasting;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof Music) {
			super.copyValue(o);
			Music p = (Music)o;
			this.author = p.author;
			this.singer = p.singer;
			this.album = p.album;
			this.lasting = p.lasting;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyValue(o);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean load() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
