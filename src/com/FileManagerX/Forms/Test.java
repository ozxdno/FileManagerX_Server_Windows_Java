package com.FileManagerX.Forms;

import java.awt.event.*;
import javax.swing.*;

import com.FileManagerX.Globals.*;

@SuppressWarnings("serial")
public class Test extends JFrame {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public AError aError = null;
	public ARecord aRecord = null;
	public CMDC cmdc = null;
	public REPC repc = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String[] cmds = new String[] { 
			"CMDC",
			"REPC", 
			"Send",
			"Receive",
			"Copy", 
			"Copy And Send",
			"Search",
			"Fetch"
		};
	public int showAt;
	
	public boolean fetch = false;
	public long index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public JTextField jInput = new JTextField();
	public JTextField jResult = new JTextField();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Test() {
		this.initThis();
        this.initInput();
        this.initResult();
    }
	private void initInput() {
		jInput.addKeyListener(listenInput);
		jInput.setLocation(10, 10);
		jInput.setSize(360, 30);
	}
	private void initResult() {
		jResult.addKeyListener(listenResult);
		jResult.setLocation(10, 50);
		jResult.setSize(360, 30);
	}
	private void initThis() {
		this.add(jInput);
		this.add(jResult);
		
		this.setLayout(null);
		this.setTitle("FileManagerX");
		this.setSize(600, 130);
		this.setLocationRelativeTo(null);//窗口居中
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(false);//窗口可见
		this.addComponentListener(listenFrameResize);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unused")
	public void resizeForm() {
		if(Datas.Form_Test == null) { return; }
		
		int wForm = Test.this.getSize().width;
		int hForm = Datas.Form_Test.getSize().height;
		
		int wInput = wForm - 40;
		int hInput = 30;
		int xInput = 10;
		int yInput = 10;
		
		int wResult = wInput;
		int hResult = hInput;
		int xResult = xInput;
		int yResult = yInput + hInput + yInput;
		
		jInput.setLocation(xInput, yInput);
		jInput.setSize(wInput, hInput);
		jResult.setLocation(xResult, yResult);
		jResult.setSize(wResult, hResult);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ComponentAdapter listenFrameResize = new ComponentAdapter() {
		public void componentResized(ComponentEvent e){
			Test.this.resizeForm();
		}
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private KeyListener listenInput = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				boolean ok = Test.this.send(jInput.getText());
				String res = ok ? "OK: " : "Failed: ";
				jInput.setText(res + jInput.getText());
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

	private KeyListener listenResult = new KeyListener() {
		public void keyReleased(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				// CMDC
				if(jResult.getText().toLowerCase().equals("cmdc")) {
					if(cmdc == null) { cmdc = new CMDC(); }
					if(!cmdc.isVisible()) { cmdc.setVisible(true); }
					cmdc.setFocusable(true);
					return;
				}
				// REPC
				if(jResult.getText().toLowerCase().equals("repc")) {
					com.FileManagerX.Interfaces.IReply reply = Test.this.receive();
					String res = reply == null ? "NULL" : reply.toString();
					jResult.setText("Receive: " + res);
					
					if(repc == null) { repc = new REPC(); }
					if(!repc.isVisible()) { repc.setVisible(true); }
					repc.setFocusable(true);
					repc.setSource(reply);
					return;
				}
				// SEND
				if(jResult.getText().toLowerCase().equals("send")) {
					boolean ok = Test.this.send(jInput.getText());
					String res = ok ? "OK: " : "Failed: ";
					jInput.setText(res + jInput.getText());
				}
				// RECE
				if(jResult.getText().toLowerCase().equals("receive")) {
					com.FileManagerX.Interfaces.IReply reply = Test.this.receive();
					String res = reply == null ? "NULL" : reply.toString();
					jResult.setText("Receive: " + res);
					return;
				}
				
				
				// COPY
				if(jResult.getText().toLowerCase().equals("copy")) {
					String cmd = cmdc == null ? "NULL" :
						(cmdc.source == null ? "NULL" : cmdc.source.toString());
					jInput.setText(cmd);
					return;
				}
				// COPY AND SEND
				if(jResult.getText().toLowerCase().equals("copy and send")) {
					String cmd = cmdc == null ? "NULL" :
						(cmdc.source == null ? "NULL" : cmdc.source.toString());
					jInput.setText(cmd);
					
					boolean ok = Test.this.send(jInput.getText());
					String res = ok ? "OK: " : "Failed: ";
					jInput.setText(res + jInput.getText());
					return;
				}
				
				// SEARCH
				if(jResult.getText().toLowerCase().equals("search")) {
					fetch = false;
					jResult.setText("Search: OK");
					return;
				}
				
				// FETCH
				if(jResult.getText().toLowerCase().equals("fetch")) {
					fetch = true;
					jResult.setText("Fetch: OK");
					return;
				}
			}
			if(event.getKeyCode() == KeyEvent.VK_UP) {
				Test.this.previous();
			}
			if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				Test.this.next();
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

	public void previous() {
		showAt--;
		if(showAt < 0) { showAt = cmds.length-1; }
		if(showAt >= cmds.length) { showAt = cmds.length-1; }
		jResult.setText(cmds[showAt]);
	}
	public void next() {
		showAt++;
		if(showAt < 0) { showAt = 0; }
		if(showAt >= cmds.length) { showAt = 0; }
		jResult.setText(cmds[showAt]);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean send(String s) {
		if(s == null || s.isEmpty()) { return false; }
		String name = com.FileManagerX.Tools.StringUtil.getField_FV(s);
		Class<?> c = com.FileManagerX.Globals.Datas.Commands.get(name);
		if(c == null) { return false; }
		
		try {
			com.FileManagerX.Interfaces.ITransport t = (com.FileManagerX.Interfaces.ITransport)c.newInstance();
			index = t.getBasicMessagePackage().getIndex();
			fetch = false;
			
			com.FileManagerX.BasicModels.Config config = t.input(jInput.getText());
			if(!config.getIsOK()) { return false; }
			t.getBasicMessagePackage().setIndex(index);
			return config.getIsOK() && t.send();
		} catch (Exception e) {
			return false;
		}
	}
	public com.FileManagerX.Interfaces.IReply receive() {
		return fetch ? com.FileManagerX.Globals.Datas.Receiver.fetchByKey(index) :
			com.FileManagerX.Globals.Datas.Receiver.searchByKey(index);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}


