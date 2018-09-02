package com.FileManagerX.Interfaces;

public interface IPublic {

	public java.lang.String toString();
	public com.FileManagerX.BasicModels.Config toConfig();
	public java.lang.String output();
	public com.FileManagerX.BasicModels.Config input(java.lang.String in);
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c);
	public void copyReference(Object o);
	public void copyValue(Object o);
	public void clear();
}
