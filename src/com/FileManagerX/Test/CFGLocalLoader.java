package com.FileManagerX.Test;

public class CFGLocalLoader {
	
	public static void main(String[] args) {
		Object res = "";
		
		com.FileManagerX.FileModels.CFG cfg = new com.FileManagerX.FileModels.CFG();
		cfg.setUrl(com.FileManagerX.Tools.Pathes.CFG.getAbsolute());
		cfg.loadBasicInfo();
		cfg.loadFromLocal();
		cfg.saveToLocal();
		
		System.out.println(res);
	}
}
