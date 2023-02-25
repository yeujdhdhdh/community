package life.study.community.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2019/6/14.
 */
@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Integer notifier;
    private String notifiername;
    private String outertitle;
    private Integer outerid;
    private String typeName;
    private Integer type;
}
