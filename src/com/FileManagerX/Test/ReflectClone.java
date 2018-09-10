package com.FileManagerX.Test;

public class ReflectClone {

	public static void main(String[] args) {
		
		com.FileManagerX.BasicModels.Folder f = new com.FileManagerX.BasicModels.Folder();
		f.setUrl("D:\\Space_For_Soft\\TagLibs");
		f.loadBasicInfo();
		Object res = com.FileManagerX.Tools.Reflect.clone(f);
		System.out.println(res);
		
		
		com.FileManagerX.Commands.LoginMachine lm = new com.FileManagerX.Commands.LoginMachine();
		res = com.FileManagerX.Tools.Reflect.clone(lm);
		System.out.println(res);
	}

}
