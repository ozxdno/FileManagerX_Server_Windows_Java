package com.FileManagerX.Test;

public class Coder {

	public static void main(String[] args) {
		byte[] bytes = new byte[300];
		for(int i=0; i<bytes.length; i++) { bytes[i] = (byte)(i%256 - 128); }
		System.out.println(new String(bytes));
		byte[] encode = com.FileManagerX.Coder.Encoder.Encode_Byte2Byte(bytes);
		System.out.println(new String(encode));
		byte[] decode = com.FileManagerX.Coder.Decoder.Decode_Byte2Byte(encode);
		System.out.println(new String(decode));
	}
	
}
