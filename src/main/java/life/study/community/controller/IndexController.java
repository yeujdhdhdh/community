package life.study.community.controller;

import life.study.community.dto.PageDto;
import life.study.community.mapper.UserMapper;
import life.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")

    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size){

        PageDto pageDto=questionService.list(page,size);
        model.addAttribute("pageDto", pageDto);

        return "index";
    }
}
