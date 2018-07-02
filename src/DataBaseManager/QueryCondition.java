package DataBaseManager;

public class QueryCondition implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String itemName;
	private Sign sign;
	private String value;
	private Relation relation;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setItemName(String itemName) {
		if(itemName == null) {
			return false;
		}
		this.itemName = itemName;
		return true;
	}
	
	public boolean setSign(Sign sign) {
		if(sign == null) {
			return false;
		}
		this.sign = sign;
		return true;
	}
	public boolean setValue(String value) {
		if(value == null) {
			return false;
		}
		this.value = value;
		return true;
	}
	public boolean setRelation(Relation r) {
		this.relation = r;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getItemName() {
		return this.itemName;
	}
	public Sign getSign() {
		return this.sign;
	}
	public String getValue() {
		return this.value;
	}
	public Relation getRelation() {
		return this.relation;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryCondition() {
		initThis();
	}
	private void initThis() {
		this.itemName = "";
		this.sign = Sign.EQUAL;
		this.value = "";
		this.relation = Relation.AND;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "[" + this.relation.getRelationString() + "] " + 
				this.itemName + " " + 
				this.sign.getSignString() + " " +
				this.value;
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config("Query Condition = ");
		c.addToBottom(this.itemName);
		c.addToBottom(this.sign.ordinal());
		c.addToBottom(this.value);
		c.addToBottom(this.relation.ordinal());
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		this.itemName = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.sign = Sign.values()[c.fetchFirstInt()];
		if(!c.getIsOK()) { return null; }
		this.value = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.relation = Relation.values()[c.fetchFirstInt()];
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
		QueryCondition q = (QueryCondition)o;
		this.itemName = q.itemName;
		this.sign = q.sign;
		this.value = q.value;
		this.relation = q.relation;
	}
	public void copyValue(Object o) {
		QueryCondition q = (QueryCondition)o;
		this.itemName = new String(q.itemName);
		this.sign = q.sign;
		this.value = new String(q.value);
		this.relation = q.relation;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 只允许有一个空格作为分割符
	 * 
	 * @param str 导入字符串
	 */
	public void stringToThis(String str) {
		if(str == null || str.length() == 0) {
			return;
		}
		str = Tools.String.clearLRSpace(str);
		
		int idx = str.indexOf(' ');
		String r = str.substring(0, idx);
		str = str.substring(idx+1);
		idx = str.indexOf(' ');
		String n = str.substring(0, idx);
		str = str.substring(idx+1);
		idx = str.indexOf(' ');
		String s = str.substring(0, idx);
		str = str.substring(idx+1);
		String v = str;
		
		if(r.equals("[&]")) {
			this.relation = Relation.AND;
		}
		if(r.equals("[|]")) {
			this.relation = Relation.OR;
		}
		
		this.itemName = n;
		
		if(s.equals("=")) {
			this.sign = Sign.EQUAL;
		}
		if(s.equals("!=")) {
			this.sign = Sign.NOT_EQUAL;
		}
		if(s.equals(">")) {
			this.sign = Sign.GREATER;
		}
		if(s.equals(">=")) {
			this.sign = Sign.GREATER_OR_EQUAL;
		}
		if(s.equals("<")) {
			this.sign = Sign.LESS;
		}
		if(s.equals("<=")) {
			this.sign = Sign.LESS_OR_EQUAL;
		}
		if(s.equals("LIKE")) {
			this.sign = Sign.LIKE;
		}
		if(s.equals("CONTAIN")) {
			this.sign = Sign.CONTAIN;
		}
		
		this.value = v;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}