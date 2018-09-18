package com.FileManagerX.Tools;

public class Package {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static java.util.HashMap<String, Class<?>> iteratorPackage(String fullname) {
		return iteratorPackage(fullname, null);
	}
	
	public final static java.util.HashMap<String, Class<?>> iteratorPackage(String fullname, Class<?> superc) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();  
        String packagePath = fullname.replace(".", "/");  
        java.net.URL url = loader.getResource(packagePath);
        if(url == null) { return new java.util.HashMap<>(); }
        
        String type = url.getProtocol();
        if(type.equals("file")) { return iteratorFolder(url.getPath(), fullname, superc); }
        if(type.equals("jar")) { return iteratorJar(url.getPath(), superc); }
        return new java.util.HashMap<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static java.util.HashMap<String, Class<?>> iteratorFolder(
			String fullpath, String fullname, Class<?> superc) {
		java.util.HashMap<String, Class<?>> res = new java.util.HashMap<>();
		java.io.File folder = new java.io.File(fullpath);
		if(!folder.exists() || !folder.isDirectory()) { return res; }
		java.io.File[] files = folder.listFiles();
		for(java.io.File f : files) {
			if(f.isDirectory()) { continue; }
			String cpath = f.getPath();
			cpath = cpath.substring(folder.getPath().length()+1, cpath.lastIndexOf('.'));
			cpath = fullname + "." + cpath;
			
			try {
				Class<?> c = Class.forName(cpath);
				if(ExtendsOrImpl(c, superc)) { res.put(c.getSimpleName(), c); }
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				continue;
			}
		}
		
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static java.util.HashMap<String, Class<?>> iteratorJar(String jarname, Class<?> superc) {
		java.util.HashMap<String, Class<?>> res = new java.util.HashMap<>();
        String[] jarInfo = jarname.split("!");  
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));  
        String packagePath = jarInfo[1].substring(1);  
        try {  
            java.util.jar.JarFile jarFile = new java.util.jar.JarFile(jarFilePath);  
            java.util.Enumeration<java.util.jar.JarEntry> entrys = jarFile.entries();  
            while (entrys.hasMoreElements()) {  
            	java.util.jar.JarEntry jarEntry = entrys.nextElement();  
                String entryName = jarEntry.getName();  
                if (entryName.endsWith(".class")) {  
                	 int index = entryName.lastIndexOf("/");  
                     String myPackagePath;  
                     if (index != -1) {  
                         myPackagePath = entryName.substring(0, index);  
                     } else {  
                         myPackagePath = entryName;  
                     }  
                     if (myPackagePath.equals(packagePath)) {  
                         entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));  
                         Class<?> c = Class.forName(entryName);
                         if(ExtendsOrImpl(c, superc)) { res.put(c.getSimpleName(), c); }
                     }
                }  
            }  
            jarFile.close();
        } catch (Exception e) {  
        	com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
        }  
        return res;  
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static boolean ExtendsOrImpl(Class<?> a, Class<?> b) {
		if(b == null) { return true; }
		if(b.isInterface()) { return b.isAssignableFrom(a); }
		if(a.getSuperclass() == null) { return false; }
		return a.getSuperclass().equals(b);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
