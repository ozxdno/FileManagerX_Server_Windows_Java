package com.FileManagerX.DataBase;

public class QueryCondition implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String itemName;
	private Sign sign;
	private String value;
	private Relation relation;
	
	private Object content;
	
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
	
	public String getValue_String() {
		return this.value;
	}
	public boolean getValue_Boolean() {
		try {
			if(content == null) { content = Integer.parseInt(this.value) != 0; }
			return (boolean)content;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
	}
	public int getValue_Integer() {
		try {
			if(content == null) { content = Integer.parseInt(this.value); }
			return (int)content;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	public long getValue_Long() {
		try {
			if(content == null) { content = Long.parseLong(this.value); }
			return (long)content;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	public double getValue_Double() {
		try {
			if(content == null) { content = Double.parseDouble(this.value); }
			return (double)content;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return 0;
		}
	}
	public String getValue_Enum() {
		return this.value;
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
		return this.toConfig().getValue();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		String str = "[" + this.relation.getRelationString() + "] " +
				this.itemName + " " +
				"= " +
				this.value;
		c.addToBottom(str);
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
		return this.input(c);
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) { return c; }
		String exp = c.fetchFirstString();
		if(!c.getIsOK()) { return c; }
		
		try {
			com.FileManagerX.Tools.StringUtil su = new com.FileManagerX.Tools.StringUtil();
			su.expression = exp;
			su.bracketL = '[';
			su.bracketR = ']';
			
			this.relation = Relation.toRelation(su.getNextArg_Brackets());
			if(this.relation == null) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Relation is Wrong");
				c.setIsOK(false);
				return c;
			}
			
			su = su.getNextStringUtil();
			String item = su.getNextArg_String();
			if(item == null || item.length() == 0) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("No ItemName");
				c.setIsOK(false);
				return c;
			}
			this.itemName = item;
			
			su = su.getNextStringUtil();
			this.sign = Sign.toSign(su.getNextArg_String());
			if(this.sign == null) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Sign is Wrong");
				c.setIsOK(false);
				return c;
			}
			
			su = su.getNextStringUtil();
			this.value = su.expression;
			
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof QueryCondition) {
			QueryCondition q = (QueryCondition)o;
			this.itemName = q.itemName;
			this.sign = q.sign;
			this.value = q.value;
			this.relation = q.relation;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof QueryCondition) {
			QueryCondition q = (QueryCondition)o;
			this.itemName = new String(q.itemName);
			this.sign = q.sign;
			this.value = new String(q.value);
			this.relation = q.relation;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}