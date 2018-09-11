package com.FileManagerX.Tools.Comparator;

public class User {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.util.Comparator<com.FileManagerX.BasicModels.User> indexAsc =
		new java.util.Comparator<com.FileManagerX.BasicModels.User>() {
			public int compare(com.FileManagerX.BasicModels.User e1, com.FileManagerX.BasicModels.User e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
	
	public final static java.util.Comparator<com.FileManagerX.BasicModels.User> indexDes =
		new java.util.Comparator<com.FileManagerX.BasicModels.User>() {
			public int compare(com.FileManagerX.BasicModels.User e1, com.FileManagerX.BasicModels.User e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.util.Comparator<com.FileManagerX.BasicModels.User> nickNameAsc =
		new java.util.Comparator<com.FileManagerX.BasicModels.User>() {
			public int compare(com.FileManagerX.BasicModels.User e1, com.FileManagerX.BasicModels.User e2) {
				if(e1.getNickName() == null) { return  1; }
				if(e2.getNickName() == null) { return -1; }
				int res = e1.getNickName().compareTo(e2.getNickName());
				return res < 0 ? -1 : 1;
			}
		};
			
	public final static java.util.Comparator<com.FileManagerX.BasicModels.User> nickNameDes =
		new java.util.Comparator<com.FileManagerX.BasicModels.User>() {
			public int compare(com.FileManagerX.BasicModels.User e1, com.FileManagerX.BasicModels.User e2) {
				if(e1.getNickName() == null) { return  1; }
				if(e2.getNickName() == null) { return -1; }
				int res = e1.getNickName().compareTo(e2.getNickName());
				return res > 0 ? -1 : 1;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
