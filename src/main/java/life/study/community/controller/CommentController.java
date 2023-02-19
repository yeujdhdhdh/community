package life.study.community.controller;

import life.study.community.dto.CommentDto;
import life.study.community.dto.ResultDto;
import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.mapper.CommentMapper;
import life.study.community.model.Comment;
import life.study.community.model.User;
import life.study.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDto commentDto , HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
//        if (user==null){
//            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
//        }
        Comment comment =new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(1);
        comment.setLikeCount(0L);

        System.out.println(comment);
        commentService.insert(comment);

        return ResultDto.okOf();
    }
}
