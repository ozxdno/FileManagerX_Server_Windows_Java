package com.FileManagerX.Factories;

public class MyNetFactory {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String SERVER_GROUP = "Server Group";
	public final static String MY_GROUP = "My Group";
	public final static String FRESH_GROUP = "Fresh Group";
	public final static String TEMP_GROUP = "Temp Group";
	public final static String HIDE_GROUP = "Hide Group";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	public final static com.FileManagerX.MyNet.Group createFreshGroup() {
		return createFreshGroup(com.FileManagerX.Globals.Datas.MyNet);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.MyNet.Group createServerGroup(com.FileManagerX.MyNet.Net net) {
		String name = SERVER_GROUP;
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	public final static com.FileManagerX.MyNet.Group createMyGroup(com.FileManagerX.MyNet.Net net) {
		String name = MY_GROUP;
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	public final static com.FileManagerX.MyNet.Group createTempGroup(com.FileManagerX.MyNet.Net net) {
		String name = TEMP_GROUP;
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	public final static com.FileManagerX.MyNet.Group createHideGroup(com.FileManagerX.MyNet.Net net) {
		String name = HIDE_GROUP;
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	public final static com.FileManagerX.MyNet.Group createFreshGroup(com.FileManagerX.MyNet.Net net) {
		String name = FRESH_GROUP;
		com.FileManagerX.MyNet.Group g = net.searchByKey(name);
		if(g == null) {
			g = new com.FileManagerX.MyNet.Group();
			g.setName(name);
			net.add(g);
		}
		return g;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
