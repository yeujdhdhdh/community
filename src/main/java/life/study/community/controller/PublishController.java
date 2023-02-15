package life.study.community.controller;

import life.study.community.dto.QuestionDto;
import life.study.community.mapper.QuestionMapper;
import life.study.community.mapper.UserMapper;
import life.study.community.model.Question;
import life.study.community.model.User;
import life.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model,HttpServletRequest request){
        request.getSession().setAttribute("errorTag",0);

        return "publish";
    }
    @PostMapping("/publish1")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String  description,
            @RequestParam("tag")String tag,

            HttpServletRequest request, Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //错误信息弹出
        if (title==null||title==""){
            request.getSession().setAttribute("errorTag",1);
            return "publish";
        }
        if (description==null||description==""){
            request.getSession().setAttribute("errorTag",1);
            return "publish";
        }
        if (tag==null||tag==""){
            request.getSession().setAttribute("errorTag",1);
            return "publish";
        }

        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("user","用户未登录");
            return "publish";
        }

        Question question=new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
//        question.setId(id);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionService.createOrUpdate(question);

        return "redirect:/";
    }
    @GetMapping("/publish/{id}")
    public String questionUpdate(@PathVariable(name = "id")Integer id,Model model){
        if (id==null){
            return "redirect:/question/"+id;
        }else {
            QuestionDto question=questionService.getById(id);
            model.addAttribute("title",question.getTitle());
            model.addAttribute("description",question.getDescription());
            model.addAttribute("tag",question.getTag());
            model.addAttribute("id",question.getId());
            return "publish";
        }

    }
}
