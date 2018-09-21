package com.FileManagerX.Coder;

public class ASCII {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static byte[] PERMIT_CODE_N = (
			"0123456789"
		).getBytes();
	
	public final static byte[] PERMIT_CODE_L = (
			"abcdefghijklmnopqrstuvwxyz"
		).getBytes();
	
	public final static byte[] PERMIT_CODE_U = (
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		).getBytes();
	
	public final static byte[] PERMIT_CODE_A = (
			new String(PERMIT_CODE_N) +
			new String(PERMIT_CODE_L) +
			new String(PERMIT_CODE_U)
		).getBytes();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static int getPosition_CodeA(byte code) {
		if('0' <= code && code <= '9') {
			return code - '0';
		}
		if('a' <= code && code <= 'z') {
			return code - 'a' + PERMIT_CODE_N.length;
		}
		if('A' <= code && code <= 'Z') {
			return code - 'A' + PERMIT_CODE_N.length + PERMIT_CODE_L.length;
		}
		
		return -1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
