package com.FileManagerX.Coder;

public class Encode_Location implements IEncode {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public byte[] encode(byte[] bytes, byte[] spc, byte[] rep) {
		if(bytes == null) {
			return null;
		}
		if(bytes.length == 0) {
			return bytes;
		}
		if(spc == null || spc.length == 0 || rep == null || rep.length == 0) {
			return bytes;
		}
		if(spc.length != rep.length) {
			return bytes;
		}
		
		java.util.List<Byte> extra = new java.util.LinkedList<>();
		extra.add((byte)'$');
		for(int i=0; i<bytes.length; i++) {
			for(int j=0; j<spc.length; j++) {
				if(bytes[i] == spc[j]) {
					bytes[i] = rep[j];
					extra.add((byte)' ');
					extra.addAll(this.encodeLocation(i));
					break;
				}
			}
		}
		
		byte[] newBytes = new byte[bytes.length + extra.size()];
		for(int i=0; i<bytes.length; i++) { newBytes[i] = bytes[i]; }
		int i = bytes.length;
		for(byte item : extra) { newBytes[i] = item; i++; }
		return newBytes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private java.util.ArrayList<Byte> encodeLocation(int loc) {
		java.util.ArrayList<Byte> res = new java.util.ArrayList<>();
		if(loc == 0) {
			res.add(ASCII.PERMIT_CODE_A[0]);
			return res;
		}
		while(loc > 0) {
			int b = loc % ASCII.PERMIT_CODE_A.length;
			loc = loc / ASCII.PERMIT_CODE_A.length;
			res.add(ASCII.PERMIT_CODE_A[b]);
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
