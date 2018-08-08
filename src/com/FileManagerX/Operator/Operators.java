package com.FileManagerX.Operator;

import java.util.*;

import com.FileManagerX.Interfaces.*;
import com.FileManagerX.Globals.*;

public class Operators implements ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Map<Long, Operator> content;
	private long permitIdle;
	private long nextIndex;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(Map<Long, Operator> content) {
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
	
	public Map<Long, Operator> getContent() {
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
			content = new java.util.HashMap<>();
		}
		content.clear();
		this.permitIdle = Configurations.TimeForOperatorIdle;
		this.nextIndex = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void removeIdleOperator() {
		
		java.util.Iterator<Map.Entry<Long, Operator>> it = this.content.entrySet().iterator();
		while(it.hasNext()) {
			Operator o = it.next().getValue();
			if(o.getEndTicks() <= o.getBeginTicks()) {
				continue;
			}
			if(o.isRunning()) {
				continue;
			}
			if(com.FileManagerX.Tools.Time.getTicks() - o.getEndTicks() <= o.getPermitIdle()) {
				continue;
			}
			it.remove();
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
			Operator o = (Operator)item;
			this.content.put(o.getIndex(), o);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Failed
	 * 
	 */
	public boolean sortIncrease() {
		return false;
	}
	
	/**
	 * Failed
	 * 
	 */
	public boolean sortDecrease() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOf(long idx) {
		return (int)idx;
	}
	public Operator search(long idx) {
		return content.get(idx);
	}
	public Operator fetch(long idx) {
		Operator i = content.remove(idx);
		return i;
	}
	public void delete(long idx) {
		content.remove(idx);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
