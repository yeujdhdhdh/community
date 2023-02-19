package life.study.community.exceptiopn;

public class CustomizeException extends RuntimeException{

   private String message;
   private Integer code;
    public CustomizeException(String message) {
        this.message=message;

    }
    public CustomizeException(ICustomizeErrorCode errorCode ) {
        this.message=errorCode.getMessage();this.code=errorCode.getCode();
    }
    @Override
    public String getMessage(){
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
