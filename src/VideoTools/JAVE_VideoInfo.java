package VideoTools;

import it.sauronsoftware.jave.Encoder;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.nio.channels.FileChannel;

/**

* @Author: HONGLINCHEN

* @Description:��ȡ��Ƶ��ߴ�Сʱ��

* @Date: 2017-9-29 14:02

*/

public class JAVE_VideoInfo {

public static void main(String[] args){

File source = new File("C:\\Users\\HONGLINCHEN\\Desktop\\QQ����Ƶ20170926141319.mp4");

Encoder encoder = new Encoder();

FileChannel fc= null;

String size = "";

try {

it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(source);

long ls = m.getDuration();

System.out.println("����Ƶʱ��Ϊ:"+ls/60000+"��"+(ls)/1000+"�룡");

//��Ƶ֡���

System.out.println("����Ƶ�߶�Ϊ:"+m.getVideo().getSize().getHeight());

System.out.println("����Ƶ���Ϊ:"+m.getVideo().getSize().getWidth());

System.out.println("����Ƶ��ʽΪ:"+m.getFormat());

FileInputStream fis = new FileInputStream(source);

fc= fis.getChannel();

BigDecimal fileSize = new BigDecimal(fc.size());

size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";

System.out.println("����Ƶ��СΪ"+size);

} catch (Exception e) {

e.printStackTrace();

}finally {

if (null!=fc){

try {

fc.close();

} catch (IOException e) {

e.printStackTrace();

}

}

}

}

}
