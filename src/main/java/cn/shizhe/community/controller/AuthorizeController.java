package cn.shizhe.community.controller;

import cn.shizhe.community.entity.User;
import cn.shizhe.community.provider.GitHubProvider;
import cn.shizhe.community.service.UserService;
import cn.shizhe.community.vo.GitHubAccessTokenVo;
import cn.shizhe.community.vo.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
//github的授权登录
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @Autowired
    private UserService userService;

    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response){
        GitHubAccessTokenVo accessTokenVo = new GitHubAccessTokenVo();
        accessTokenVo.setClient_id(client_id);
        accessTokenVo.setClient_secret(client_secret);
        accessTokenVo.setCode(code);
        accessTokenVo.setState(state);
        accessTokenVo.setRedirect_uri(redirect_uri);
        String accessToken = gitHubProvider.getAccessToken(accessTokenVo);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if (gitHubUser != null){
            //登录成功
            String token = UUID.randomUUID().toString();
            User user = new User();
            user.setName(gitHubUser.getName());
            user.setToken(token);
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.insertUser(user);
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }
    }
}
