package life.study.community.service;

import life.study.community.enums.CommentTypeEnum;
import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import life.study.community.mapper.CommentMapper;
import life.study.community.mapper.CommentMyMapper;
import life.study.community.mapper.QuestionMapper;
import life.study.community.mapper.QuestionMyMapper;
import life.study.community.model.Comment;
import life.study.community.model.CommentKey;
import life.study.community.model.Question;
import life.study.community.model.QuestionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null|| CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
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

//            // 创建通知
//            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());

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


//            // 创建通知
//            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());

        }

    }
}
