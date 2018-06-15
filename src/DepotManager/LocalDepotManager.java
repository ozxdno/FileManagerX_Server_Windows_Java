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
			boolean ex = sour.exists();
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
		try {
			String url = this.file.getLocalUrl();
			java.io.File sour = new java.io.File(url);
			url = Tools.Url.getLocalUrl(destUrl);
			java.io.File dest = new java.io.File(url);
			
			java.io.FileInputStream is = new java.io.FileInputStream(sour);
			java.io.FileOutputStream os = new java.io.FileOutputStream(dest);
	        byte[] b = new byte[1024];
	        int n=0;
	        while((n=is.read(b))!=-1){
	            os.write(b, 0, n);
	        }
	        
	        is.close();
	        os.close();
	        return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean renameDirectory(String name) {
		return renameFile(name);
	}
	public boolean deleteDirectory() {
		return deleteFile();
	}
	public boolean moveDirectory(String destUrl) {
		return moveFile(destUrl);
	}
	public boolean copyDirectory(String destUrl) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
