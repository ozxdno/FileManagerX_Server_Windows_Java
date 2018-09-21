package com.FileManagerX.Coder;

public class Decode_Location implements IDecode {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public byte[] decode(byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return bytes;
		}
		
		int bg = bytes.length-1;
		int ed = 0;
		for(; bg>=0; bg--) { if(bytes[bg] == '$') { break; } }
		if(bg == -1) { return bytes; }
		
		byte[] newBytes = new byte[bg];
		for(int i=0; i<bg; i++) { newBytes[i] = bytes[i]; }
		
		bg += 2;
		while(bg < bytes.length) {
			ed = bytes.length;
			for(int i=bg; i<bytes.length; i++) {
				if(bytes[i] == ' ') {
					ed = i;
					break;
				}
			}
			int loc = this.decodeLocation(bytes, bg, ed);
			com.FileManagerX.BasicEnums.EOF eof = 
					com.FileManagerX.BasicEnums.EOF.getEofByReplace(newBytes[loc]);
			
			newBytes[loc] = eof.getSpecial();
			bg = ed + 1;
		}
		
		return newBytes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int decodeLocation(byte[] bytes, int bg, int ed) {
		int base = ASCII.PERMIT_CODE_A.length;
		int factor = 1;
		int loc = 0;
		for(int i=bg; i<ed; i++) {
			loc += ASCII.getPosition_CodeA(bytes[i])*factor;
			factor *= base;
		}
		return loc;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
