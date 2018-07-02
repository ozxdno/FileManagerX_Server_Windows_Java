package Factories;

public class DepotManagerFactory {

	public static final Interfaces.IDepotManager createDepotManager() {
		return new DepotManager.DepotManager();
	}
	
}
