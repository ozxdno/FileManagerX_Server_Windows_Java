package com.FileManagerX.Tools.Comparator;

public class Transport {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static java.util.Comparator<com.FileManagerX.Interfaces.ITransport> priorityAsc =
		new java.util.Comparator<com.FileManagerX.Interfaces.ITransport>() {
			public int compare(com.FileManagerX.Interfaces.ITransport e1, 
					com.FileManagerX.Interfaces.ITransport e2) {
				if(e1 == null) {
					return -1;
				}
				if(e2 == null) {
					return 1;
				}
				return e1.getBasicMessagePackage().getPriority() < e2.getBasicMessagePackage().getPriority() ? 1 : -1;
			}
		};
	
	public final static java.util.Comparator<com.FileManagerX.Interfaces.ITransport> priorityDec =
		new java.util.Comparator<com.FileManagerX.Interfaces.ITransport>() {
			public int compare(com.FileManagerX.Interfaces.ITransport e1, 
					com.FileManagerX.Interfaces.ITransport e2) {
				if(e1 == null) {
					return -1;
				}
				if(e2 == null) {
					return 1;
				}
				return e1.getBasicMessagePackage().getPriority() < e2.getBasicMessagePackage().getPriority() ? 1 : -1;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
