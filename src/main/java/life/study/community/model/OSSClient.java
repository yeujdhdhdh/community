package life.study.community.model;

import lombok.Data;

@Data
public class OSSClient {

    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 使用刚刚创建的accessKeyId和accessKeySecret
    String accessKeyId = "LTAI5tAMK41FY3CkfCzTX7qz";
    String accessKeySecret = "v8dItnoTPN6rNziVtqTbwtVGQi2Y18";

    public OSSClient(String endpoint, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }
}
