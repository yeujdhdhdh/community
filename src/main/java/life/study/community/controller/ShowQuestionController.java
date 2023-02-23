package life.study.community.controller;

import life.study.community.dto.CommentDto;
import life.study.community.dto.QuestionDto;
import life.study.community.service.CommentService;
import life.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShowQuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model){
        QuestionDto questionDto=questionService.getById(id);
        List<CommentDto> commentDtoList=commentService.listByQuestion(id);

        //增加阅读数
        questionService.incView(id);
        model.addAttribute("comments",commentDtoList);
        model.addAttribute("showQuestion",questionDto);
        return "question";
    }

}
