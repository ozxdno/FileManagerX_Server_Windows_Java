package DataBaseManager;

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
}
