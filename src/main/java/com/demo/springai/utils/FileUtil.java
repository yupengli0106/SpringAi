package com.demo.springai.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtil {

    /**
     * 将字节数组写入到指定路径的 .mp3 文件中，确保文件名不重复
     *
     * @param directory 目标目录路径
     * @param data      字节数组数据
     * @return 生成的文件名
     * @throws IOException 如果发生 I/O 错误
     */
    public static String writeBytesToMp3File(String directory, byte[] data) throws IOException {
        String fileName = generateUniqueFileName(directory, "mp3");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(directory, fileName));
            fos.write(data);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

    /**
     * 生成唯一的文件名
     *
     * @param directory 目标目录路径
     * @param extension 文件扩展名
     * @return 唯一的文件名
     */
    private static String generateUniqueFileName(String directory, String extension) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String uniqueID = UUID.randomUUID().toString().substring(0, 8);
        String fileName = "audio_" + timeStamp + "_" + uniqueID + "." + extension;

        File file = new File(directory, fileName);
        while (file.exists()) {
            uniqueID = UUID.randomUUID().toString().substring(0, 8);
            fileName = "audio_" + timeStamp + "_" + uniqueID + "." + extension;
            file = new File(directory, fileName);
        }
        return fileName;
    }

}





