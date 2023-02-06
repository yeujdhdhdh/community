package life.study.community.controller;

import life.study.community.mapper.QuestionMapper;
import life.study.community.mapper.UserMapper;
import life.study.community.model.Question;
import life.study.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        //验证是否登录，并保持登录状态
        Cookie[] cookies=request.getCookies();
        if (cookies==null){
            return "index";
        }
        User user=null;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                user=userMapper.findByToken(token);
                if (user!=null)
                    request.getSession().setAttribute("user",user);
                break;
            }
        }
        if (user==null){
            model.addAttribute("user","用户未登录");
            return "publish";
        }

        Question question=new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());

        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);

        return "redirect:/";
    }
}
