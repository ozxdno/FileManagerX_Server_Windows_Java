package com.FileManagerX.Tools.Video;

import java.io.File;
import java.util.List;

public class JAVE_Frame {

	public static boolean processImg(String veido_path,String ffmpeg_path) {

		File file = new File(veido_path);

		if (!file.exists()) {

		System.err.println("·��[" + veido_path + "]��Ӧ����Ƶ�ļ�������!");

		return false;

		}

		List<String> commands = new java.util.ArrayList<String>();

		commands.add(ffmpeg_path);

		commands.add("-i");

		commands.add(veido_path);

		commands.add("-y");

		commands.add("-f");

		commands.add("image2");

		commands.add("-ss");

		commands.add("1");//������������ý�ȡ��Ƶ������ʱ�Ļ���

		commands.add("-t");

		commands.add("0.001");

		commands.add("-s");

		commands.add("1920x1080");//��X��

		commands.add(veido_path.substring(0, veido_path.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");

		try {

		ProcessBuilder builder = new ProcessBuilder();

		builder.command(commands);

		builder.start();

		System.out.println("��ȡ�ɹ�");

		return true;

		} catch (Exception e) {

		e.printStackTrace();

		return false;

		}

		}

		public static void main(String[] args) {

		processImg("C:\\Users\\HONGLINCHEN\\Desktop\\��Ц��Ƶ\\Android Oreo - Open Wonder.mp4","src/test/lib/ffmpeg.exe");

		}
	
}
