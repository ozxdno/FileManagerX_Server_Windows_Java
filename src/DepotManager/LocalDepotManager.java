package DepotManager;

public class LocalDepotManager implements IDepotManager{
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.BaseFile file;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setFile(BasicModels.BaseFile file) {
		if(file == null || file.getLocalUrl().length() == 0) {
			return false;
		}
		this.file = file;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.BaseFile getFile() {
		return file;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LocalDepotManager() {
		initThis();
	}
	public LocalDepotManager(BasicModels.BaseFile file) {
		initThis();
		this.setFile(file);
	}
	private void initThis() {
		this.file = new BasicModels.BaseFile();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean renameFile(String name) {
		try {
			String url = this.file.getLocalUrl();
			java.io.File sour = new java.io.File(url);
			url = Tools.Url.setName(url, name);
			java.io.File dest = new java.io.File(url);
			return sour.renameTo(dest);
		} catch(Exception e) {
			return false;
		}
	}
	public boolean renameFileWithoutExtension(String nameWithoutExtension) {
		try {
			String url = this.file.getLocalUrl();
			java.io.File sour = new java.io.File(url);
			url = Tools.Url.setNameWithoutExtension(url, nameWithoutExtension);
			java.io.File dest = new java.io.File(url);
			return sour.renameTo(dest);
		} catch(Exception e) {
			return false;
		}
	}
	public boolean deleteFile() {
		try {
			String url = this.file.getLocalUrl();
			java.io.File sour = new java.io.File(url);
			return sour.delete();
		} catch(Exception e) {
			return false;
		}
	}
	public boolean moveFile(String destUrl) {
		try {
			String url = this.file.getLocalUrl();
			java.io.File sour = new java.io.File(url);
			url = Tools.Url.getLocalUrl(destUrl);
			java.io.File dest = new java.io.File(url);
			return sour.renameTo(dest);
		} catch(Exception e) {
			return false;
		}
		
	}
	public boolean copyFile(String destUrl) {
		return this.copyFileCore(this.file.getLocalUrl(), Tools.Url.getLocalUrl(destUrl));
	}
	
	public boolean renameDirectory(String name) {
		return renameFile(name);
	}
	public boolean deleteDirectory() {
		return this.deleteDirectoryCore(file.getLocalUrl());
	}
	public boolean moveDirectory(String destUrl) {
		return moveFile(destUrl);
	}
	public boolean copyDirectory(String destUrl) {
		return this.copyDirectoryCore(file.getLocalUrl(), Tools.Url.getLocalUrl(destUrl));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean copyFileCore(String sourLocalUrl, String destLocalUrl) {
		try {
			java.io.File sour = new java.io.File(sourLocalUrl);
			java.io.File dest = new java.io.File(destLocalUrl);
			
			java.io.FileInputStream is = new java.io.FileInputStream(sour);
			java.io.FileOutputStream os = new java.io.FileOutputStream(dest);
	        byte[] b = new byte[1024];
	        int n=0;
	        while((n=is.read(b))!=-1){
	            os.write(b, 0, n);
	            os.flush();
	        }
	        
	        is.close();
	        os.close();
	        return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private boolean deleteDirectoryCore(String localUrl) {
		try {
			boolean ok = true;
			java.io.File sour = new java.io.File(localUrl);
			java.io.File[] subfiles = sour.listFiles();
			for(java.io.File f : subfiles) {
				if(f.isFile()) {
					ok &= f.delete();
				}
				if(f.isDirectory()) {
					ok &= deleteDirectoryCore(f.getAbsolutePath());
				}
			}
			sour.delete();
			return ok;
		} catch(Exception e) {
			return false;
		}
	}
	private boolean copyDirectoryCore(String sourLocalUrl, String destLocalUrl) {
		try {
			boolean ok = true;
			java.io.File sour = new java.io.File(sourLocalUrl);
			java.io.File dest = new java.io.File(destLocalUrl);
			
			if(!dest.exists()) {
				ok &= dest.mkdirs();
			}
			
			java.io.File[] subfiles = sour.listFiles();
			for(java.io.File f : subfiles) {
				if(f.isFile()) {
					ok &= this.copyFileCore(f.getAbsolutePath(), destLocalUrl+"\\"+f.getName());
				}
				if(f.isDirectory()) {
					ok &= this.copyDirectoryCore(f.getAbsolutePath(), destLocalUrl+"\\"+f.getName());
				}
			}
			return ok;
		} catch(Exception e) {
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
