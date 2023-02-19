package life.study.community.mapper;

import life.study.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMyMapper {
    void incCommentCount(Comment parentComment);

}
