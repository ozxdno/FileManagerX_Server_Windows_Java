package com.FileManagerX.Test;

public class CreateT {
	
	public static void main(String[] args) {
		
		com.FileManagerX.BasicCollections.Users users = new com.FileManagerX.BasicCollections.Users();
		users.setContent(new com.FileManagerX.BasicCollections.BasicArrayList<>());
		com.FileManagerX.BasicModels.User u = users.createT();
		System.out.println(u);
		
	}
	
}
