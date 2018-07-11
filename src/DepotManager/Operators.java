package DepotManager;

import java.util.*;

public class Operators implements Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<Operator> content;
	private long permitIdle;
	private long nextIndex;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<Operator> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	public boolean setPermitIdle(long permitIdle) {
		if(permitIdle < 0) {
			return false;
		}
		this.permitIdle = permitIdle;
		return true;
	}
	public boolean setNextIndex(long index) {
		this.nextIndex = index;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Operator> getContent() {
		return this.content;
	}
	
	public long getPermitIdle() {
		return this.permitIdle;
	}
	public long getNextIndex() {
		return this.nextIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Operators() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<Operator>();
		}
		content.clear();
		this.permitIdle = Globals.Configurations.TimeForOperatorIdle;
		this.nextIndex = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void removeIdleOperator() {
		for(int i=content.size()-1; i>=0; i--) {
			if(content.get(i).isSuccessed()) {
				this.content.remove(i);
				continue;
			}
			if(!content.get(i).isStarted()) {
				continue;
			}
			if(!content.get(i).isFinished()) {
				continue;
			}
			if(Tools.Time.getTicks() - content.get(i).getEndTicks() > this.permitIdle) {
				this.content.remove(i);
				continue;
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public int size() {
		return content.size();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			this.content.add((Operator)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By Index
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<Operator>() {
			public int compare(Operator e1, Operator e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		
		try {
			Collections.sort(this.getContent(), c);
			return true;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	/**
	 * Sort By Index
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<Operator>() {
			public int compare(Operator e1, Operator e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		try {
			Collections.sort(this.getContent(), c);
			return true;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOf(long idx) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getIndex() == idx) {
				return i;
			}
		}
		return -1;
	}
	public Operator search(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public Operator fetch(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		Operator i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(long idx) {
		int index = this.indexOf(idx);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
