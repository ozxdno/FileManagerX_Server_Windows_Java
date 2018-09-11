package com.FileManagerX.Test;

public class PQ {

	public static void main(String[] args) {
		com.FileManagerX.BasicCollections.Users users = new com.FileManagerX.BasicCollections.Users();
		com.FileManagerX.BasicCollections.BasicPriorityQueue<com.FileManagerX.BasicModels.User> content = new com.FileManagerX.BasicCollections.BasicPriorityQueue<>();
		content.sort(com.FileManagerX.Tools.Comparator.User.nickNameAsc);
		content.setKey(com.FileManagerX.BasicCollections.Users.KeyForIndex);
		users.setContent(content);
		
		com.FileManagerX.BasicModels.User u1 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u2 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u3 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u4 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u5 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u6 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u7 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u8 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u9 = new com.FileManagerX.BasicModels.User();
		
		u1.setIndex(1); u1.setNickName("a");
		u2.setIndex(2); u2.setNickName("b");
		u3.setIndex(3); u3.setNickName("c");
		u4.setIndex(4); u4.setNickName("d");
		u5.setIndex(5); u5.setNickName("a");
		u6.setIndex(6); u6.setNickName("b");
		u7.setIndex(7); u7.setNickName("c");
		u8.setIndex(8); u8.setNickName("d");
		u9.setIndex(9); u9.setNickName("d");
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		users.add(u5);
		users.add(u6);
		users.add(u7);
		users.add(u8);
		users.add(u9);
		
		users.fetchByCount(0);
	}
	
}
