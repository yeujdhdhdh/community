package life.study.community.provider;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.sun.istack.logging.Logger;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class AliImageProvider {
    static Logger logger = Logger.getLogger(AliImageProvider.class);
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI5tAMK41FY3CkfCzTX7qz";
    private static String accessKeySecret = "v8dItnoTPN6rNziVtqTbwtVGQi2Y18";
    private static String bucketName = "ysworkuser";
    private static String objectName1    = "images";


    public URL upload(InputStream inputStream, String module, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                endpoint,
                accessKeyId,
                accessKeySecret);
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        if(!ossClient.doesBucketExist(bucketName)){
            //创建bucket
            ossClient.createBucket(bucketName);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }

        //文件名：uuid.扩展名
        fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        //文件根路径
        String key = module + "/" + fileName;
        //文件上传至阿里云
        Date expiration = new Date(System.currentTimeMillis() + 5*31*24*3600 * 1000);
        ossClient.putObject(bucketName, key, inputStream);
        URL res=ossClient.generatePresignedUrl(bucketName,key,expiration);
        // 关闭OSSClient。
        ossClient.shutdown();

        //阿里云文件绝对路径
        return res;


    }


    }


