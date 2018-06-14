package DataBaseManager;

public class QueryCondition implements Tools.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String itemName;
	private String uBound;
	private String dBound;
	private String equal;
	private Relation relationToNext;
	private boolean equalUBound;
	private boolean equalDBound;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setItemName(String itemName) {
		if(itemName == null) {
			return false;
		}
		this.itemName = itemName;
		return true;
	}
	public boolean setUBound(String uBound) {
		this.uBound = uBound;
		return true;
	}
	public boolean setDBound(String dBound) {
		this.dBound = dBound;
		return true;
	}
	public boolean setEqual(String equal) {
		this.equal = equal;
		return true;
	}
	public boolean setRelationToNext(Relation r) {
		this.relationToNext = r;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getItemName() {
		return this.itemName;
	}
	public String getUBound() {
		return this.uBound;
	}
	public String getDBound() {
		return this.dBound;
	}
	public String getEqual() {
		return this.equal;
	}
	public Relation getRelationToNext() {
		return this.relationToNext;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryCondition() {
		initThis();
	}
	private void initThis() {
		this.itemName = "";
		this.uBound = null;
		this.dBound = null;
		this.relationToNext = Relation.OR;
		this.equalUBound = true;
		this.equalDBound = true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		String r = "";
		if(this.relationToNext == Relation.AND) r = " [&] ";
		if(this.relationToNext == Relation.NOT) r = " [!] ";
		if(this.relationToNext == Relation.OR ) r = " [|] ";
		
		String sign = "";
		if(this.equal != null) {
			return this.itemName + " == " + this.equal + r;
		}
		if(this.uBound == null && this.dBound == null) {
			return "Wrong Expression";
		}
		if(this.uBound == null) {
			if(this.equalUBound) {
				sign = " <= ";
			} else {
				sign = " < ";
			}
			return this.itemName + sign + this.uBound + r;
		}
		if(this.dBound == null) {
			if(this.equalDBound) {
				sign = " >= ";
			} else {
				sign = " > ";
			}
			return this.itemName + sign + this.dBound + r;
		}
		
		String signL = this.equalDBound ? " <= " : " < ";
		String signR = this.equalUBound ? " <= " : " < ";
		return this.dBound + signL + this.itemName + signR + this.uBound + r;
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config("Query Condition = ");
		c.addToBottom(this.itemName);
		c.addToBottom(this.uBound);
		c.addToBottom(this.dBound);
		c.addToBottom(this.equal);
		c.addToBottom(this.relationToNext.ordinal());
		c.addToBottom(this.equalUBound);
		c.addToBottom(this.equalDBound);
		return c.output();
	}
	public boolean input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		this.itemName = c.fetchFirstString();
		if(!c.getIsOK()) { return false; }
		this.uBound = c.fetchFirstString();
		if(!c.getIsOK()) { return false; }
		this.dBound = c.fetchFirstString();
		if(!c.getIsOK()) { return false; }
		this.equal = c.fetchFirstString();
		if(!c.getIsOK()) { return false; }
		this.relationToNext = Relation.values()[c.fetchFirstInt()];
		if(!c.getIsOK()) { return false; }
		this.equalUBound = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return false; }
		this.equalDBound = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return false; }
		return true;
	}
	public void copyReference(Object o) {
		QueryCondition q = (QueryCondition)o;
		this.itemName = q.itemName;
		this.uBound = q.uBound;
		this.dBound = q.dBound;
		this.equal = q.equal;
		this.relationToNext = q.relationToNext;
		this.equalUBound = q.equalUBound;
		this.equalDBound = q.equalDBound;
	}
	public void copyValue(Object o) {
		QueryCondition q = (QueryCondition)o;
		this.itemName = new String(q.itemName);
		this.uBound = new String(q.uBound);
		this.dBound = new String(q.dBound);
		this.equal = new String(q.equal);
		this.relationToNext = q.relationToNext;
		this.equalUBound = q.equalUBound;
		this.equalDBound = q.equalDBound;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}