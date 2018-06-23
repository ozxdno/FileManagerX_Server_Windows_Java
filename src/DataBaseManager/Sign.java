package DataBaseManager;

public enum Sign {
	EQUAL("="),
	NOT_EQUAL("!="),
	GREATER(">"),
	LESS("<"),
	GREATER_OR_EQUAL(">="),
	lESS_OR_EQUAL("<="),
	LIKE("LIKE"),
	CONTAIN("CONTAIN");
	
	private String sign;
	
	private Sign(String sign) {
		this.sign = sign;
	}
	
	public String getSignString() {
		return sign;
	}
}
