package com.FileManagerX.Test;

public class CFGLocalLoader {
	
	public static void main(String[] args) {
		Object res = "";
		
		com.FileManagerX.FileModels.CFG cfg = new com.FileManagerX.FileModels.CFG();
		cfg.setUrl(com.FileManagerX.Tools.Pathes.getFile_CFG());
		cfg.loadBasicInfo();
		cfg.loadFromLocal();
		cfg.saveToLocal();
		
		System.out.println(res);
	}
}
