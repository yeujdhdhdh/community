package life.study.community.mapper;

import life.study.community.model.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMyMapper {
    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);
}
