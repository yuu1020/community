package com.example.community.controller;

import com.example.community.dto.AccessTokenDTo;
import com.example.community.dto.GithubUser;
import com.example.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,@RequestParam(name="state")String state )
    {
        AccessTokenDTo accessTokenDTo = new AccessTokenDTo();
        accessTokenDTo.setClient_id("15b62aa4afb8aea49628");
        accessTokenDTo.setClient_secret("78a1acc482f5b5bb5eb0f687880d2788431ce9ec");
        accessTokenDTo.setCode(code);
        accessTokenDTo.setRedirect_uri("http://localhost:8887/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTo);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getId());
        System.out.println(user.getName());
        return "index";
    }
}
