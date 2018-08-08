package com.FileManagerX.Coder;

/**
 * 把其他信息编码成字符串，方便传输。
 * 
 * @author ozxdno
 *
 */
public class Encoder {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static IEncode encode = new Encode_Location();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static byte[] Encode_Byte2Byte(byte[] bytes) {
		byte[] spc = new byte[] {
				com.FileManagerX.BasicEnums.EOF.N.getSpecial()
			};
		byte[] rep = new byte[] {
				com.FileManagerX.BasicEnums.EOF.N.getReplce()
			};
		
		return encode.encode(bytes, spc, rep);
	}
	public final static byte[] Encode_String2Byte(String str) {
		byte[] spc = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getSpecial()
			};
		byte[] rep = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getReplce()
			};
		
		return encode.encode(str.getBytes(), spc, rep);
	}
	public final static String Encode_String2String(String str) {
		byte[] spc = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getSpecial()
			};
		byte[] rep = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getReplce()
			};
		
		return new String(encode.encode(str.getBytes(), spc, rep));
	}
	public final static byte[] Encode_Transport2Byte(com.FileManagerX.Interfaces.ITransport t) {
		String s = ((t instanceof com.FileManagerX.Interfaces.ICommand) ? 'C' : 'R') +
				t.output() + '\n';
		return s.getBytes();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

