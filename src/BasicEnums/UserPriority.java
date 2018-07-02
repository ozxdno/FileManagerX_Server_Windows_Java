package BasicEnums;

public enum UserPriority {
	Guest,
	Member,
	Depot,
	VIP,
	Admin,
	Ozxdno;
	
	public boolean isEnough(UserPriority p) {
		return this.compareTo(p) >= 0;
	}
}
