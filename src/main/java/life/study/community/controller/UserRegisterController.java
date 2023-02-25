package life.study.community.controller;

import life.study.community.model.User;
import life.study.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class UserRegisterController {
    @Autowired
    private UserService userService;
    @GetMapping("/userRegister")
    public String userRegister(){

        return "userRegister";
    }
    @PostMapping("/userRegister")
    public String userRegister(@RequestParam(value = "username",required = false)String username,
                               @RequestParam(value = "password",required = false)String   password,
                               @RequestParam(value = "bio",required = false)String bio,
                               @RequestParam(value = "imgUrl",required = false)Integer imgUrl,
                               HttpServletRequest request, RedirectAttributesModelMap model){
        User user=new User();
        user.setName(username);
        user.setAccountId(UUID.randomUUID().toString());
        user.setToken(UUID.randomUUID().toString());
        user.setBio(bio);
        user.setUsertype("locationUser");
        user.setPassword(password);

        user.setImgUrl("https://foruda.gitee.com/avatar/1676859954971005111/8796378_yswork_1676859954.png");
        int res=userService.userCreateOrUpdate(user);

        if (res==1){
            model.addFlashAttribute("resYes","注册成功，请点此登录");
            return "redirect:/userRegister";
        }else {
            model.addFlashAttribute("resNo","用户名重复，请重新输入");
            return "redirect:/userRegister";
        }
    }
}
