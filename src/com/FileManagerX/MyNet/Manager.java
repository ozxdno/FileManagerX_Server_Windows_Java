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
	
	public void addToHide(User user,Machine machine) {
		if(net == null) { return; }
		
		Group hide = com.FileManagerX.Factories.MyNetFactory.createHideGroup(net);
		User u = hide.findUser(user.getName());
		if(u == null) {
			if(hide.getUsers().size() > hide.getLimit()) {
				hide.getUsers().remove(0);
			}
			hide.addUser(user);
		}
		else {
			hide.getUsers().remove(u);
			hide.addUser(u);
			user = u;
		}
		
		Machine m = user.findMachine(machine.getName());
		if(m == null) {
			
		}
		
	}
	
}
