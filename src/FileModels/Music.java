package FileModels;

public class Music extends BasicModels.BaseFile implements Interfaces.IPublic {

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
	public Music(String url) {
		super(url);
		initThis();
	}
	public Music(java.io.File file) {
		super(file);
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
		return "[" + this.getName() + "] " + Tools.Time.ticks2String(lasting, "mm:ss");
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		//c.addToBottom(new BasicModels.Config(super.output()));
		c.addToBottom(this.author);
		c.addToBottom(this.singer);
		c.addToBottom(this.album);
		c.addToBottom(this.lasting);
		return c.output();
	}
	public String input(String in) {
		//in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.author = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.singer = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.album = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.lasting = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Music p = (Music)o;
		this.author = p.author;
		this.singer = p.singer;
		this.album = p.album;
		this.lasting = p.lasting;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Music p = (Music)o;
		this.author = new String(p.author);
		this.singer = new String(p.singer);
		this.album = new String(p.album);
		this.lasting = p.lasting;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean load() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
