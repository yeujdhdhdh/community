package life.study.community.dto;

import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import lombok.Data;

@Data
public class ResultDto<T> {
    private Integer code;

    private String message;
    private T data;
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

    public static <T> ResultDto okOf(T t) {
        ResultDto resultDTto = new ResultDto();
        resultDTto.setCode(200);
        resultDTto.setMessage("请求成功");
        resultDTto.setData(t);
        return resultDTto;
    }
}
