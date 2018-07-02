package Factories;

public class ServerCheckerFactory {

	public static final Interfaces.IServerChecker createServerChecker() {
		return new ServerChecker.ServerChecker();
	}
	
}
