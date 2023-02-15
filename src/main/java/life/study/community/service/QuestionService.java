package life.study.community.service;

import life.study.community.dto.PageDto;
import life.study.community.dto.QuestionDto;
import life.study.community.mapper.QuestionMapper;
import life.study.community.mapper.UserMapper;
import life.study.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PageDto list(Integer page, Integer size) {
        PageDto pageDto=new PageDto();

        Integer totalCount=questionMapper.countByExample(new QuestionExample());
        List<QuestionDto> questionDtoList=new ArrayList<>();
        pageDto.setPage(totalCount,page,size);
        if (page<1){
            page=1;
        }
        if (page>pageDto.getTotalPage()){
            page=pageDto.getTotalPage();
        }
        Integer offSizeNum=size*abs(page-1);
        List<Question> qusetions=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offSizeNum,size));

        for (Question question:qusetions){
            UserKey userKey=new UserKey();
            userKey.setId(question.getCreator());
            User user=userMapper.selectByPrimaryKey(userKey);

            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestionDtos(questionDtoList);
        return pageDto;
    }

    public PageDto list(Integer userId, Integer page, Integer size) {
        PageDto pageDto=new PageDto();

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount=questionMapper.countByExample(example);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        pageDto.setPage(totalCount,page,size);
        if (page<1){
            page=1;
        }
        if (page>totalCount&&totalCount!=0){
            page=totalCount;
        }
        Integer offSizeNum=size*(page-1);

        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorEqualTo(userId);
        List<Question> qusetions=questionMapper.selectByExampleWithRowbounds(example1,new RowBounds(offSizeNum,size));


        for (Question question:qusetions){
            UserKey userKey=new UserKey();
            userKey.setId(question.getCreator());
            User user=userMapper.selectByPrimaryKey(userKey);
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestionDtos(questionDtoList);
        return pageDto;
    }

    public QuestionDto getById(Integer id) {
        QuestionKey questionKey=new QuestionKey();
        questionKey.setId(id);
        Question question=questionMapper.selectByPrimaryKey(questionKey);
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        UserKey userKey=new UserKey();
        userKey.setId(question.getCreator());
        User user=userMapper.selectByPrimaryKey(userKey);
        questionDto.setUser(user);

        return questionDto;
    }
    public int abs(int a){
        if (a<0)
        return -a;
        else
            return a;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //create
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());

            questionMapper.insert(question);
        }else {
            //update
            Question updateQuestion=new Question();

            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria()
                            .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
    }
}