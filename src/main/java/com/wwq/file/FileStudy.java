package com.wwq.file;

import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.util.Base64;

import com.wwq.file.FileUtils;
import sun.security.provider.MD5;


public class FileStudy {
    File file;

    public static void main(String[] args) {
        JSONObject configJson = new JSONObject();
        configJson.put("date", "2019-06-27");
        configJson.put("time", "2019年6月27日 11:41:55");
        configJson.put("user", "beyond");

        String filePath = "C:\\Users\\Beyound\\Desktop";
        String fileName = "config.json";

        String imgString = FileUtils.fileToBase64("E:\\图片\\Camera\\20190201223151_2.mp4");
        String imgString2 = FileUtils.fileToBase64("E:\\毕业设计\\153110083-王炜权-毕业论文.docx");

        FileUtils.base64ToFile(imgString,filePath,"test.mp4");
        FileUtils.base64ToFile(imgString2,filePath,"毕业论文.docx");
        FileUtils.writeFile(filePath, fileName, configJson);
        configJson=JSONObject.parseObject(FileUtils.readFile(filePath,fileName));
        System.out.println(configJson.get("user"));
    }

}
