package life.study.community.controller;

import life.study.community.dto.ResultDto;
import life.study.community.dto.UserChangeDto;
import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.model.User;
import life.study.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserChangeController {
    @Autowired
    private UserService userService;
    @PostMapping("/userChange")
    public Object userChange(@RequestBody UserChangeDto userChangeDto,
                             HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setBio(userChangeDto.getBio());
        updateUser.setName(userChangeDto.getUsername());
        updateUser.setPassword(userChangeDto.getPassword());
        updateUser.setGmtModified(System.currentTimeMillis());
        updateUser.setImgUrl(user.getImgUrl());
        updateUser.setAccountId(user.getAccountId());
        updateUser.setToken(user.getToken());
        updateUser.setGmtCreate(user.getGmtCreate());
        System.out.println("updateUser==="+updateUser);

        userService.createOrUpdate(updateUser);
        return "redirect:/userAllQuestion/user";
    }
}
