package com.FileManagerX.BasicEnums;

public enum SocketType {

	IPV4_TCP,
	IPV4_UDP,
	IPV6_TCP,
	IPV6_UDP,
	;
	
	public com.FileManagerX.Interfaces.ISocketS getSocketS() {
		if(this.equals(IPV4_TCP)) {
			return new com.FileManagerX.Socket.Server_IPV4_TCP();
		}
		return null;
	}
	public com.FileManagerX.Interfaces.ISocketC getSocketC() {
		if(this.equals(IPV4_TCP)) {
			return new com.FileManagerX.Socket.Client_IPV4_TCP();
		}
		return null;
	}
	
}
