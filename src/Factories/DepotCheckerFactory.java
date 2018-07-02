package Factories;

public class DepotCheckerFactory {

	public static final Interfaces.IDepotChecker createDepotChecker() {
		return new DepotChecker.DepotChecker();
	}
	
}
