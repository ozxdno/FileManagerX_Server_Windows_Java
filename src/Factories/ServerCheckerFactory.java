package Factories;

public class ServerCheckerFactory {

	public static final Interfaces.IServerChecker createServerChecker() {
		Interfaces.IServerChecker sc = new ServerChecker.ServerChecker();
		return sc;
	}
	
}
