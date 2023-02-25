package life.study.community.service;

import life.study.community.dto.PageDto;
import life.study.community.dto.QuestionDto;
import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import life.study.community.mapper.QuestionMapper;
import life.study.community.mapper.QuestionMyMapper;
import life.study.community.mapper.UserMapper;
import life.study.community.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionMyMapper questionMyMapper;
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
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> qusetions=questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offSizeNum,size));

        for (Question question:qusetions){
            UserKey userKey=new UserKey();
            userKey.setId(question.getCreator());
            User user=userMapper.selectByPrimaryKey(userKey);

            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setData(questionDtoList);
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
        example1.setOrderByClause("gmt_create desc");
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
        pageDto.setData(questionDtoList);
        return pageDto;
    }

    public QuestionDto getById(Integer id) {
        QuestionKey questionKey=new QuestionKey();
        questionKey.setId(id);
        Question question=questionMapper.selectByPrimaryKey(questionKey);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
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
            question.setLikeCount(0);

            question.setViveCount(0);
            question.setCommentCount(0);
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

    public void incView(Integer id) {
        Question question=new Question();
        question.setId(id);
        question.setViveCount(1);
        questionMyMapper.incView(question);


    }

    public List<QuestionDto> selectRelated(QuestionDto queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionMyMapper.selectRelated(question);
        List<QuestionDto> questionDTOS = questions.stream().map(q -> {
            QuestionDto questionDTO = new QuestionDto();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
