package life.study.community.dto;

import life.study.community.model.User;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;

    private Integer parentId;

    private Integer type;

    private Integer commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private String content;

    private Integer commentCount;
    private User user;

}
