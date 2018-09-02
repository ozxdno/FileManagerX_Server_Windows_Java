package com.FileManagerX.DataBase;

public enum Relation {
	AND("&"),
	OR("|");
	
	private String relation;
	
	private Relation(String relation) {
		this.relation = relation;
	}
	
	public String getRelationString() {
		return this.relation;
	}
	public static Relation toRelation(String str) {
		if(AND.getRelationString().equals(str)) {
			return AND;
		}
		if(OR.getRelationString().equals(str)) {
			return OR;
		}
		return null;
	}
}
