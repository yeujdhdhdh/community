package life.study.community.dto;

import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import lombok.Data;

@Data
public class ResultDto {
    private Integer code;

    private String message;
    public static ResultDto errorOf(Integer coed,String message ){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(coed);
        resultDto.setMessage(message);
        return resultDto;

    }
    public static ResultDto errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }
    public static ResultDto errorOf(CustomizeErrorCode code) {
        return errorOf(code.getCode(),code.getMessage());
    }
    public static ResultDto okOf() {
        return errorOf(200,"成功");
    }


}
