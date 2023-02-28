package life.study.community.dto;

import lombok.Data;

import java.net.URL;

@Data
public class FileDto {
    private int success;
    private String  message;
    private URL url;

}
