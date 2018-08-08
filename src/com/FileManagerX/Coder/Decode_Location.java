package com.FileManagerX.Coder;

public class Decode_Location implements IDecode {

	public byte[] decode(byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return bytes;
		}
		
		int bg = bytes.length;
		for(; bg>=0; bg--) { if(bytes[bg] == '$') { break; } }
		if(bg == -1) { return bytes; }
		
		byte[] newBytes = new byte[bg];
		for(int i=0; i<bg; i++) { newBytes[i] = bytes[i]; }
		bg++;
		while(bg < bytes.length && bytes[bg] == '|') {
			bg++;
			int loc = bytes[bg];
			bg++;
			if(bytes[bg] == '|') { 
				bg--; newBytes[loc] = (byte) (-newBytes[loc]); continue;
			} else {
				loc |= newBytes[bg] << 8;
			}
			bg++;
			if(bytes[bg] == '|') { 
				bg--; newBytes[loc] = (byte) (-newBytes[loc]); continue;
			} else {
				loc |= newBytes[bg] << 16;
			}
			bg++;
			if(bytes[bg] == '|') { 
				bg--; newBytes[loc] = (byte) (-newBytes[loc]); continue;
			} else {
				loc |= newBytes[bg] << 24;
			}
			
			bg++;
			newBytes[loc] = (byte) (-newBytes[loc]);
		}
		
		return newBytes;
	}
}
