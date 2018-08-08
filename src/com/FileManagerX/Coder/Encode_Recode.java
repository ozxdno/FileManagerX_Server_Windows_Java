package com.FileManagerX.Coder;

public class Encode_Recode implements IEncode {

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

		byte[] extra = new byte[1 + bytes.length/7 + 1];
		extra[0] = (byte)'|';
		for(int i=1; i<extra.length; i++) { extra[i] &= 0x80; }
		
		for(int i=0; i<bytes.length; i++) {
			if(bytes[i] < 0) {
				;
			}
			else { // >= 0
				int x = i / 7;
				int y = i % 7;
				if(y != 0) { x++; } else { y=7; }
				x++;
				extra[x] &= 1<<y;
			}
		}
		
		byte[] newBytes = new byte[bytes.length + extra.length];
		for(int i=0; i<bytes.length; i++) { newBytes[i] = bytes[i]; }
		for(int i=bytes.length, j=0; j<extra.length; i++,j++) { newBytes[i] = extra[j]; }
		return newBytes;
	}
}
