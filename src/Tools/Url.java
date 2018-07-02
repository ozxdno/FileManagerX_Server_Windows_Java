package Tools;

public class Url {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static java.lang.String getMachineUrl(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isPortFirstItem(url)) {
			int idx = url.indexOf('\\');
			if(idx < 0) {
				return url;
			}
			return url.substring(0, idx);
		}
		if(Url.isIpFirstItem(url)) {
			int idx = url.indexOf('\\');
			if(idx < 0) {
				return url;
			}
			return url.substring(0, idx);
		}
		return "";
	}
	public final static java.lang.String getIp(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isIpFirstItem(url)) {
			int idx1 = url.indexOf('\\');
			int idx2 = url.indexOf(':');
			if(idx1 < 0 && idx2 < 0) {
				return url;
			}
			if(idx1 < 0) {
				return url.substring(0, idx2);
			}
			if(idx2 < 0) {
				return url.substring(0, idx1);
			}
			if(idx2 > idx1) {
				return url.substring(0, idx1);
			}
			return url.substring(0, idx2);
		}
		return "";
	}
	public final static int getPort(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return -1;
		}
		if(Url.isPortFirstItem(url)) {
			int idx = url.indexOf('\\');
			if(idx < 0) {
				return Integer.parseInt(url);
			}
			return Integer.parseInt(url.substring(0, idx));
		}
		if(Url.isIpFirstItem(url)) {
			int idx1 = url.indexOf(':');
			int idx2 = url.indexOf("\\");
			if(idx1 < 0 && idx2 < 0) {
				return -1;
			}
			if(idx1 < 0) {
				return -1;
			}
			if(idx2 < 0) {
				try {
					return Integer.parseInt(url.substring(idx1+1));
				} catch(Exception e) {
					return -1;
				}
			}
			if(idx1 > idx2) {
				return -1;
			}
			try {
				return Integer.parseInt(url.substring(idx1+1,idx2));
			} catch(Exception e) {
				return -1;
			}
		}
		
		return -1;
	}
	public final static java.lang.String getLocalUrl(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isPortFirstItem(url) || Url.isIpFirstItem(url)) {
			int idx0 = url.indexOf('\\');
			if(idx0 < 0) {
				return "";
			}
			url = url.substring(idx0 + 1);
		}
		return url;
	}
	public final static java.lang.String getDriver(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isPortFirstItem(url) || Url.isIpFirstItem(url)) {
			int idx0 = url.indexOf('\\');
			if(idx0 < 0) {
				return "";
			}
			url = url.substring(idx0 + 1);
		}
		if(Url.isDriverFirstItem(url)) {
			int idx = url.indexOf(':');
			return url.substring(0, idx);
		}
		return "";
	}
	public final static java.lang.String getPath(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isPortFirstItem(url) || Url.isIpFirstItem(url)) {
			int idx0 = url.indexOf('\\');
			if(idx0 < 0) {
				return "";
			}
			url = url.substring(idx0 + 1);
		}
		if(Url.isDriverFirstItem(url)) {
			int idx0 = url.indexOf(':');
			url = url.substring(idx0 + 1);
		}
		int idx = url.lastIndexOf('\\');
		if(idx < 0) {
			return "";
		}
		return url.substring(0,idx);
	}
	public final static java.lang.String getName(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isPortFirstItem(url) || Url.isIpFirstItem(url)) {
			int idx0 = url.indexOf('\\');
			if(idx0 < 0) {
				return "";
			}
			url = url.substring(idx0 + 1);
		}
		if(Url.isDriverFirstItem(url)) {
			int idx0 = url.indexOf(':');
			url = url.substring(idx0 + 1);
		}
		int idx = url.lastIndexOf('\\');
		if(idx < 0) {
			return url;
		}
		return url.substring(idx+1, url.length());
	}
	public final static java.lang.String getNameWithoutExtension(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isPortFirstItem(url) || Url.isIpFirstItem(url)) {
			int idx0 = url.indexOf('\\');
			if(idx0 < 0) {
				return "";
			}
			url = url.substring(idx0 + 1);
		}
		if(Url.isDriverFirstItem(url)) {
			int idx0 = url.indexOf(':');
			url = url.substring(idx0 + 1);
		}
		int bg = url.lastIndexOf('\\');
		int ed = url.lastIndexOf('.');
		if(bg < 0) {
			bg = -1;
		}
		if(ed < 0 || ed < bg) {
			ed = url.length();
		}
		return url.substring(bg+1, ed);
	}
	public final static java.lang.String getExtension(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return "";
		}
		if(Url.isPortFirstItem(url) || Url.isIpFirstItem(url)) {
			int idx0 = url.indexOf('\\');
			if(idx0 < 0) {
				return "";
			}
			url = url.substring(idx0 + 1);
		}
		if(Url.isDriverFirstItem(url)) {
			int idx0 = url.indexOf(':');
			url = url.substring(idx0 + 1);
		}
		int bg = url.lastIndexOf('\\');
		int ed = url.lastIndexOf('.');
		if(bg < 0) {
			bg = -1;
		}
		if(ed < 0 || ed < bg) {
			return "";
		}
		return url.substring(ed, url.length());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static java.lang.String setMachineUrl(java.lang.String sourUrl, java.lang.String machineUrl) {
		if(sourUrl == null || sourUrl.length() == 0) {
			return machineUrl;
		}
		if(Url.isPortFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return machineUrl;
			}
			return machineUrl + sourUrl.substring(idx);
		}
		if(Url.isIpFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return machineUrl;
			}
			return machineUrl + sourUrl.substring(idx);
		}
		return machineUrl + "\\" + sourUrl;
	}
	public final static java.lang.String setIp(java.lang.String sourUrl, java.lang.String ip) {
		if(sourUrl == null || sourUrl.length() == 0) {
			return ip;
		}
		if(Url.isPortFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return ip + ":" + sourUrl;
			}
			return ip + ":" + sourUrl;
		}
		if(Url.isIpFirstItem(sourUrl)) {
			int idx1 = sourUrl.indexOf(':');
			int idx2 = sourUrl.indexOf('\\');
			if(idx1 < 0 && idx2 < 0) {
				return ip;
			}
			if(idx1 < 0) {
				return ip + "\\" + sourUrl;
			}
			if(idx2 < 0) {
				return ip + sourUrl.substring(idx1);
			}
			if(idx2 < idx1) {
				return ip + "\\" + sourUrl;
			}
			return ip + sourUrl.substring(idx1);
		}
		
		return ip + "\\" + sourUrl;
	}
	public final static java.lang.String setPort(java.lang.String sourUrl, int port) {
		java.lang.String p = java.lang.String.valueOf(port);
		if(sourUrl == null || sourUrl.length() == 0) {
			return p;
		}
		if(Url.isPortFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return p;
			}
			return sourUrl.substring(0 ,idx+1) + p;
		}
		if(Url.isIpFirstItem(sourUrl)) {
			int idx1 = sourUrl.indexOf(':');
			int idx2 = sourUrl.indexOf('\\');
			if(idx1 < 0 && idx2 < 0) {
				return sourUrl + ":" + p;
			}
			if(idx1 < 0) {
				return p + "\\" + sourUrl;
			}
			if(idx2 < 0) {
				return sourUrl.substring(0, idx1+1) + p;
			}
			if(idx2 < idx1) {
				return p + "\\" + sourUrl;
			}
			
			return sourUrl.substring(0,idx1+1) + p + sourUrl.substring(idx2);
		}
		
		return p + "\\" + sourUrl;
	}
	public final static java.lang.String setLocalUrl(java.lang.String sourUrl, java.lang.String localUrl) {
		if(sourUrl == null || sourUrl.length() == 0) {
			return localUrl;
		}
		java.lang.String machineUrl = "";
		if(Url.isPortFirstItem(sourUrl) || Url.isIpFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return sourUrl + "\\" + localUrl;
			}
			machineUrl = sourUrl.substring(0 , idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		
		return machineUrl + "\\" + localUrl;
	}
	public final static java.lang.String setDriver(java.lang.String sourUrl, java.lang.String driver) {
		if(sourUrl == null || sourUrl.length() == 0) {
			return driver + ":";
		}
		java.lang.String machineUrl = "";
		if(Url.isPortFirstItem(sourUrl) || Url.isIpFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return sourUrl + "\\" + driver + ":";
			}
			machineUrl = sourUrl.substring(0 , idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		if(Url.isDriverFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf(':');
			return machineUrl + driver + sourUrl.substring(idx);
		}
		
		return driver + ":" + sourUrl;
	}
	public final static java.lang.String setPath(java.lang.String sourUrl, java.lang.String path) {
		if(sourUrl == null || sourUrl.length() == 0) {
			return path;
		}
		java.lang.String machineUrl = "";
		if(Url.isPortFirstItem(sourUrl) || Url.isIpFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return sourUrl + "\\" + path;
			}
			machineUrl = sourUrl.substring(0 , idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		java.lang.String driver = "";
		if(Url.isDriverFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf(':');
			driver = sourUrl.substring(0, idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		
		int idx1 = sourUrl.indexOf('\\');
		int idx2 = sourUrl.lastIndexOf('\\');
		if(idx1 < 0) {
			if(sourUrl.length() == 0) {
				return machineUrl + driver + path;
			}
			return machineUrl + driver + path + "\\" + sourUrl;
		}
		if(idx1 == idx2) {
			return machineUrl + driver + path + sourUrl.substring(idx2);
		}
		
		return machineUrl + driver + path + sourUrl.substring(idx2);
	}
	public final static java.lang.String setName(java.lang.String sourUrl, java.lang.String name) {
		if(sourUrl == null || sourUrl.length() == 0) {
			return name;
		}
		java.lang.String machineUrl = "";
		if(Url.isPortFirstItem(sourUrl) || Url.isIpFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return sourUrl + "\\" + name;
			}
			machineUrl = sourUrl.substring(0 , idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		java.lang.String driver = "";
		if(Url.isDriverFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf(':');
			driver = sourUrl.substring(0, idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		
		int idx1 = sourUrl.lastIndexOf('\\');
		if(idx1 < 0) {
			if(sourUrl.length() == 0) {
				return machineUrl + driver + name;
			}
			return machineUrl + driver + name;
		}
		return machineUrl + driver + sourUrl.substring(0,idx1+1) + name;
	}
	public final static java.lang.String setNameWithoutExtension(java.lang.String sourUrl, java.lang.String name) {
		if(sourUrl == null || sourUrl.length() == 0) {
			return name;
		}
		java.lang.String machineUrl = "";
		if(Url.isPortFirstItem(sourUrl) || Url.isIpFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return sourUrl + "\\" + name;
			}
			machineUrl = sourUrl.substring(0 , idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		java.lang.String driver = "";
		if(Url.isDriverFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf(':');
			driver = sourUrl.substring(0, idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		
		int idx1 = sourUrl.lastIndexOf('.');
		int idx2 = sourUrl.lastIndexOf('\\');
		if(idx1 < 0 && idx2 < 0) {
			if(sourUrl.length() == 0) {
				return machineUrl + driver + name;
			}
			return machineUrl + driver + name;
		}
		if(idx1 < 0) {
			return machineUrl + driver + sourUrl.substring(0,idx2+1) + name;
		}
		if(idx2 < 0) {
			return machineUrl + driver + name + sourUrl.substring(idx1);
		}
		if(idx1 < idx2) {
			return machineUrl + driver + sourUrl.substring(0,idx2+1) + name;
		}
		
		return machineUrl + driver + sourUrl.substring(0,idx2+1) + name + sourUrl.substring(idx1);
	}
	public final static java.lang.String setExtension(java.lang.String sourUrl, java.lang.String extension) {
		extension = Url.fixExtension(extension);
		if(sourUrl == null || sourUrl.length() == 0) {
			return "";
		}
		java.lang.String machineUrl = "";
		if(Url.isPortFirstItem(sourUrl) || Url.isIpFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf('\\');
			if(idx < 0) {
				return sourUrl;
			}
			machineUrl = sourUrl.substring(0 , idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		java.lang.String driver = "";
		if(Url.isDriverFirstItem(sourUrl)) {
			int idx = sourUrl.indexOf(':');
			driver = sourUrl.substring(0, idx+1);
			sourUrl = sourUrl.substring(idx+1);
		}
		if(sourUrl.length() == 0) {
			return machineUrl + driver;
		}
		
		int idx1 = sourUrl.lastIndexOf('.');
		int idx2 = sourUrl.lastIndexOf('\\');
		if(idx1 < 0 && idx2 < 0) {
			return machineUrl + driver + sourUrl + extension;
		}
		if(idx1 < 0) {
			return machineUrl + driver + sourUrl + extension;
		}
		if(idx2 < 0) {
			return machineUrl + driver + sourUrl.substring(0,idx1) + extension;
		}
		if(idx1 < idx2) {
			return machineUrl + driver + sourUrl + extension;
		}
		
		return machineUrl + driver + sourUrl.substring(0,idx1) + extension;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static java.lang.String fixExtension(java.lang.String extension) {
		if(extension == null || extension.length() == 0) {
			return "";
		}
		extension = extension.toLowerCase();
		if(extension.charAt(0) != '.') {
			extension = '.' + extension;
		}
		return extension;
	}
	public final static boolean isFileIn(java.lang.String fileUrl, java.lang.String folderUrl) {
		if(folderUrl == null || folderUrl.length() == 0) {
			return false;
		}
		if(fileUrl == null || fileUrl.length() == 0) {
			return false;
		}
		if(fileUrl.length() < folderUrl.length()) {
			return false;
		}
		
		return folderUrl.equals(fileUrl.substring(0, folderUrl.length()));
	}
	public final static boolean isFolderIn(java.lang.String folderUrl, java.lang.String rootUrl) {
		if(folderUrl == null || folderUrl.length() == 0) {
			return false;
		}
		if(rootUrl == null || rootUrl.length() == 0) {
			return false;
		}
		if(folderUrl.length() < rootUrl.length()) {
			return false;
		}
		
		return rootUrl.equals(folderUrl.substring(0, rootUrl.length()));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean isIp(java.lang.String ip) {
		java.lang.String format = "([0-9]{1,3}.){3}[0-9]{1,3}";
		return ip.matches(format);
	}
	public final static boolean isIpFirstItem(java.lang.String url) {
		if(url == null || url.length() == 0) {
			return false;
		}
		int idx1 = url.indexOf(':');
		int idx2 = url.indexOf('\\');
		java.lang.String ip = url;
		if(idx1 < 0 && idx2 < 0) {
			ip = url;
		}
		else if(idx1 < 0) {
			ip = url.substring(0,idx2);
		}
		else if(idx2 < 0) {
			ip = url.substring(0,idx1);
		}
		else if(idx2 < idx1){
			ip = url.substring(0,idx2);
		} 
		else {
			ip = url.substring(0,idx1);
		}
		
		return isIp(ip);
	}
	public final static boolean isPortFirstItem(java.lang.String url) {
		try {
			int idx = url.indexOf('\\');
			if(idx < 0) {
				Integer.parseInt(url);
			} else {
				Integer.parseInt(url.substring(0, idx));
			}
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public final static boolean isDriverFirstItem(java.lang.String url) {
		int idx1 = url.indexOf(':');
		if(idx1 < 0) {
			return false;
		}
		int idx2 = url.indexOf('\\');
		if(idx2 >=0 && idx2 < idx1) {
			return false;
		}
		return !Url.isIp(url.substring(0, idx1));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
