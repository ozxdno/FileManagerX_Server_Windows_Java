package com.FileManagerX.Coder;

public class Encode_Location implements IEncode {

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
		byte b = 0;
		for(int i=0; i<bytes.length; i++) {
			for(int j=0; j<spc.length; j++) {
				if(bytes[i] == spc[j]) {
					bytes[i] = rep[j];
					extra.add((byte)'|');
					b = (byte)(i & 0x000F);
					extra.add(b);
					b = (byte)(i & 0x00F0 >> 8);
					if(b == 0) { continue; } else { extra.add(b); }
					b = (byte)(i & 0x0F00 >> 16);
					if(b == 0) { continue; } else { extra.add(b); }
					b = (byte)(i & 0xF000 >> 24);
					if(b == 0) { continue; } else { extra.add(b); }
				}
			}
		}
		
		byte[] newBytes = new byte[bytes.length + extra.size()];
		for(int i=0; i<bytes.length; i++) { newBytes[i] = bytes[i]; }
		int i = bytes.length;
		for(byte item : extra) { newBytes[i] = item; i++; }
		return newBytes;
	}
}
