package life.study.community.controller;

import life.study.community.dto.AccessTokenDto;
import life.study.community.dto.GithubUser;
import life.study.community.mapper.UserMapper;
import life.study.community.model.User;
import life.study.community.provider.GithubProvider;
import life.study.community.provider.Provide;
import life.study.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.netty.http.server.HttpServerRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private Provide provide;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request, HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken= githubProvider.getAccessToken(accessTokenDto);

        GithubUser  githubUser=githubProvider.getUser(accessToken);

        if (githubUser!=null&&githubUser.getId()!=null){
            //登录成功
            User user=new User();
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId());;
            user.setToken(UUID.randomUUID().toString());
            user.setBio(githubUser.getBio());
            user.setImgUrl(githubUser.getAvatar_url());
            user.setUsertype("indexGithub");
            userService.createOrUpdate(user,request);

            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("unreadCount");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/login";
    }
}
