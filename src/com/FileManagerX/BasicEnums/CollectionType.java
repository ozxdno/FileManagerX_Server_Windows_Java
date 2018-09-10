package com.FileManagerX.BasicEnums;

public enum CollectionType {
	
	ArrayList,
	LinkedList,
	HashSet,
	TreeSet,
	HashMap,
	TreeMap,
	LinkedHashMap,
	LRUMap,
	;
	
	private boolean safe = false;
	public boolean setSafe(boolean useSafe) { this.safe = useSafe; return true; }
	public boolean isSafe() { return this.safe; }
}
