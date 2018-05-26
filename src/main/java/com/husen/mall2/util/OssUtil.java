package com.husen.mall2.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 阿里云oss文件上传工具
 * @author husen
 */
public class OssUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(OssUtil.class);
    private static final String BUCKET_NAMW = "oss-husen-test";
    private static final String END_POINT = "oss-cn-shenzhen.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIwfCaedv20UgG";
    private static final String SECRET_ACCESS_KEY = "viZURAskaBPvxeN8BC1jrrohxP8znf";
    /**
     * 封装一个上传文件的方法
     * @param multipartFile
     */
    public static String uploadFile(MultipartFile multipartFile) throws IOException {
        //上传照片
        if(multipartFile != null){
            //原始名称
            LOGGER.info("开始上传文件:{}", multipartFile.getOriginalFilename());
            String originalFilename = multipartFile.getOriginalFilename();
            //上传图片
            String newFileName = null;
            if(multipartFile != null && StringUtils.isNotBlank(originalFilename)){
                //新的图片名称
                newFileName = UUID.randomUUID() +
                        originalFilename.substring(originalFilename.lastIndexOf("."));
                String filePath = LocalDateTime.now().getMonth().toString() + "/" + newFileName;
                //在OSS上存储图片的地址
                String picPath = "http://" + BUCKET_NAMW + "." + END_POINT + "/" + filePath;
                OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
                ossClient.putObject(BUCKET_NAMW, filePath, multipartFile.getInputStream());
                ossClient.shutdown();
                return picPath;
            }
        }
        return null;
    }
}