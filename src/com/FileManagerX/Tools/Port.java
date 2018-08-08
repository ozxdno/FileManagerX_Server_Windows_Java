package com.FileManagerX.Tools;

public class Port {

	public static java.net.ServerSocket socket;
	public static int port = 0;
	
	public final static int getIdleSocketPort() {
		int maxPort = 65535;
		int minPort = 1024;
		
		for(int i=maxPort; i>=minPort; i--) {
			try {
				socket = new java.net.ServerSocket(i);
				port = i;
				return i;
			} catch(Exception e) {
				;
			}
		}
		
		com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_BUILD_SOCKED_FAILED.register(
				"No Available Port");
		socket = null;
		port = -1;
		return -1;
	}
	
}
