package life.study.community.controller;

import life.study.community.dto.PageDto;
import life.study.community.enums.NotificationStatusEnum;
import life.study.community.mapper.UserMapper;
import life.study.community.model.User;
import life.study.community.service.NotificationService;
import life.study.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserQuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/userAllQuestion/{action}")
    public String userALlQuestion(@PathVariable(name = "action")String action , HttpServletRequest request, Model model,
            @RequestParam(name = "page",defaultValue = "1")Integer page, @RequestParam(name = "size",defaultValue = "5")Integer size){


        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDto userPageDto = questionService.list(user.getId(), page, size);
            model.addAttribute("userPageDto",userPageDto);
        } else if ("replies".equals(action)) {
            PageDto notifierPageDto = notificationService.list(user.getId(), page, size);
            Integer unreadCount=notificationService.unreadCount(user.getId());
            model.addAttribute("userPageDto",notifierPageDto);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }else if ("user".equals(action)){
            model.addAttribute("section","user");
            model.addAttribute("sectionName",((User) request.getSession().getAttribute("user")).getName());
        }
        return "userAllQuestion";
}

}
