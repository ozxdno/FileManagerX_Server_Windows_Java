package com.FileManagerX.FileModels;

public class Picture extends com.FileManagerX.BasicModels.File {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int height;
	private int width;
	
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
	public int[] getRowPixels() {
		return this.rowPixels;
	}
	public int[] getColPixels() {
		return this.colPixels;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Picture() {
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
		return "[" + this.getName() + "] " + width + "X" + height;
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.height);
		c.addToBottom(this.width);
		c.addToBottom(com.FileManagerX.Tools.StringUtil.link(rowPixels, " "));
		c.addToBottom(com.FileManagerX.Tools.StringUtil.link(colPixels, " "));
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
		this.height = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		this.width = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		java.util.ArrayList<Integer> pixes = 
				com.FileManagerX.Tools.StringUtil.split2int(c.fetchFirstString(), " ");
		this.rowPixels = com.FileManagerX.Tools.List2Array.toIntArray(pixes);
		if(!c.getIsOK()) { return c; }
		pixes = com.FileManagerX.Tools.StringUtil.split2int(c.fetchFirstString(), " ");
		this.colPixels = com.FileManagerX.Tools.List2Array.toIntArray(pixes);
		if(!c.getIsOK()) { return c; }
		return c;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof Picture) {
			super.copyReference(o);
			Picture p = (Picture)o;
			this.height = p.height;
			this.width = p.width;
			this.rowPixels = p.rowPixels;
			this.colPixels = p.colPixels;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof Picture) {
			super.copyValue(o);
			Picture p = (Picture)o;
			this.height = p.height;
			this.width = p.width;
			this.rowPixels = p.rowPixels.clone();
			this.colPixels = p.colPixels.clone();
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyValue(o);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean load() {
		
		try {
			java.io.File p = new java.io.File(this.getUrl());
			java.awt.image.BufferedImage bi = javax.imageio.ImageIO.read(p);
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
