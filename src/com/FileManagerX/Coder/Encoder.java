package com.FileManagerX.Coder;

/**
 * 把其他信息编码成字符串，方便传输。
 * 
 * @author ozxdno
 *
 */
public class Encoder {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static IEncode ENCODE = new Encode_Location();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static byte[] Encode_Byte2Byte(byte[] bytes) {
		byte[] result = new byte[bytes.length << 1];
		for(int i=0; i<bytes.length; i++) {
			int bit = bytes[i] + 128;
			result[i<<1] = (byte)((bit & 0x0F) + 'A');
			result[(i<<1) + 1] = (byte)((bit >>> 4) + 'A');
		}
		return result;
	}
	
	public final static byte[] Encode_String2Byte(String str) {
		byte[] spc = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getSpecial()
			};
		byte[] rep = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getReplce()
			};
		
		return ENCODE.encode(str.getBytes(), spc, rep);
	}
	public final static String Encode_String2String(String str) {
		byte[] spc = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getSpecial()
			};
		byte[] rep = new byte[] {
				com.FileManagerX.BasicEnums.EOF.S.getReplce()
			};
		
		return new String(ENCODE.encode(str.getBytes(), spc, rep));
	}
	public final static String Encode_String2DataBaseString(String str) {
		if(str == null) { return ""; }
		str = str.replace("\\", "\\\\");
		return str;
	}
	
	public final static byte[] Encode_Transport2Byte(com.FileManagerX.Interfaces.ITransport t) {
		byte[] spc = new byte[] {
				com.FileManagerX.BasicEnums.EOF.N.getSpecial()
			};
		byte[] rep = new byte[] {
				com.FileManagerX.BasicEnums.EOF.N.getReplce()
			};
		
		byte[] content = ENCODE.encode(t.output().getBytes(), spc, rep);
		byte[] bytes = new byte[content.length+2];
		for(int i=0; i<content.length; i++) { bytes[i+1] = content[i]; }
		
		bytes[0] = (t instanceof com.FileManagerX.Interfaces.ICommand) ? (byte)'C' : (byte)'R';
		bytes[bytes.length-1] = (byte)'\n';
		return bytes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

