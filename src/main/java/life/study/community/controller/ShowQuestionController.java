package life.study.community.controller;

import life.study.community.dto.QuestionDto;
import life.study.community.mapper.QuestionMapper;
import life.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShowQuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model){
        QuestionDto questionDto=questionService.getById(id);
        model.addAttribute("showQuestion",questionDto);
        return "question";
    }

}
