package com.FileManagerX.Tools.Comparator;

public class File {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.util.Comparator<com.FileManagerX.BasicModels.File> indexAsc =
		new java.util.Comparator<com.FileManagerX.BasicModels.File>() {
			public int compare(com.FileManagerX.BasicModels.File e1, com.FileManagerX.BasicModels.File e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
	
	public final static java.util.Comparator<com.FileManagerX.BasicModels.File> indexDes =
		new java.util.Comparator<com.FileManagerX.BasicModels.File>() {
			public int compare(com.FileManagerX.BasicModels.File e1, com.FileManagerX.BasicModels.File e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
