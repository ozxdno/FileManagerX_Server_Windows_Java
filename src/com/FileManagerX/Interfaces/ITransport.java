package com.FileManagerX.Interfaces;

public interface ITransport extends IPublic {

	public boolean setBasicMessagePackage(com.FileManagerX.Interfaces.IBasicMessagePackage bmp);
	public boolean setConnection(com.FileManagerX.Interfaces.IConnection connection);
	
	public com.FileManagerX.Interfaces.IBasicMessagePackage getBasicMessagePackage();
	public com.FileManagerX.Interfaces.IConnection getConnection();
	
	public boolean execute();
	public boolean executeInLocal();
	public boolean deliver();
	public boolean deliverToServer();
	
	public boolean send();
	public com.FileManagerX.Interfaces.IReply receive();
	
	public boolean isArriveTargetMachine();
	public boolean isArriveServer();
	public boolean isDeliver();
	public boolean isTimeOut();
	
	public boolean isArriveSourMachine();
	public boolean isArriveDestMachine();
	public boolean isSourIsServer();
	public boolean isDestIsServer();
}
