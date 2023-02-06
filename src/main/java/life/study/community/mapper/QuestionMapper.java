package life.study.community.mapper;

import life.study.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) value (#{question.title},#{question.description},#{question.gmtCreate},#{question.gmtModified},#{question.creator},#{question.tag})")
    void create(@Param("question") Question question);
}
