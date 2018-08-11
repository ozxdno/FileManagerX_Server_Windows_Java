package com.FileManagerX.FileModels;

public class Gif extends com.FileManagerX.BasicModels.BaseFile {

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
	
	public Gif() {
		initThis();
	}
	public Gif(String url) {
		super(url);
		initThis();
	}
	public Gif(java.io.File file) {
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
		return "[" + this.getName() + "] " + width + "X" + height + " " + com.FileManagerX.Tools.Time.ticks2String(lasting, "mm:ss");
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
		if(o instanceof Gif) {
			super.copyReference(o);
			Gif p = (Gif)o;
			this.height = p.height;
			this.width = p.width;
			this.lasting = p.lasting;
			this.rowPixels = p.rowPixels;
			this.colPixels = p.colPixels;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.BaseFile) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof Gif) {
			super.copyValue(o);
			Gif p = (Gif)o;
			this.height = p.height;
			this.width = p.width;
			this.lasting = p.lasting;
			this.rowPixels = p.rowPixels.clone();
			this.colPixels = p.colPixels.clone();
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.BaseFile) {
			super.copyReference(o);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean load() {
		
		try {
			com.FileManagerX.Tools.Gif.GifDecoder gd = new com.FileManagerX.Tools.Gif.GifDecoder();
			int status = gd.read(this.getUrl());
			if(status != com.FileManagerX.Tools.Gif.GifDecoder.STATUS_OK) {
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register("Read Gif Failed, STATUS: " + status, this.getUrl());
				return false;
			}
			
			this.lasting = 0;
			for(int i=0; i<gd.getFrameCount(); i++) {
				this.lasting += gd.getDelay(i);
			}
			
			//java.io.File p = new java.io.File(this.getUrl());
			//java.awt.image.BufferedImage bi = javax.imageio.ImageIO.read(p);
			java.awt.image.BufferedImage bi = gd.getFrame(0);
			int xmin = bi.getMinX();
			int ymin = bi.getMinY();
			int xmax = bi.getWidth();
			int ymax = bi.getHeight();
			int len = com.FileManagerX.Globals.Extras.MatchPicture_PixelAmount;
			
			this.width = xmax - xmin;
			this.height = ymax - ymin;
			
			double wpace = (double)this.width / (double)len;
			double hpace = (double)this.height / (double)len;
			int ch = this.height / 2;
			int cw = this.width / 2;
			
			int half = com.FileManagerX.Globals.Extras.MatchPicture_PixelAmount / 2;
			for(int i=0; i<half; i++) {
				this.rowPixels[i] = bi.getRGB((int)(xmin + i*wpace), ch);
			}
			for(int i=0; i<half; i++) {
				this.rowPixels[len-1-i] = bi.getRGB((int)(xmax-i*wpace-1), ch);
			}
			for(int i=0; i<half; i++) {
				this.colPixels[i] = bi.getRGB(cw, (int)(ymin + i*hpace));
			}
			for(int i=0; i<half; i++) {
				this.rowPixels[len-1-i] = bi.getRGB(cw, (int)(ymax-i*hpace-1));
			}
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}