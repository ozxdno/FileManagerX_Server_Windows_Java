package com.FileManagerX.FileModels;

public class Xml extends com.FileManagerX.BasicModels.File {
	
	private com.FileManagerX.Tools.Xml.Node current;
	private com.FileManagerX.Tools.Xml.Node root;

	public com.FileManagerX.BasicModels.Config getConfig(com.FileManagerX.Tools.Xml.Node node, String itemName) {
		return null;
	}
	public com.FileManagerX.Tools.Xml.Node getNode(com.FileManagerX.Tools.Xml.Node node, String itemName) {
		return null;
	}
	public com.FileManagerX.BasicModels.Config getConfigFromCurrent(String itemName) {
		return null;
	}
	public com.FileManagerX.Tools.Xml.Node getNodeFromCurrent(String itemName) {
		return null;
	}
	public com.FileManagerX.BasicModels.Config getConfigFromRoot(String itemName) {
		return null;
	}
	public com.FileManagerX.Tools.Xml.Node getNodeFromRoot(String itemName) {
		return null;
	}
	
	public com.FileManagerX.Tools.Xml.Node getCurrentNode() {
		return this.current;
	}
	public com.FileManagerX.Tools.Xml.Node getRootNode() {
		return this.root;
	}
	
}
