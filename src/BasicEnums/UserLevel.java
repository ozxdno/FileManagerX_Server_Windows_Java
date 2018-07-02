package BasicEnums;

public enum UserLevel {
	Level1,
	Level2,
	Level3,
	Level4,
	Level5,
	Level6,
	Level7,
	Level8,
	Level9,
	Level0;
	
	public boolean isEnough(UserLevel level) {
		return this.compareTo(level) >= 0;
	}
}
