package com.FileManagerX.Forms;

public class CMDC extends javax.swing.JFrame {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public javax.swing.JTextField jResult = new javax.swing.JTextField();
	public javax.swing.JTextField jField = new javax.swing.JTextField();
	public javax.swing.JTextField jValue = new javax.swing.JTextField();
	public javax.swing.JButton jExecute = new javax.swing.JButton("execute");
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CMDC() {
		this.initThis();
		this.initResult();
		this.initField();
		this.initValue();
		this.initExecute();
	}
	private void initThis() {
		this.add(jResult);
		this.add(jField);
		this.add(jValue);
		this.add(jExecute);
		
		this.setLayout(null);
		this.setTitle("CMDC");
		this.setSize(600, 180);
		this.setLocationRelativeTo(null);//窗口居中
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(false);//窗口可见
		//this.addComponentListener(listenFrameResize);
	}
	private void initResult() {
		jResult.setLocation(10, 10);
		jResult.setSize(360, 30);
	}
	private void initField() {
		jField.setLocation(10, 50);
		jField.setSize(100, 25);
	}
	private void initValue() {
		jValue.setLocation(10, 50);
		jValue.setSize(100, 25);
	}
	private void initExecute() {
		jExecute.setLocation(10, 50);
		jExecute.setSize(100, 25);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
