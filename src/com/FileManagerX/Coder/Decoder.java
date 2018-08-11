package com.FileManagerX.Coder;

/**
 * 把一个字符串解析成想要的信息格式。
 * 
 * @author ozxdno
 *
 */
public class Decoder {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static IDecode DECODE = new Decode_Location();

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static byte[] Decode_Byte2Byte(byte[] bytes) {
		return DECODE.decode(bytes);
	}
	public final static String Decode_Byte2String(byte[] bytes) {
		return new String(DECODE.decode(bytes));
	}
	public final static byte[] Decode_String2Byte(String str) {
		return DECODE.decode(str.getBytes());
	}
	public final static String Decode_String2String(String str) {
		return new String(DECODE.decode(str.getBytes()));
	}
	public final static com.FileManagerX.Interfaces.ITransport Decode_Byte2Transport(byte[] bytes) {
		try {
			String s = new String(bytes);
			boolean isCMD = s.charAt(0) == 'C';
			s = s.substring(1);
			
			com.FileManagerX.BasicEnums.CMDType cmdType = com.FileManagerX.BasicEnums.CMDType.valueOf(
					com.FileManagerX.Tools.String.getField(s)
				);
			com.FileManagerX.Interfaces.ITransport t = isCMD ?
					cmdType.getCommand() :
					cmdType.getReply();
			
			if(t.input(s) == null) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(
						"Wrong Receive Content",
						"Content: " + new String(bytes)
					);
				com.FileManagerX.Replies.Unsupport u = new com.FileManagerX.Replies.Unsupport();
				u.setContent(new String(bytes));
				u.getBasicMessagePackage().setIsRecord(true);
				return u;
			}
			
			return t;
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(
					"Wrong Receive Content",
					"Content: " + new String(bytes)
				);
			com.FileManagerX.Replies.Unsupport u = new com.FileManagerX.Replies.Unsupport();
			u.setContent(new String(bytes));
			u.getBasicMessagePackage().setIsRecord(true);
			return u;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
