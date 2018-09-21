package com.FileManagerX.Forms;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class REPC extends javax.swing.JFrame {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public javax.swing.JTextField jResult = new javax.swing.JTextField();
	public javax.swing.JTextField jField = new javax.swing.JTextField();
	public javax.swing.JTextField jValue = new javax.swing.JTextField();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private java.util.List<String> list = new java.util.ArrayList<>();
	private String initTitle;
	private Object source;
	private Object result;
	private Object upper;
	private int index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setSource(com.FileManagerX.Interfaces.IReply source) {
		if(source == null) {
			this.source = null;
			this.result = null;
			this.upper = null;
			this.index = 0;
			jResult.setText("NULL");
			jField.setText("No Fields");
			jValue.setText("NULL");
			return;
		}
		
		this.source = source;
		this.result = source;
		this.upper = null;
		this.index = 0;
		jResult.setText(this.source.toString());
		jField.setText(this.source.getClass().getSimpleName());
		jValue.setText(this.source.toString());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public REPC() {
		this.initThis();
		this.initResult();
		this.initField();
		this.initValue();
	}
	private void initThis() {
		this.add(jResult);
		this.add(jField);
		this.add(jValue);
		
		this.initTitle = "FileManagerX" + " [" + com.FileManagerX.Globals.Configurations.This_MachineIndex + "]" +
				" - " + 
				com.FileManagerX.Globals.Configurations.MachineType.toString() + " : " + 
				"REPC";
		
		this.setLayout(null);
		this.setTitle(this.initTitle);
		this.setSize(600, 130);
		this.setLocationRelativeTo(null);//窗口居中
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);//窗口可见
		this.addComponentListener(listenFrameResize);
	}
	private void initResult() {
		jResult.addKeyListener(listenResult);
		jResult.setLocation(10, 10);
		jResult.setSize(360, 30);
	}
	private void initField() {
		jField.addKeyListener(listenField);
		jField.setLocation(10, 50);
		jField.setSize(150, 30);
	}
	private void initValue() {
		jValue.addKeyListener(listenValue);
		jValue.setLocation(10, 50);
		jValue.setSize(150, 30);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unused")
	public void resizeForm() {
		int wForm = this.getWidth();
		int hForm = this.getHeight();
		
		int wResult = wForm - 40;
		int hResult = 30;
		int xResult = 10;
		int yResult = 10;
		
		double rate1 = 0.5;
		int wField = (int)(wResult*rate1) + 1;
		for(int i=0; i<10; i++) { if(--wField <= 1) { break; } }
		int hField = hResult;
		int xField = xResult;
		int yField = yResult + hResult + yResult;
		
		double rate2 = 0.5;
		int wValue = (int)(wResult*rate2) + 1;
		for(int i=0; i<10; i++) { if(--wValue <= 1) { break; } }
		int space = wResult - wField - wValue;
		int hValue = hResult;
		int xValue = xResult + wField + space;
		int yValue = yField;
		
		jResult.setLocation(xResult, yResult);
		jResult.setSize(wResult, hResult);
		jField.setLocation(xField, yField);
		jField.setSize(wField, hField);
		jValue.setLocation(xValue, yValue);
		jValue.setSize(wValue, hValue);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ComponentAdapter listenFrameResize = new ComponentAdapter() {
		public void componentResized(ComponentEvent e){
			REPC.this.resizeForm();
		}
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private KeyListener listenResult = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			;
		}
		public void keyTyped(KeyEvent event) {
			;
		}
		public void keyPressed(KeyEvent event) {
			;
		}
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private KeyListener listenField = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			jValue.setText("");
			
			if(event.getKeyCode() == KeyEvent.VK_UP) {
				REPC.this.previous();
			}
			if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				REPC.this.next();
			}
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				REPC.this.print();
			}
			if(event.getKeyCode() == 46) {
				REPC.this.hint();
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

	private KeyListener listenValue = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				REPC.this.resetValue(jValue.getText());
				REPC.this.result();
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
		source = null;
		list.clear();
		index = 0;
		for(String s : com.FileManagerX.Globals.Datas.Replies.keySet()) { list.add(s); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void previous() {
		if(jField.getText().isEmpty()) { this.initList(); }
		if(list == null || list.size() == 0) { return; }
		index--;
		if(index < 0) { index = list.size()-1; }
		if(index >= list.size()) { index = list.size()-1; }
		jField.setText(list.get(index));
	}
	private void next() {
		if(jField.getText().isEmpty()) { this.initList(); }
		if(list == null || list.size() == 0) { return; }
		index++;
		if(index < 0) { index = 0; }
		if(index >= list.size()) { index = 0; }
		jField.setText(list.get(index));
	}
	private void current() {
		if(jField.getText().isEmpty()) { this.initList(); }
		if(list == null || list.size() == 0) { return; }
		if(index < 0) { index = 0; }
		if(index >= list.size()) { index = list.size()-1; }
		jField.setText(list.get(index));
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void hint() {
		String fstr = jField.getText();
		String ustr = fstr.substring(0, fstr.length()-1);
		this.upper = this.fetchValue(ustr);
		if(this.upper == null) {
			jValue.setText("Error: NULL has No Fields");
			return;
		}
		
		com.FileManagerX.BasicEnums.DataType type = com.FileManagerX.Tools.Reflect.getType(this.upper);
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

	private void print() {
		this.result = this.fetchValue(jField.getText());
		String res = result == null ? "NULL" : result.toString();
		jValue.setText(res);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void result() {
		String res = this.source == null ? "NULL" : this.source.toString();
		jResult.setText(res);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Object fetchValue(String field) {
		if(source == null) { return null; }
		java.util.List<String> ps = com.FileManagerX.Tools.StringUtil.split2string(field, ".");
		if(ps.isEmpty()) { return null; }
		if(!ps.get(0).equals(this.source.getClass().getSimpleName())) { return null; }
		ps.remove(0);
		if(ps.isEmpty()) { return source; }
		
		try {
			Object temp = source;
			while(!ps.isEmpty()) {
				String f = ps.remove(0);
				temp = com.FileManagerX.Tools.Reflect.getField(f, temp);
			}
			return temp;
		} catch(Exception e) {
			return null;
		}
	}
	private Object fetchUpper(String field) {
		if(source == null) { return null; }
		int idx = field.lastIndexOf('.');
		if(idx < 0) { return null; }
		return this.fetchValue(field.substring(0, idx));
	}
	private String lastField(String field) {
		int idx = field.lastIndexOf('.');
		return idx < 0 ? "" : field.substring(idx);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void resetValue(String value) {
		if(value.length() >= 3 && value.substring(0, 3).equals("new")) {
			this.createValue(value);
			return;
		}
		if(value.equals("collection")) {
			ACollection aCollection = new ACollection();
			aCollection.setCollection(this.result);
			aCollection.source = this.fetchUpper(jField.getText());
			aCollection.field = this.lastField(jField.getText());
			return;
		}
		
		String fstr = jField.getText();
		int idx = fstr.lastIndexOf('.');
		if(idx < 0) { 
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		String ustr = fstr.substring(0, idx);
		this.result = this.fetchValue(fstr);
		this.upper = this.fetchValue(ustr);
		if(this.upper == null) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		com.FileManagerX.BasicEnums.DataType type = com.FileManagerX.Tools.Reflect.getType(this.result);
		Class<?> c = this.result == null ? null : this.result.getClass();
		this.result = com.FileManagerX.Tools.Parse.parseAny(value, type, c);
		
		String name = fstr.substring(idx+1);
		boolean ok = com.FileManagerX.Tools.Reflect.setFeild(name, result, upper);
		if(!ok) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		String res = this.result == null ? "NULL" : this.result.toString();
		jValue.setText("OK: " + res);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void createValue(String value) {
		if(value.length() < 3 || !value.substring(0, 3).equals("new")) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		if(value.equals("new")) {
			try {
				this.source = com.FileManagerX.Globals.Datas.Replies.get(jField.getText()).newInstance();
				jValue.setText(this.source.toString());
				return;
			} catch(Exception e) {
				jValue.setText("Failed: " + jValue.getText());
				return;
			}
		}
		
		String fstr = jField.getText();
		int idx = fstr.lastIndexOf('.');
		if(idx < 0) { 
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		String ustr = fstr.substring(0, idx);
		this.upper = this.fetchValue(ustr);
		if(this.upper == null) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		try {
			String cname = value.substring(3);
			cname = com.FileManagerX.Tools.StringUtil.clearSepMarkLR(cname, ' ');
			this.result = Class.forName(cname).newInstance();
		} catch(Exception e) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		String name = fstr.substring(idx+1);
		boolean ok = com.FileManagerX.Tools.Reflect.setFeild(name, result, upper);
		if(!ok) {
			jValue.setText("Failed: " + jValue.getText());
			return;
		}
		
		String res = this.result == null ? "NULL" : this.result.toString();
		jValue.setText("OK: " + res);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

