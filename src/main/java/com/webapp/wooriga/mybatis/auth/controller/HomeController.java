package com.webapp.wooriga.mybatis.auth.controller;

import com.webapp.wooriga.mybatis.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final Environment env;
    private final KakaoService kakaoService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${social.kakao.redirect}")
    private String kakaoRedirect;

    /*
    @RequestMapping("/")
    public ModelAndView main(ModelAndView mav) {
        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);

        mav.addObject("title", "로그인");
        mav.addObject("loginUrl", loginUrl);
        mav.setViewName("index");
        return mav;
    }
    */

    @RequestMapping("/logout/{code}")
    public Map logout(@PathVariable String code) {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(code);
        try {
            kakaoService.logout(code);
            System.out.println("로그아웃 완료");
            map.put("success", true);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}