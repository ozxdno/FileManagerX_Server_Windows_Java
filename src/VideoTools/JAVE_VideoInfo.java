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

* @Description:获取视频宽高大小时间

* @Date: 2017-9-29 14:02

*/

public class JAVE_VideoInfo {

public static void main(String[] args){

File source = new File("C:\\Users\\HONGLINCHEN\\Desktop\\QQ短视频20170926141319.mp4");

Encoder encoder = new Encoder();

FileChannel fc= null;

String size = "";

try {

it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(source);

long ls = m.getDuration();

System.out.println("此视频时长为:"+ls/60000+"分"+(ls)/1000+"秒！");

//视频帧宽高

System.out.println("此视频高度为:"+m.getVideo().getSize().getHeight());

System.out.println("此视频宽度为:"+m.getVideo().getSize().getWidth());

System.out.println("此视频格式为:"+m.getFormat());

FileInputStream fis = new FileInputStream(source);

fc= fis.getChannel();

BigDecimal fileSize = new BigDecimal(fc.size());

size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";

System.out.println("此视频大小为"+size);

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
