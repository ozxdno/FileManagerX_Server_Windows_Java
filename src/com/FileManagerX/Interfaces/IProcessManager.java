package com.FileManagerX.Interfaces;

public interface IProcessManager extends ICollection {

	public java.util.LinkedList<IProcess> getContent();
	public boolean setContent(java.util.LinkedList<IProcess> processes);
	
	public void removeIdleProcesses();
	public void removeAllProcesses();
	
	public int indexOf(String threadName);
	public IProcess search(String threadName);
	public IProcess fetch(String threadName);
	public void delete(String threadName);
}
