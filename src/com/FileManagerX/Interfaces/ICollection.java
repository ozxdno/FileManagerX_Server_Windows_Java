package com.FileManagerX.Interfaces;

public interface ICollection {

	public Object getContent();
	
	public int size();
	public void clear();
	public boolean add(Object item);
	
	public boolean sortIncrease();
	public boolean sortDecrease();
}
