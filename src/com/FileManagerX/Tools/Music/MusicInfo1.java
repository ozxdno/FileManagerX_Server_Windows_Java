package com.FileManagerX.Tools.Music;

import java.io.*;

public class MusicInfo1 {

	/**
	* 获取MP3文件信息
	* 
	* @param musicFile
	* MP3文件对象
	*/
	private void getMusicInfo(File musicFile) {
	try {
	RandomAccessFile randomAccessFile = new RandomAccessFile(musicFile,"r");
	byte[] buffer = new byte[128];
	randomAccessFile.seek(randomAccessFile.length() - 128);
	randomAccessFile.read(buffer);
	if (buffer.length == 128) {
	String tag = new String(buffer, 0, 3);
	// 只有前三个字节是TAG才处理后面的字节
	if (tag.equalsIgnoreCase("TAG")) {
	// 歌曲名
	String songName = new String(buffer, 3, 30).trim();
	// 艺术家
	String artist = new String(buffer, 33, 30).trim();
	// 所属唱片
	String album = new String(buffer, 63, 30).trim();
	// 发行年
	String year = new String(buffer, 93, 4).trim();
	// 备注
	String comment = new String(buffer, 97, 28).trim();
	System.out.println(songName + "\t" + artist + "\t" + album
	+ "\t" + year + "\t" + comment);
	} else {
	System.out.println("无效的歌曲信息...");
	}
	}

	} catch (FileNotFoundException e) {
	e.printStackTrace();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	
}
