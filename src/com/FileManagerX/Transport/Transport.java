package com.FileManagerX.Transport;

public class Transport implements com.FileManagerX.Interfaces.ITransport {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IBasicMessagePackage bmp;
	
	private com.FileManagerX.Interfaces.IConnection scon;
	private com.FileManagerX.Interfaces.IConnection dcon;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setBasicMessagePackage(com.FileManagerX.Interfaces.IBasicMessagePackage bmp) {
		if(bmp == null) {
			return false;
		}
		this.bmp = bmp;
		return true;
	}
	public boolean setSourConnection(com.FileManagerX.Interfaces.IConnection connection) {
		if(connection == null) {
			return false;
		}
		this.scon = connection;
		return true;
	}
	public boolean setDestConnection(com.FileManagerX.Interfaces.IConnection connection) {
		if(connection == null) {
			return false;
		}
		this.dcon = connection;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.Interfaces.IBasicMessagePackage getBasicMessagePackage() {
		return this.bmp;
	}
	public com.FileManagerX.Interfaces.IConnection getSourConnection() {
		return this.scon;
	}
	public com.FileManagerX.Interfaces.IConnection getDestConnection() {
		return this.dcon;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Transport() {
		this.initThis();
	}
	private void initThis() {
		this.bmp = com.FileManagerX.Factories.CommunicatorFactory.createBMP();
		this.scon = null;
		this.dcon = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.bmp.toConfig());
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		try {
			if(!c.getIsOK()) { return c; }
			c = this.bmp.input(c);
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof Transport) {
			Transport t = (Transport)o;
			this.bmp = t.bmp;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof Transport) {
			Transport t = (Transport)o;
			this.bmp.copyValue(t.bmp);
		}
	}
	
	public boolean copyReversePath(com.FileManagerX.Interfaces.ITransport t) {
		com.FileManagerX.Interfaces.IRoutePathPackage sour = t.getBasicMessagePackage().getRoutePathPackage();
		com.FileManagerX.Interfaces.IRoutePathPackage dest = 
				com.FileManagerX.Factories.CommunicatorFactory.createRRP();
		this.getBasicMessagePackage().setRoutPathPackage(dest);

		dest.setSourMountPath(sour.getDestMountPath());
		dest.setDestMountPath(sour.getSourMountPath());
		dest.setRecommendPath(sour.getActualPath());
		dest.reverse(dest.getSourMountPath());
		dest.reverse(dest.getDestMountPath());
		dest.reverse(dest.getRecommendPath());
		
		dest.setStartTime1(sour.getStartTime1());
		dest.setStartTime2(sour.getStartTime2());
		dest.setEndTime1(sour.getEndTime1());
		dest.setEndTime2(sour.getEndTime2());
		
		dest.setSourMountServer(sour.getDestMountServer());
		dest.setDestMountServer(sour.getSourMountServer());
		return true;
	}
	public boolean copyPath(com.FileManagerX.Interfaces.ITransport t) {
		com.FileManagerX.Interfaces.IRoutePathPackage sour = t.getBasicMessagePackage().getRoutePathPackage();
		com.FileManagerX.Interfaces.IRoutePathPackage dest = this.bmp.getRoutePathPackage();
		dest.copyValue(sour);
		dest.setRecommendDepth(-1);
		dest.setActualDepth(-1);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		return true;
	}
	public boolean executeInLocal() {
		return true;
	}
	public boolean send() {
		return com.FileManagerX.Globals.Datas.Sender.add(this);
	}
	public com.FileManagerX.Interfaces.IReply receive(boolean fetch) {
		return com.FileManagerX.Globals.Datas.Receiver.get(
				this.getBasicMessagePackage().getIndex(),
				this.getBasicMessagePackage().getPermitIdle(),
				fetch
			);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isArriveTargetMachine() {
		return this.getBasicMessagePackage().getBroadcast().getNeedExecute();
	}
	public boolean isSourIsServer() {
		return this.bmp.getSourMachineIndex() == com.FileManagerX.Globals.Configurations.Server_MachineIndex;
	}
	public boolean isDestIsServer() {
		return this.bmp.getDestMachineIndex() == com.FileManagerX.Globals.Configurations.Server_MachineIndex;
	}
	public boolean isArriveServer() {
		return com.FileManagerX.Globals.Configurations.IsServer;
	}
	public boolean isDeliver() {
		return this.getBasicMessagePackage().getBroadcast().getNeedDeliver();
	}
	public boolean isTimeOut() {
		long now = com.FileManagerX.Tools.Time.getTicks();
		long start = this.bmp.getRoutePathPackage().getStartTime1();
		long permit = this.bmp.getPermitIdle();
		return start > 0 && now - start > permit;
	}
	
	public boolean isArriveSourMachine() {
		return this.bmp.getSourMachineIndex() == com.FileManagerX.Globals.Configurations.This_MachineIndex;
	}
	public boolean isArriveDestMachine() {
		return this.bmp.getDestMachineIndex() == com.FileManagerX.Globals.Configurations.This_MachineIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
