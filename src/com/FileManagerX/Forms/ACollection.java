package com.FileManagerX.Forms;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 查看/修改集合的工具，包括 add 和 del 两种操作。
 * 
 * @author ozxdno
 *
 */
public class ACollection extends javax.swing.JFrame {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection collection = new ArrayImpl();
	public Object source;
	public String field;
	
	public java.util.List<String> list = new java.util.LinkedList<>();
	public int index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCollection(Object collection) {
		if(collection instanceof com.FileManagerX.Interfaces.ICollection<?>) {
			this.collection = new MyCollectionImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof java.util.Map<?, ?>) {
			this.collection = new MapImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof java.util.List<?>) {
			this.collection = new ListImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof java.util.Set<?>) {
			this.collection = new SetImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof boolean[]) {
			this.collection = new BooleanArrayImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof byte[]) {
			this.collection = new ByteArrayImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof char[]) {
			this.collection = new CharArrayImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof int[]) {
			this.collection = new IntegerArrayImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof long[]) {
			this.collection = new LongArrayImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		if(collection instanceof double[]) {
			this.collection = new DoubleArrayImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		}
		try {
			Object[] os = (Object[])collection;
			this.collection = new ArrayImpl();
			this.collection.setCollection(collection);
			this.collection.setResult(this.collection.first());
			this.collection.setIndex(0);
			this.currentItem();
			return;
		} catch(Exception e) {
			;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public javax.swing.JTextField jItem = new javax.swing.JTextField();
	public javax.swing.JTextField jIndex = new javax.swing.JTextField();
	public javax.swing.JTextField jField = new javax.swing.JTextField();
	public javax.swing.JTextField jValue = new javax.swing.JTextField();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ACollection() {
		this.initThis();
		this.initItem();
		this.initIndex();
		this.initField();
		this.initValue();
	}
	private void initThis() {
		this.add(jItem);
		this.add(jIndex);
		this.add(jField);
		this.add(jValue);
		
		String title = "FileManagerX" + " [" + com.FileManagerX.Globals.Configurations.This_MachineIndex + "]" +
				" - " + 
				com.FileManagerX.Globals.Configurations.MachineType.toString() + " : " + 
				"ACollection";
		
		this.setLayout(null);
		this.setTitle(title);
		this.setSize(600, 130);
		this.setLocationRelativeTo(null);//窗口居中
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);//窗口可见
		this.addComponentListener(listenFrameResize);
	}
	private void initItem() {
		jItem.addKeyListener(listenItem);
		jItem.setLocation(10, 10);
		jItem.setSize(360, 30);
	}
	private void initIndex() {
		jIndex.addKeyListener(listenIndex);
		jIndex.setLocation(10, 50);
		jIndex.setSize(20, 30);
		jIndex.setText("0");
	}
	private void initField() {
		jField.addKeyListener(listenField);
		jField.setLocation(10, 50);
		jField.setSize(40, 30);
	}
	private void initValue() {
		jValue.addKeyListener(listenValue);
		jValue.setLocation(10, 50);
		jValue.setSize(40, 30);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void resizeForm() {
		int wForm = this.getWidth();
		int hForm = this.getHeight();
		
		int wItem = wForm - 40;
		int hItem = 30;
		int xItem = 10;
		int yItem = 10;
		
		double rate1 = 0.2;
		int wIndex = (int)(rate1*wItem) + 1;
		for(int i=0; i<10; i++) { if(--wIndex <= 1) { break; } }
		
		double rate2 = 0.4;
		int wField = (int)(rate2*wItem) + 1;
		for(int i=0; i<10; i++) { if(--wField <= 1) { break; } }
		
		double rate3 = 0.4;
		int wValue = (int)(rate3*wItem) + 1;
		for(int i=0; i<10; i++) { if(--wValue <= 1) { break; } }
		
		int hIndex = hItem;
		int xIndex = xItem;
		int yIndex = yItem + hItem + yItem;
		
		int hValue = hItem;
		int xValue = xItem + wItem - wValue;
		int yValue = yIndex;
		
		int space = wItem - wIndex - wField - wValue;
		int hField = hItem;
		int xField = xItem + wIndex + space/2;
		int yField = yIndex;
		
		jIndex.setLocation(xIndex, yIndex);
		jIndex.setSize(wIndex, hIndex);
		jItem.setLocation(xItem, yItem);
		jItem.setSize(wItem, hItem);
		jField.setLocation(xField, yField);
		jField.setSize(wField, hField);
		jValue.setLocation(xValue, yValue);
		jValue.setSize(wValue, hValue);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ComponentAdapter listenFrameResize = new ComponentAdapter() {
		public void componentResized(ComponentEvent e){
			ACollection.this.resizeForm();
		}
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private KeyListener listenItem = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_UP) {
				ACollection.this.previousItem();
			}
			if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				ACollection.this.nextItem();
			}
		}
		public void keyTyped(KeyEvent event) {
			;
		}
		public void keyPressed(KeyEvent event) {
			;
		}
	};
	
	private KeyListener listenIndex = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_UP) {
				ACollection.this.previousItem();
			}
			if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				ACollection.this.nextItem();
			}
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				ACollection.this.currentItem();;
			}
		}
		public void keyTyped(KeyEvent event) {
			;
		}
		public void keyPressed(KeyEvent event) {
			;
		}
	};
	
	private KeyListener listenField = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			jValue.setText("");
			
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				ACollection.this.currentField();
			}
			if(event.getKeyCode() == KeyEvent.VK_UP) {
				ACollection.this.previousField();
			}
			if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				ACollection.this.nextField();
			}
			if(event.getKeyCode() == 46) {
				ACollection.this.hintField();
			}
		}
		public void keyTyped(KeyEvent event) {
			;
		}
		public void keyPressed(KeyEvent event) {
			;
		}
	};
	
	private KeyListener listenValue = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				ACollection.this.resetValue(jValue.getText());
			}
		}
		public void keyTyped(KeyEvent event) {
			;
		}
		public void keyPressed(KeyEvent event) {
			;
		}
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initList() {
		list.clear();
		if(this.collection.getResult() == null) { list.add("No Fields"); return; }
		list.add("Item");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void previousItem() {
		if(this.collection.size() == 0) { 
			jItem.setText("Collection is Empty");
			return;
		}
		
		this.collection.setResult(this.collection.previous());
		Object result = this.collection.getResult();
		int index = this.collection.getIndex();
		String res = "[" + index + "] " + (result == null ? "NULL" : result.toString());
		jItem.setText(res);
		jIndex.setText("" + index);
	}
	private void nextItem() {
		if(this.collection.size() == 0) { 
			jItem.setText("Collection is Empty");
			return;
		}
		
		this.collection.setResult(this.collection.next());
		Object result = this.collection.getResult();
		int index = this.collection.getIndex();
		String res = "[" + index + "] " + (result == null ? "NULL" : result.toString());
		jItem.setText(res);
		jIndex.setText("" + index);
	}
	private void currentItem() {
		if(this.collection.size() == 0) { 
			jItem.setText("Collection is Empty");
			return;
		}
		
		this.collection.setResult(this.collection.get(this.collection.getIndex()));
		Object result = this.collection.getResult();
		int index = this.collection.getIndex();
		String res = "[" + index + "] " + (result == null ? "NULL" : result.toString());
		jItem.setText(res);
		jIndex.setText("" + index);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void previousField() {
		if(jField.getText().isEmpty()) { this.initList(); }
		if(list.isEmpty()) { return; }
		index--;
		if(index < 0) { index = list.size(); }
		if(index >= list.size()) { index = list.size()-1; }
		jField.setText(list.get(index));
	}
	private void nextField() {
		if(jField.getText().isEmpty()) { this.initList(); }
		if(list.isEmpty()) { return; }
		index++;
		if(index < 0) { index = 0; }
		if(index >= list.size()) { index = 0; }
		jField.setText(list.get(index));
	}
	private void currentField() {
		if(jField.getText().isEmpty()) { this.initList(); }
		if(list.isEmpty()) { return; }
		Object res = this.fetchValue(jField.getText());
		jValue.setText(res == null ? "NULL" : res.toString());
	}
	
	private void hintField() {
		String fstr = jField.getText();
		String ustr = fstr.substring(0, fstr.length()-1);
		Object upper = this.fetchValue(ustr);
		if(upper == null) {
			jValue.setText("Error: NULL has No Fields");
			return;
		}
		
		com.FileManagerX.BasicEnums.DataType type = com.FileManagerX.Tools.Reflect.getType(upper);
		if(!com.FileManagerX.BasicEnums.DataType.OTHERS.equals(type)) {
			jValue.setText("Error: Base Data Type has No Fields");
			return;
		}
		
		java.lang.reflect.Field[] fields = com.FileManagerX.Tools.Reflect.getFields(upper);
		if(fields.length == 0) {
			jValue.setText("Error: No Fields");
			return;
		}
		
		list.clear();
		for(java.lang.reflect.Field f : fields) {
			list.add(ustr + "." + f.getName());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Object fetchValue(String field) {
		if(this.collection.getResult() == null) { return null; }
		java.util.List<String> ps = com.FileManagerX.Tools.StringUtil.split2string(field, ".");
		if(ps.isEmpty()) { return null; }
		ps.remove(0);
		if(ps.isEmpty()) { return this.collection.getResult(); }
		
		try {
			Object temp = this.collection.getResult();
			while(!ps.isEmpty()) {
				String f = ps.remove(0);
				temp = com.FileManagerX.Tools.Reflect.getField(f, temp);
			}
			return temp;
		} catch(Exception e) {
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void resetValue(String value) {
		if(value.length() >= 3 && value.substring(0, 3).equals("add")) {
			String className = value.substring(3);
			if(className.length() == 0) { className = null; }
			this.collection.add(className);
			return;
		}
		if(value.length() >= 3 && value.substring(0, 3).equals("del")) {
			this.collection.del(com.FileManagerX.Tools.Parse.parseInt(jIndex.getText()));
			return;
		}
		if(value.equals("save")) {
			com.FileManagerX.Tools.Reflect.setFeild(field, collection.getResult(), source);
			return;
		}
		if(jField.getText().toLowerCase().equals("item")) {
			com.FileManagerX.BasicEnums.DataType type = com.FileManagerX.Tools.Reflect.getType(this.collection.getTClass());
			Object item = com.FileManagerX.Tools.Parse.parseAny(value, type, this.collection.getTClass());
			this.collection.set(this.collection.getIndex(), item);
			return;
		}
		
		String fstr = jField.getText();
		int idx = fstr.lastIndexOf('.');
		if(idx < 0) { 
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		String ustr = fstr.substring(0, idx);
		Object result = this.fetchValue(fstr);
		Object upper = this.fetchValue(ustr);
		if(upper == null) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		com.FileManagerX.BasicEnums.DataType type = com.FileManagerX.Tools.Reflect.getType(result);
		Class<?> c = result == null ? null : result.getClass();
		result = com.FileManagerX.Tools.Parse.parseAny(value, type, c);
		
		String name = fstr.substring(idx+1);
		boolean ok = com.FileManagerX.Tools.Reflect.setFeild(name, result, upper);
		if(!ok) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		String res = result == null ? "NULL" : result.toString();
		jValue.setText("OK: " + res);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private interface Collection {
		public void setCollection(Object collection);
		public void setResult(Object result);
		public void setIndex(int index);
		
		public Object getCollection();
		public Object getResult();
		public int getIndex();
		public Class<?> getTClass();
		
		public Object previous();
		public Object next();
		public Object first();
		public Object last();
		
		public int size();
		
		public void add(String className);
		public void del(int index);
		
		public Object get(int index);
		public boolean set(int index, Object item);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class MyCollectionImpl implements Collection {
		public com.FileManagerX.Interfaces.ICollection<?> collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (com.FileManagerX.Interfaces.ICollection<?>)collection;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public com.FileManagerX.Interfaces.ICollection<?> getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.size() == 0) { return null; }
			index--;
			if(index < 0) { index = collection.size()-1; }
			if(index >= collection.size()) { index = collection.size()-1; }
			return collection.searchByCount(index);
		}
		public Object next() {
			if(collection == null || collection.size() == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.size()) { index = 0; }
			return collection.searchByCount(index);
		}
		public Object first() {
			return collection == null ? null : collection.fetchByCount(0);
		}
		public Object last() {
			return collection == null ? null : collection.fetchByCount(collection.size()-1);
		}
		
		public int size() {
			return collection == null ? 0 : collection.size();
		}
		
		public void add(String className) {
			if(clazz == null) { clazz = collection == null ? null : collection.getTClass(); }
			if(clazz == null) {
				if(className == null) {
					if(this.size() > 0) { clazz = this.first().getClass(); }
				}
				else {
					try { clazz = Class.forName(className); } catch(Exception e) {}
				}
			}
			
			if(clazz == null) { 
				jValue.setText("Failed: Wrong Class Name");
				return;
			}
			
			try {
				Object item = clazz.newInstance();
				com.FileManagerX.Tools.Reflect.executeMethod("add", collection, item);
			} catch(Exception e) {
				jValue.setText("Failed: Create New Instance Failed");
				return;
			}
			
			jValue.setText("OK: " + jValue.getText());
		}
		public void del(int index) {
			if(collection != null) { collection.fetchByCount(index); }
		}
		
		public Object get(int index) {
			return collection == null ? null : collection.searchByCount(index);
		}
		public boolean set(int index, Object item) {
			return false;
		}
	}
	private class ListImpl implements Collection {
		public java.util.List<?> collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (java.util.List<?>)collection;
			this.clazz = this.size() == 0 ? null : this.first().getClass();
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public java.util.List<?> getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.size() == 0) { return null; }
			index--;
			if(index < 0) { index = collection.size()-1; }
			if(index >= collection.size()) { index = collection.size()-1; }
			return collection.get(index);
		}
		public Object next() {
			if(collection == null || collection.size() == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.size()) { index = 0; }
			return collection.get(index);
		}
		public Object first() {
			return collection == null ? null : collection.get(0);
		}
		public Object last() {
			return collection == null ? null : collection.get(collection.size()-1);
		}
		
		public int size() {
			return collection == null ? 0 : collection.size();
		}
		
		public void add(String className) {
			if(className == null) {
				if(collection.size() > 0) { clazz = collection.get(0).getClass(); }
			}
			else {
				try { clazz = Class.forName(className); } catch(Exception e) {}
			}
			
			try {
				Object item = clazz.newInstance();
				com.FileManagerX.Tools.Reflect.executeMethod("add", collection, item);
			} catch(Exception e) {
				jValue.setText("Failed: Create New Instance Failed");
				return;
			}
			
			jValue.setText("OK: " + jValue.getText());
		}
		public void del(int index) {
			if(collection != null) { collection.remove(index); }
		}
		
		public Object get(int index) {
			return collection == null ? null : collection.get(index);
		}
		public boolean set(int index, Object item) {
			if(collection == null) { return false; }
			if(index < 0) { return false; }
			if(index >= this.size()) { return false; }
			java.lang.reflect.Method m = com.FileManagerX.Tools.Reflect.getMethod(
					"set", collection, int.class, Object.class
				);
			com.FileManagerX.Tools.Reflect.executeMethod(m, collection, index, item);
			return true;
		}
	}
	
	private class SetImpl implements Collection {
		public java.util.Set<?> collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (java.util.Set<?>)collection;
			this.clazz = this.size() == 0 ? null : this.first().getClass();
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public java.util.Set<?> getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.size() == 0) { return null; }
			index--;
			if(index < 0) { index = collection.size()-1; }
			if(index >= collection.size()) { index = collection.size()-1; }
			return this.get(index);
		}
		public Object next() {
			if(collection == null || collection.size() == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.size()) { index = 0; }
			return this.get(index);
		}
		public Object first() {
			return collection == null ? null : this.get(0);
		}
		public Object last() {
			return collection == null ? null : this.get(collection.size()-1);
		}
		
		public int size() {
			return collection == null ? 0 : collection.size();
		}
		
		public void add(String className) {
			if(className == null) {
				if(collection.size() > 0) { clazz = this.get(0).getClass(); }
			}
			else {
				try { clazz = Class.forName(className); } catch(Exception e) {}
			}
			
			try {
				Object item = clazz.newInstance();
				com.FileManagerX.Tools.Reflect.executeMethod("add", collection, item);
			} catch(Exception e) {
				jValue.setText("Failed: Create New Instance Failed");
				return;
			}
			
			jValue.setText("OK: " + jValue.getText());
		}
		public void del(int index) {
			if(collection == null) { return; }
			java.util.Iterator<?> it = collection.iterator();
			int cnt = 0;
			while(it.hasNext()) { if(cnt++ == index) { it.next(); it.remove(); } }
		}
		
		public Object get(int index) {
			if(collection == null) { return null; }
			if(index < 0 || index >= collection.size()) { return null; }
			
			int cnt = 0;
			for(Object item : collection) { if(cnt++ == index) { return item; } }
			return null;
		}
		public boolean set(int index, Object item) {
			return false;
		}
	}
	private class MapImpl implements Collection {
		public java.util.Map<?, ?> collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (java.util.Map<?, ?>)collection;
			this.clazz = this.size() == 0 ? null : this.first().getClass();
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public java.util.Map<?, ?> getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.size() == 0) { return null; }
			index--;
			if(index < 0) { index = collection.size()-1; }
			if(index >= collection.size()) { index = collection.size()-1; }
			return this.get(index);
		}
		public Object next() {
			if(collection == null || collection.size() == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.size()) { index = 0; }
			return this.get(index);
		}
		public Object first() {
			return collection == null ? null : this.get(0);
		}
		public Object last() {
			return collection == null ? null : this.get(collection.size()-1);
		}
		
		public int size() {
			return collection == null ? 0 : collection.size();
		}
		
		public void add(String className) {
			if(className == null) {
				if(collection.size() > 0) { clazz = this.get(0).getClass(); }
			}
			else {
				try { clazz = Class.forName(className); } catch(Exception e) {}
			}
			
			try {
				Object item = clazz.newInstance();
				com.FileManagerX.Tools.Reflect.executeMethod("add", collection, item);
			} catch(Exception e) {
				jValue.setText("Failed: Create New Instance Failed");
				return;
			}
			
			jValue.setText("OK: " + jValue.getText());
		}
		public void del(int index) {
			if(collection == null) { return; }
			java.util.Iterator<?> it = collection.entrySet().iterator();
			int cnt = 0;
			while(it.hasNext()) { if(cnt++ == index) { it.next(); it.remove(); } }
		}
		
		public Object get(int index) {
			if(collection == null) { return null; }
			if(index < 0 || index >= collection.size()) { return null; }
			
			int cnt = 0;
			for(Object item : collection.values()) { if(cnt++ == index) { return item; } }
			return null;
		}
		public boolean set(int index, Object item) {
			if(collection == null) { return false; }
			if(index < 0 || index >= collection.size()) { return false; }
			
			Object key = null;
			int cnt = 0;
			for(Object k : collection.keySet()) { if(cnt++ == index) { key = k; break; } }
			
			com.FileManagerX.Tools.Reflect.executeMethod("put", collection, key, item);
			return true;
		}
	}
	private class ArrayImpl implements Collection {
		public Object[] collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (Object[])collection;
			clazz = this.size() == 0 ? null : this.collection[0].getClass();
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public Object[] getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.length == 0) { return null; }
			index--;
			if(index < 0) { index = collection.length-1; }
			if(index >= collection.length) { index = collection.length-1; }
			return collection[index];
		}
		public Object next() {
			if(collection == null || collection.length == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.length) { index = 0; }
			return collection[index];
		}
		public Object first() {
			return this.size() == 0 ? null : collection[0];
		}
		public Object last() {
			return this.size() == 0 ? null : collection[collection.length-1];
		}
		
		public int size() {
			return this.collection == null ? 0 : collection.length;
		}
		
		public void add(String className) {
			jValue.setText("Failed: " + jValue.getText());
		}
		public void del(int index) {
			;
		}
		
		public Object get(int index) {
			return collection[index];
		}
		public boolean set(int index, Object item) {
			if(index < 0 || index >= this.size()) { return true; }
			collection[index] = item;
			return true;
		}
	}
	private class BooleanArrayImpl implements Collection {
		public boolean[] collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (boolean[])collection;
			clazz = boolean.class;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public boolean[] getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.length == 0) { return null; }
			index--;
			if(index < 0) { index = collection.length-1; }
			if(index >= collection.length) { index = collection.length-1; }
			return collection[index];
		}
		public Object next() {
			if(collection == null || collection.length == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.length) { index = 0; }
			return collection[index];
		}
		public Object first() {
			return this.size() == 0 ? null : collection[0];
		}
		public Object last() {
			return this.size() == 0 ? null : collection[collection.length-1];
		}
		
		public int size() {
			return this.collection == null ? 0 : collection.length;
		}
		
		public void add(String className) {
			jValue.setText("Failed: " + jValue.getText());
		}
		public void del(int index) {
			;
		}
		
		public Object get(int index) {
			return collection[index];
		}
		public boolean set(int index, Object item) {
			if(index < 0 || index >= this.size()) { return true; }
			collection[index] = (boolean)item;
			return true;
		}
	}
	private class ByteArrayImpl implements Collection {
		public byte[] collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (byte[])collection;
			clazz = byte.class;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public byte[] getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.length == 0) { return null; }
			index--;
			if(index < 0) { index = collection.length-1; }
			if(index >= collection.length) { index = collection.length-1; }
			return collection[index];
		}
		public Object next() {
			if(collection == null || collection.length == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.length) { index = 0; }
			return collection[index];
		}
		public Object first() {
			return this.size() == 0 ? null : collection[0];
		}
		public Object last() {
			return this.size() == 0 ? null : collection[collection.length-1];
		}
		
		public int size() {
			return this.collection == null ? 0 : collection.length;
		}
		
		public void add(String className) {
			jValue.setText("Failed: " + jValue.getText());
		}
		public void del(int index) {
			;
		}
		
		public Object get(int index) {
			return collection[index];
		}
		public boolean set(int index, Object item) {
			if(index < 0 || index >= this.size()) { return true; }
			collection[index] = (byte)item;
			return true;
		}
	}
	private class CharArrayImpl implements Collection {
		public char[] collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (char[])collection;
			clazz = char.class;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public char[] getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.length == 0) { return null; }
			index--;
			if(index < 0) { index = collection.length-1; }
			if(index >= collection.length) { index = collection.length-1; }
			return collection[index];
		}
		public Object next() {
			if(collection == null || collection.length == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.length) { index = 0; }
			return collection[index];
		}
		public Object first() {
			return this.size() == 0 ? null : collection[0];
		}
		public Object last() {
			return this.size() == 0 ? null : collection[collection.length-1];
		}
		
		public int size() {
			return this.collection == null ? 0 : collection.length;
		}
		
		public void add(String className) {
			jValue.setText("Failed: " + jValue.getText());
		}
		public void del(int index) {
			;
		}
		
		public Object get(int index) {
			return collection[index];
		}
		public boolean set(int index, Object item) {
			if(index < 0 || index >= this.size()) { return true; }
			collection[index] = (char)item;
			return true;
		}
	}
	private class IntegerArrayImpl implements Collection {
		public int[] collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (int[])collection;
			clazz = int.class;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public int[] getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.length == 0) { return null; }
			index--;
			if(index < 0) { index = collection.length-1; }
			if(index >= collection.length) { index = collection.length-1; }
			return collection[index];
		}
		public Object next() {
			if(collection == null || collection.length == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.length) { index = 0; }
			return collection[index];
		}
		public Object first() {
			return this.size() == 0 ? null : collection[0];
		}
		public Object last() {
			return this.size() == 0 ? null : collection[collection.length-1];
		}
		
		public int size() {
			return collection == null ? 0 : collection.length;
		}
		
		public void add(String className) {
			jValue.setText("Failed: " + jValue.getText());
		}
		public void del(int index) {
			;
		}
		
		public Object get(int index) {
			return collection[index];
		}
		public boolean set(int index, Object item) {
			if(index < 0 || index >= this.size()) { return true; }
			collection[index] = (int)item;
			return true;
		}
	}
	private class LongArrayImpl implements Collection {
		public long[] collection;
		public Class<?> clazz;
		public Object result;
		public int index;

		public void setCollection(Object collection) {
			this.collection = (long[])collection;
			clazz = long.class;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public long[] getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}

		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.length == 0) { return null; }
			index--;
			if(index < 0) { index = collection.length-1; }
			if(index >= collection.length) { index = collection.length-1; }
			return collection[index];
		}
		public Object next() {
			if(collection == null || collection.length == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.length) { index = 0; }
			return collection[index];
		}
		public Object first() {
			return this.size() == 0 ? null : collection[0];
		}
		public Object last() {
			return this.size() == 0 ? null : collection[collection.length-1];
		}
		
		public int size() {
			return collection == null ? 0 : collection.length;
		}
		
		public void add(String className) {
			jValue.setText("Failed: " + jValue.getText());
		}
		public void del(int index) {
			;
		}
		
		public Object get(int index) {
			return collection[index];
		}
		public boolean set(int index, Object item) {
			if(index < 0 || index >= this.size()) { return true; }
			collection[index] = (long)item;
			return true;
		}
	}
	private class DoubleArrayImpl implements Collection {
		public double[] collection;
		public Class<?> clazz;
		public Object result;
		public int index;
		
		public void setCollection(Object collection) {
			this.collection = (double[])collection;
			clazz = double.class;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		public double[] getCollection() {
			return this.collection;
		}
		public Object getResult() {
			return this.result;
		}
		public int getIndex() {
			return this.index;
		}
		
		public Class<?> getTClass() {
			return this.clazz;
		}
		
		public Object previous() {
			if(collection == null || collection.length == 0) { return null; }
			index--;
			if(index < 0) { index = collection.length-1; }
			if(index >= collection.length) { index = collection.length-1; }
			return collection[index];
		}
		public Object next() {
			if(collection == null || collection.length == 0) { return null; }
			index++;
			if(index < 0) { index = 0; }
			if(index >= collection.length) { index = 0; }
			return collection[index];
		}
		public Object first() {
			return this.size() == 0 ? null : collection[0];
		}
		public Object last() {
			return this.size() == 0 ? null : collection[collection.length-1];
		}
		
		public int size() {
			return collection == null ? 0 : collection.length;
		}
		
		public void add(String className) {
			jValue.setText("Failed: " + jValue.getText());
		}
		public void del(int index) {
			;
		}
		
		public Object get(int index) {
			return collection[index];
		}
		public boolean set(int index, Object item) {
			if(index < 0 || index >= this.size()) { return true; }
			collection[index] = (double)item;
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
