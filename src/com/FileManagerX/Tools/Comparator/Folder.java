package com.FileManagerX.Tools.Comparator;

public class Folder {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.util.Comparator<com.FileManagerX.BasicModels.Folder> indexAsc =
		new java.util.Comparator<com.FileManagerX.BasicModels.Folder>() {
			public int compare(com.FileManagerX.BasicModels.Folder e1, com.FileManagerX.BasicModels.Folder e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
	
	public final static java.util.Comparator<com.FileManagerX.BasicModels.Folder> indexDes =
		new java.util.Comparator<com.FileManagerX.BasicModels.Folder>() {
			public int compare(com.FileManagerX.BasicModels.Folder e1, com.FileManagerX.BasicModels.Folder e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
