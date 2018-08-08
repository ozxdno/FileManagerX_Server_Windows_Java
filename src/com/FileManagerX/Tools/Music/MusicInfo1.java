package com.FileManagerX.Tools.Music;

import java.io.*;

public class MusicInfo1 {

	/**
	* ��ȡMP3�ļ���Ϣ
	* 
	* @param musicFile
	* MP3�ļ�����
	*/
	private void getMusicInfo(File musicFile) {
	try {
	RandomAccessFile randomAccessFile = new RandomAccessFile(musicFile,"r");
	byte[] buffer = new byte[128];
	randomAccessFile.seek(randomAccessFile.length() - 128);
	randomAccessFile.read(buffer);
	if (buffer.length == 128) {
	String tag = new String(buffer, 0, 3);
	// ֻ��ǰ�����ֽ���TAG�Ŵ��������ֽ�
	if (tag.equalsIgnoreCase("TAG")) {
	// ������
	String songName = new String(buffer, 3, 30).trim();
	// ������
	String artist = new String(buffer, 33, 30).trim();
	// ������Ƭ
	String album = new String(buffer, 63, 30).trim();
	// ������
	String year = new String(buffer, 93, 4).trim();
	// ��ע
	String comment = new String(buffer, 97, 28).trim();
	System.out.println(songName + "\t" + artist + "\t" + album
	+ "\t" + year + "\t" + comment);
	} else {
	System.out.println("��Ч�ĸ�����Ϣ...");
	}
	}

	} catch (FileNotFoundException e) {
	e.printStackTrace();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	
}
