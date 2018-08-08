package com.FileManagerX.DataBase;

public class QueryCondition implements com.FileManagerX.Interfaces.IPublic {

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
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config("Query Condition = ");
		c.addToBottom(this.itemName);
		c.addToBottom(this.sign.toString());
		c.addToBottom(this.value);
		c.addToBottom(this.relation.toString());
		return c.output();
	}
	public String input(String in) {
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			this.itemName = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.sign = Sign.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			this.value = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.relation = Relation.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
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
	public boolean stringToThis(String str) {
		this.clear();
		try {
			if(str == null || str.length() == 0) {
				return true;
			}
			str = com.FileManagerX.Tools.String.clearLRSpace(str);
			
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
			else if(r.equals("[|]")) {
				this.relation = Relation.OR;
			}
			else {
				return false;
			}
			
			this.itemName = n;
			
			if(s.equals("=")) {
				this.sign = Sign.EQUAL;
			}
			else if(s.equals("!=")) {
				this.sign = Sign.NOT_EQUAL;
			}
			else if(s.equals(">")) {
				this.sign = Sign.GREATER;
			}
			else if(s.equals(">=")) {
				this.sign = Sign.GREATER_OR_EQUAL;
			}
			else if(s.equals("<")) {
				this.sign = Sign.LESS;
			}
			else if(s.equals("<=")) {
				this.sign = Sign.LESS_OR_EQUAL;
			}
			else if(s.equals("LIKE")) {
				this.sign = Sign.LIKE;
			}
			else if(s.equals("CONTAIN")) {
				this.sign = Sign.CONTAIN;
			}
			else {
				return false;
			}
			
			this.value = v;
			return true;
		} catch(Exception e) {
			//com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}