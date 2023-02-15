package life.study.community.dto;

import life.study.community.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private String title;
    private String description;
    private Integer id;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viveCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
