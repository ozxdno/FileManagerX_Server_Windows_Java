package Factories;

public class MatchFactory {

	public final static Interfaces.IMatcher createPictureMacther() {
		return new Match.PictureMatcher();
	}
}
