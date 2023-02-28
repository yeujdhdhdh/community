package life.study.community.mapper;

import life.study.community.dto.QuestionQueryDTO;
import life.study.community.model.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMyMapper {
    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);
    List<Question> getHotQuestion();

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
