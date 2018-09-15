package com.FileManagerX.Interfaces;

public interface ITransport extends IPublic {

	public boolean setBasicMessagePackage(com.FileManagerX.Interfaces.IBasicMessagePackage bmp);
	public boolean setSourConnection(com.FileManagerX.Interfaces.IConnection connection);
	public boolean setDestConnection(com.FileManagerX.Interfaces.IConnection connection);
	
	public com.FileManagerX.Interfaces.IBasicMessagePackage getBasicMessagePackage();
	public com.FileManagerX.Interfaces.IConnection getSourConnection();
	public com.FileManagerX.Interfaces.IConnection getDestConnection();
	
	public boolean execute();
	public boolean executeInLocal();
	
	public boolean send();
	public com.FileManagerX.Interfaces.IReply receive(boolean fetch);
	public default com.FileManagerX.Interfaces.IReply receive() {
		return receive(true);
	}
	
	public boolean isArriveTargetMachine();
	public boolean isArriveServer();
	public boolean isDeliver();
	public boolean isTimeOut();
	
	public boolean isArriveSourMachine();
	public boolean isArriveDestMachine();
	public boolean isSourIsServer();
	public boolean isDestIsServer();
	
	public boolean copyReversePath(ITransport t);
	public boolean copyPath(ITransport t);
}
