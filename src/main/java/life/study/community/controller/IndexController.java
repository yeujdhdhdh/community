package life.study.community.controller;

import life.study.community.dto.PageDto;
import life.study.community.dto.QuestionDto;
import life.study.community.mapper.UserMapper;
import life.study.community.model.Question;
import life.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")

    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", required = false) String sort) {
        {

            PageDto pageDto = questionService.list(search,page, size);
            List<Question> hotQuestions = questionService.getHotQuestion();
            model.addAttribute("search",search);
            model.addAttribute("pageDto", pageDto);
            model.addAttribute("hotQuestion", hotQuestions);
            return "index";
        }
    }
}
