package life.study.community.controller;

import life.study.community.dto.AccessTokenDto;
import life.study.community.dto.GiteeUser;
import life.study.community.dto.GithubUser;
import life.study.community.model.User;
import life.study.community.provider.GiteeProvider;
import life.study.community.provider.GithubProvider;

import life.study.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private GiteeProvider giteeProvider;
    @Value("${loginGithub.client.id}")
    private String clientId;
    @Value("${loginGithub.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Value("${loginGithub.redirect.uri}")
    private String loginGithubUrl;
    @Value("${gitee.client.id}")
    private String giteeClientId;
    @Value("${gitee.client.secret}")
    private String giteeClientSecret;
    @Value("${gitee.redirect.uri}")
    private String giteeRedirectUri;
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login(){
        return "/login";
    }
    @GetMapping("/loginCallback")
    public String loginCallback(@RequestParam(name = "code")String code,
                                @RequestParam(name = "state")String state,
                                HttpServletRequest request, HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(loginGithubUrl);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);

        String accessToken= githubProvider.getAccessToken(accessTokenDto);
        System.out.println("accessToken="+accessToken);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        System.out.println("githubUser="+githubUser);

        if (githubUser!=null&&githubUser.getId()!=null){
            //登录成功
            User user=new User();
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId());;
            user.setToken(UUID.randomUUID().toString());
            user.setUsertype("github");
            user.setImgUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);

            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/login";
        }

    }
    @GetMapping("/loginGiteeCallback")
    public String loginGiteeCallback(@RequestParam(name = "code")String code,
                                     HttpServletRequest request, HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState("1");
        accessTokenDto.setRedirect_uri(giteeRedirectUri);
        accessTokenDto.setClient_id(giteeClientId);
        accessTokenDto.setClient_secret(giteeClientSecret);
        String accesstoken=giteeProvider.getAccessToken(accessTokenDto);
        System.out.println(accesstoken);
        GiteeUser giteeUser=giteeProvider.getUser(accesstoken);
        System.out.println("githubUser="+giteeUser);

        if (giteeUser!=null&&giteeUser.getId()!=null){
            //登录成功
            User user=new User();
            user.setName(giteeUser.getName());
            user.setAccountId(giteeUser.getId());;
            user.setBio(giteeUser.getBio());
            user.setUsertype("gitee");
            user.setToken(UUID.randomUUID().toString());
            user.setImgUrl(giteeUser.getAvatarUrl());
            userService.createOrUpdate(user);

            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/login";
        }

    }
    @PostMapping("/login")
    public String loginVerify(@RequestParam(name = "username")String username,
                              @RequestParam(name = "password")String password,
                              HttpServletRequest request, RedirectAttributesModelMap model){
        int res= userService.userLoginVerify(username,password,request);
        if (res ==1){

            return "redirect:/";
        }else {
            model.addFlashAttribute("loginCode","用户名或者密码错误");
            return "redirect:/login";
        }


    }
    }


