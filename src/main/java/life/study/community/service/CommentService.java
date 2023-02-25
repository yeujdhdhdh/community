package life.study.community.service;

import life.study.community.dto.CommentDto;
import life.study.community.enums.CommentTypeEnum;
import life.study.community.enums.NotificationStatusEnum;
import life.study.community.enums.NotificationTypeEnum;
import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import life.study.community.mapper.*;
import life.study.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMyMapper commentMyMapper;
    @Autowired
    private QuestionMyMapper questionMyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment,User user) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null|| CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getContent()==null||comment.getContent().equals("")){
            throw  new CustomizeException(CustomizeErrorCode.CONTENT_NOTHING);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            CommentKey commentKey=new CommentKey();
            commentKey.setId(comment.getParentId());
            Comment dbComment = commentMapper.selectByPrimaryKey(commentKey);
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            // 回复问题
            QuestionKey questionKey=new QuestionKey();
            questionKey.setId(dbComment.getParentId());
            Question question = questionMapper.selectByPrimaryKey(questionKey);
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMyMapper.incCommentCount(parentComment);
            System.out.println(user);

          // 创建通知
           createNotify(comment, dbComment.getCommentator(), user.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        }else {
            //回复问题
            QuestionKey questionKey=new QuestionKey();
            questionKey.setId(comment.getParentId());
            Question question = questionMapper.selectByPrimaryKey(questionKey);
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionMyMapper.incComment(question);
            System.out.println(user);
            // 创建通知
            createNotify(comment, question.getCreator(), user.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Integer outerId) {
//        if (receiver == comment.getCommentator()) {
//            return;
//        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifiername(notifierName);
        notification.setOutertitle(outerTitle);
        notificationMapper.insert(notification);
    }


    public List<CommentDto> listByQuestion(Integer id) {
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                        .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);

        if (comments.size()==0){
            return new ArrayList<>();
        }
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        List<Integer> users=new ArrayList<>();
        users.addAll(commentators);

        UserExample example1 = new UserExample();
        example1.createCriteria()
                        .andIdIn(users);
        List<User> users1 = userMapper.selectByExample(example1);

        Map<Integer, User> userMap = users1.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());


        return commentDtos;
    }

    public List<CommentDto> listByTargetId(Integer id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList();
        userIds.addAll(commentators);


        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        // 转换 comment 为 commentDTO
        List<CommentDto> commentDTOS = comments.stream().map(comment -> {
            CommentDto commentDTO = new CommentDto();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

}
