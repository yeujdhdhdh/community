package life.study.community.controller;

import life.study.community.dto.AccessTokenDto;
import life.study.community.dto.GithubUser;
import life.study.community.provider.GithubProvider;
import life.study.community.provider.Provide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private Provide provide;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,@RequestParam(name = "state")String state) throws Exception {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken= githubProvider.getAccessToken(accessTokenDto);
        System.out.println("accessToken="+accessToken);
        GithubUser  githubUser=githubProvider.getUser(accessToken);
        System.out.println("githubUser="+githubUser);
        System.out.println("user======"+provide.httpGet(accessToken));
        return "index";
    }
}
