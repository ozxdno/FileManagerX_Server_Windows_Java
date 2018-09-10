package com.FileManagerX.Factories;

public class FormFactory {

	public final static com.FileManagerX.Forms.Test createTest() {
		return new com.FileManagerX.Forms.Test();
	}
	
	public final static com.FileManagerX.Forms.CMDC createCMDC() {
		return new com.FileManagerX.Forms.CMDC();
	}
	
}
