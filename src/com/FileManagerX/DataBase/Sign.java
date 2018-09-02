package com.FileManagerX.DataBase;

public enum Sign {
	EQUAL("="),
	NOT_EQUAL("!="),
	GREATER(">"),
	LESS("<"),
	GREATER_OR_EQUAL(">="),
	LESS_OR_EQUAL("<="),
	LIKE("LIKE"),
	CONTAIN("CONTAIN");
	
	private String sign;
	
	private Sign(String sign) {
		this.sign = sign;
	}
	
	public String getSignString() {
		return sign;
	}
	public static Sign toSign(String str) {
		for(Sign sign : Sign.values()) {
			if(sign.getSignString().equals(str)) {
				return sign;
			}
		}
		return null;
	}
}
