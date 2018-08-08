package com.FileManagerX.Factories;

public class MyNetFactory {

	public final static com.FileManagerX.MyNet.Group createServerGroup() {
		return createServerGroup(com.FileManagerX.Globals.Datas.MyNet);
	}
	public final static com.FileManagerX.MyNet.Group createMyGroup() {
		return createMyGroup(com.FileManagerX.Globals.Datas.MyNet);
	}
	public final static com.FileManagerX.MyNet.Group createTempGroup() {
		return createTempGroup(com.FileManagerX.Globals.Datas.MyNet);
	}
	public final static com.FileManagerX.MyNet.Group createHideGroup() {
		return createHideGroup(com.FileManagerX.Globals.Datas.MyNet);
	}
	
	public final static com.FileManagerX.MyNet.Group createServerGroup(com.FileManagerX.MyNet.Net net) {
		if(net.getGroups().size() > 0) {
			return net.getGroups().get(0);
		}
		else {
			net.addGroup("ServerGroup");
			return net.getGroups().get(0);
		}
	}
	public final static com.FileManagerX.MyNet.Group createMyGroup(com.FileManagerX.MyNet.Net net) {
		if(net.getGroups().size() > 1) {
			return net.getGroups().get(1);
		}
		else {
			createServerGroup(net);
			net.addGroup("MyGroup");
			return net.getGroups().get(1);
		}
	}
	public final static com.FileManagerX.MyNet.Group createTempGroup(com.FileManagerX.MyNet.Net net) {
		if(net.getGroups().size() > 2) {
			return net.getGroups().get(2);
		}
		else {
			createMyGroup(net);
			net.addGroup("TempGroup");
			return net.getGroups().get(2);
		}
	}
	public final static com.FileManagerX.MyNet.Group createHideGroup(com.FileManagerX.MyNet.Net net) {
		if(net.getGroups().size() > 2) {
			return net.getGroups().get(2);
		}
		else {
			createTempGroup(net);
			net.addGroup("HideGroup");
			return net.getGroups().get(2);
		}
	}
}
