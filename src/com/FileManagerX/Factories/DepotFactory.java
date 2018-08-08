package com.FileManagerX.Factories;

public class DepotFactory {

	public static final com.FileManagerX.Interfaces.IDepotChecker createChecker() {
		return new com.FileManagerX.Depot.Checker();
	}
	
	public static final com.FileManagerX.Interfaces.IDepotManager createManager() {
		return new com.FileManagerX.Depot.Manager();
	}
	
}
