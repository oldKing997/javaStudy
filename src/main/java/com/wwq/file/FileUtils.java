package com.wwq.file;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * 文件的工具类
 *
 * @author wwq
 * @date 2019年6月27日 09:56:59
 */
public class FileUtils {

    /**
     * 文件转换为Base64字符串
     *
     * @param filePath 文件的路径
     * @return base64字符串
     */
    public static String fileToBase64(String filePath) {
        String imgBase64String;
        InputStream inputStream;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(filePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            return null;
        }
        imgBase64String = Base64.getEncoder().encodeToString(data);
        return imgBase64String;
    }


    /**
     * Base64字符串转文件并存储到指定路径
     *
     * @param base64String Base64字符串
     * @param savePath     路径
     * @param fileName     文件名(包含扩展名)
     * @return 成功或失败
     */
    public static boolean base64ToFile(String base64String, String savePath, String fileName) {
        if (!StringUtils.isNotBlank(base64String)) {
            return false;
        }
        try {
            Path path = Paths.get(savePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            byte[] bytes = Base64.getDecoder().decode(base64String);
            for (byte b : bytes) {
                if (b < 0) {
                    b += 256;
                }
            }
            OutputStream outputStream = new FileOutputStream(savePath + "/" + fileName);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建目录，先判断父目录是否存在，如果不存在则将使用父目录的路径再递归调用本函数。如目录不存在则创建，如果存在直接返回true.
     *
     * @param path 目录的路径.
     * @return 目录存在或创建成功返回True，路径错误或创建失败返回False.
     * @deprecated 可使用JDK8带的Files.
     */
    private static boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (!new File(file.getParent()).exists()) {
                    createDir(file.getParent());
                }
            } catch (Exception e) {
                return false;
            }
            try {
                file.mkdirs();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                createDir(file.getParent());
            }
        }
        System.gc();
        return true;
    }


    /**
     * 写入内容到文件中（String）
     *
     * @param filePath    文件的路径
     * @param fileName    文件名
     * @param fileContent 要写入的内容
     */
    public static void writeFile(String filePath, String fileName, String fileContent) {
        try {
            createFile(filePath, fileName);
            Path path = Paths.get(filePath, fileName);
            BufferedWriter bw = Files.newBufferedWriter(path);
            bw.write(fileContent);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入内容到文件中(JSON)
     *
     * @param filePath    文件的路径
     * @param fileName    文件名
     * @param fileContent 要写入的内容
     */
    public static void writeFile(String filePath, String fileName, JSONObject fileContent) {
        try {
            createFile(filePath, fileName);
            Path path = Paths.get(filePath, fileName);
            BufferedWriter bw = Files.newBufferedWriter(path);
            bw.write(fileContent.toJSONString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件中的内容
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static String readFile(String filePath, String fileName) {
        Path path = Paths.get(filePath, fileName);
        String fileContent = "";
        try {
            if (!Files.exists(path)) {
                return "";
            }
            BufferedReader br = Files.newBufferedReader(path);
            String s;
            while ((s = br.readLine()) != null) {
                fileContent += "\n" + s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * 创建指定路径的文件
     *
     * @param filePath 路径
     * @param fileName 文件名
     */
    public static void createFile(String filePath, String fileName) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = Paths.get(filePath, fileName);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于删除文件或文件夹
     *
     * @param file 传入要删除的文件(使用文件方便递归调用)
     */
    private static void deleteFile(File file) {
        if (file.exists()) {
            //判断文件是否存在
            if (file.isFile()) {
                //判断是否是文件
                file.delete();
                //删除文件
            } else if (file.isDirectory()) {
                //否则如果它是一个目录
                File[] files = file.listFiles();
                //声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {
                    //遍历目录下所有的文件
                    deleteFile(files[i]);
                    //把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
    }
}
