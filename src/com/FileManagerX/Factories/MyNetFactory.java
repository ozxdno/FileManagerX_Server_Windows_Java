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
		String name = "ServerGroup";
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	public final static com.FileManagerX.MyNet.Group createMyGroup(com.FileManagerX.MyNet.Net net) {
		String name = "MyGroup";
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	public final static com.FileManagerX.MyNet.Group createTempGroup(com.FileManagerX.MyNet.Net net) {
		String name = "TempGroup";
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	public final static com.FileManagerX.MyNet.Group createHideGroup(com.FileManagerX.MyNet.Net net) {
		String name = "HideGroup";
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
}
