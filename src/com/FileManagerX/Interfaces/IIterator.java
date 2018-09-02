package com.FileManagerX.Interfaces;

public interface IIterator<T> {

	public boolean hasNext();
	public T getNext();
	public void remove();
}
