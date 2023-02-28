package life.study.community.controller;

import life.study.community.dto.FileDto;
import life.study.community.provider.AliImageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Controller
public class FileController {
    @Autowired
    private AliImageProvider aliImageProvider;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upLoad(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
        MultipartFile file= multipartHttpServletRequest.getFile("editormd-image-file");
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();

        URL res=aliImageProvider.upload(inputStream,"images",originalFilename);
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setMessage("上传成功");
        fileDto.setUrl(res);
        System.out.println(res);
        //http://ysworkuser.oss-cn-beijing.aliyuncs.com/20230227/1677496289890515.jpg?Expires=1677499890&OSSAccessKeyId=LTAI5tAMK41FY3CkfCzTX7qz&Signature=dctiRFO9y6hadfEnBsEQntKMYkw%3D
        return fileDto;
    }
}
