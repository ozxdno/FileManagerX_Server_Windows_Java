package com.FileManagerX.Interfaces;

public interface IReply extends ITransport {

	public boolean setOK(boolean ok);
	public boolean setStore(boolean store);
	public boolean setFailedReason(String failedReason);
	
	public boolean isOK();
	public boolean isStore();
	public String getFailedReason();
}

