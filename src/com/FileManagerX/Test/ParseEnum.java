package com.FileManagerX.Test;

public class ParseEnum {
	
	public static void main(String[] args) {
		com.FileManagerX.BasicModels.User u = new com.FileManagerX.BasicModels.User();
		Object res = com.FileManagerX.Tools.Parse.parseEnum("Level5", u.getLevel().getClass());
		System.out.println(res);
	}
	
}
