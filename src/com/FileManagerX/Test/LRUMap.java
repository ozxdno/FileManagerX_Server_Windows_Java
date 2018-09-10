package com.FileManagerX.Test;

public class LRUMap {
	public static void main(String[] args) {
		TMap map = new TMap();
		map.setLimit(3);
		
		com.FileManagerX.BasicModels.User u0 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u1 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u2 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u3 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u4 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u5 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u6 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u7 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u8 = new com.FileManagerX.BasicModels.User();
		com.FileManagerX.BasicModels.User u9 = new com.FileManagerX.BasicModels.User();
		
		u0.setIndex(1);
		u1.setIndex(3);
		u2.setIndex(2);
		u3.setIndex(1);
		u4.setIndex(2);
		u5.setIndex(5);
		u6.setIndex(6);
		u7.setIndex(7);
		u8.setIndex(1);
		u9.setIndex(8);
		
		map.add(u0); printMap(map);
		map.add(u1); printMap(map);
		map.add(u2); printMap(map);
		map.add(u3); printMap(map);
		map.add(u4); printMap(map);
		map.add(u5); printMap(map);
		map.add(u6); printMap(map);
		map.add(u7); printMap(map);
		map.add(u8); printMap(map);
		map.add(u9); printMap(map);
		map.add(u0); printMap(map);
		map.add(u1); printMap(map);
		map.add(u2); printMap(map);
		map.add(u3); printMap(map);
		map.add(u4); printMap(map);
		map.add(u5); printMap(map);
		map.add(u6); printMap(map);
		map.add(u7); printMap(map);
		map.add(u8); printMap(map);
		map.add(u9); printMap(map);
	}
	
	private static class TMap extends com.FileManagerX.Safe.BasicCollections.BasicLRUMap
		<com.FileManagerX.BasicModels.User, Long> {
		public Long getKey(com.FileManagerX.BasicModels.User item) {
			return item == null ? -1 : item.getIndex();
		}
	}
	private static void printMap(TMap map) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.User> it =
				map.getIterator();
		String res = "";
		while(it.hasNext()) {
			res += it.getNext().getIndex() + "->";
		}
		System.out.println(res);
	}
}
