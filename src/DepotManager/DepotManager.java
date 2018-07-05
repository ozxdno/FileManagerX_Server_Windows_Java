package DepotManager;

public class DepotManager implements Interfaces.IDepotManager{
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DepotInfo depot;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDepot(BasicModels.DepotInfo depot) {
		if(depot == null) {
			return false;
		}
		this.depot = depot;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DepotInfo getDepot() {
		return depot;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DepotManager() {
		initThis();
	}
	private void initThis() {
		this.depot = new BasicModels.DepotInfo();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean renameFile(String sourUrl, String targetName) {
		if(!this.isDepotRight()) {
			return false;
		}
		
		try {
			java.io.File sour = new java.io.File(sourUrl);
			String destUrl = Tools.Url.setName(sourUrl, targetName);
			java.io.File dest = new java.io.File(destUrl);
			return sour.renameTo(dest);
		} catch(Exception e) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(
					
					e.toString()
					);
			return false;
		}
	}
	public boolean renameFileWithoutExtension(String sourUrl, String targetNameWithoutExtension) {
		if(!this.isDepotRight()) {
			return false;
		}
		
		try {
			java.io.File sour = new java.io.File(sourUrl);
			String destUrl = Tools.Url.setNameWithoutExtension(sourUrl, targetNameWithoutExtension);
			java.io.File dest = new java.io.File(destUrl);
			return sour.renameTo(dest);
		} catch(Exception e) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(
					
					e.toString()
					);
			return false;
		}
	}
	public boolean deleteFile(String targetFile) {
		if(!this.isDepotRight()) {
			return false;
		}
		
		try {
			java.io.File sour = new java.io.File(targetFile);
			return sour.delete();
		} catch(Exception e) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(
					
					e.toString()
					);
			return false;
		}
	}
	public boolean moveFile(String sourUrl, String destUrl) {
		if(!this.isDepotRight()) {
			return false;
		}
		if(!this.isInDepot(destUrl)) {
			return false;
		}
		
		try {
			java.io.File sour = new java.io.File(sourUrl);
			java.io.File dest = new java.io.File(destUrl);
			return sour.renameTo(dest);
		} catch(Exception e) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(
					
					e.toString()
					);
			return false;
		}
		
	}
	public boolean copyFile(String sourUrl, String destUrl) {
		if(!this.isDepotRight()) {
			return false;
		}
		if(!this.isInDepot(destUrl)) {
			return false;
		}
		return this.copyFileCore(sourUrl, destUrl);
	}
	
	public boolean renameDirectory(String sourUrl, String targetName) {
		if(!this.isDepotRight()) {
			return false;
		}
		
		return renameFile(sourUrl, targetName);
	}
	public boolean deleteDirectory(String targetFolder) {
		if(!this.isDepotRight()) {
			return false;
		}
		
		return this.deleteDirectoryCore(targetFolder);
	}
	public boolean moveDirectory(String sourUrl, String destUrl) {
		if(!this.isDepotRight()) {
			return false;
		}
		if(!this.isInDepot(destUrl)) {
			return false;
		}
		return moveFile(sourUrl, destUrl);
	}
	public boolean copyDirectory(String sourUrl, String destUrl) {
		if(!this.isDepotRight()) {
			return false;
		}
		if(!this.isInDepot(destUrl)) {
			return false;
		}
		return this.copyDirectoryCore(sourUrl, destUrl);
	}
	
	public boolean createFolder(String targetUrl) {
		if(!this.isDepotRight()) {
			return false;
		}
		if(!this.isInDepot(targetUrl)) {
			return false;
		}
		
		java.io.File f = new java.io.File(targetUrl);
		try {
			return f.createNewFile();
		} catch(Exception e) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(e.toString());
			return false;
		}
	}
	public boolean createFile(String targetUrl) {
		if(!this.isDepotRight()) {
			return false;
		}
		if(!this.isInDepot(targetUrl)) {
			return false;
		}
		
		java.io.File f = new java.io.File(targetUrl);
		try {
			return f.mkdirs();
		} catch(Exception e) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(e.toString());
			return false;
		}
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
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(
					
					e.toString()
					);
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
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(
					
					e.toString()
					);
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
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register(
					
					e.toString()
					);
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean isInDepot(String url) {
		if(!Tools.Url.isFileIn(url, depot.getUrl())) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register("URL Not in This Depot");
			return false;
		}
		return true;
	}
	private boolean isDepotRight() {
		if(depot == null) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register("Depot is NULL");
			return false;
		}
		if(depot.getIndex() <= 0) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register("Depot Index Error");
			return false;
		}
		if(depot.getUrl() == null || depot.getUrl().length() == 0) {
			BasicEnums.ErrorType.FILE_OPERATION_FAILED.register("Depot Url is NULL or Empty");
			return false;
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
