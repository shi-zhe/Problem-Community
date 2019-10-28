package cn.shizhe.community.controller;

import cn.shizhe.community.provider.GitHubProvider;
import cn.shizhe.community.vo.GitHubAccessTokenVo;
import cn.shizhe.community.vo.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code,@RequestParam("state") String state){
        GitHubAccessTokenVo accessTokenVo = new GitHubAccessTokenVo();
        accessTokenVo.setClient_id(client_id);
        accessTokenVo.setClient_secret(client_secret);
        accessTokenVo.setCode(code);
        accessTokenVo.setState(state);
        accessTokenVo.setRedirect_uri(redirect_uri);
        String accessToken = gitHubProvider.getAccessToken(accessTokenVo);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        System.out.println(gitHubUser.getName());
        return "index";
    }
}
