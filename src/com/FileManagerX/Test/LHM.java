package com.FileManagerX.Test;

public class LHM {

	public static void main(String[] args) {
		
		java.util.HashMap<Integer, String> hm = new java.util.HashMap<>();
		hm.put(1, "a");
		hm.put(2, "b");
		hm.put(3, "c");
		hm.put(4, "d");
		
		java.util.Iterator<java.util.Map.Entry<Integer, String>> itEntryHM = 
				hm.entrySet().iterator();
		while(itEntryHM.hasNext()) {
			itEntryHM.next();
			itEntryHM.remove();
		}
		
		java.util.LinkedHashMap<Integer, String> lhm = new java.util.LinkedHashMap<>(16, 0.75f, true);
		lhm.put(1, "a");
		lhm.put(2, "b");
		lhm.put(3, "c");
		lhm.put(4, "d");
		lhm.get(3);
		
		
		com.FileManagerX.BasicCollections.Users users = new com.FileManagerX.BasicCollections.Users();
		com.FileManagerX.BasicCollections.BasicLRUMap<com.FileManagerX.BasicModels.User> content = new com.FileManagerX.BasicCollections.BasicLRUMap<>();
		content.setLimit(3);
		content.setKey(com.FileManagerX.BasicCollections.Users.KeyForIndex);
		users.setContent(content);
		
		com.FileManagerX.BasicModels.User u1 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u2 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u3 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u4 = new com.FileManagerX.BasicModels.User();
		
		u1.setIndex(1); u1.setNickName("a");
		u2.setIndex(2); u2.setNickName("b");
		u3.setIndex(3); u3.setNickName("c");
		u4.setIndex(4); u4.setNickName("d");
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
		System.out.println(users.toString());
		users.searchByKey(2L);
		System.out.println(users.toString());
		users.searchByKey(1L);
		System.out.println(users.toString());
		users.add(u4);
		System.out.println(users.toString());
	}
	
}
