package com.FileManagerX.MyNet;

public class Manager {
	
	private Net net;

	public boolean setNet(Net net) {
		this.net = net;
		
		return true;
	}
	public boolean setNet() {
		this.net = com.FileManagerX.Globals.Datas.MyNet;
		return true;
	}
	
	public Net getNet() {
		return net;
	}
	
	public Manager() {
		initThis();
	}
	private void initThis() {
		net = null;
	}
	
}
