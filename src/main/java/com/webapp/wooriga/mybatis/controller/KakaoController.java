package com.webapp.wooriga.mybatis.controller;

import com.google.gson.Gson;
import com.webapp.wooriga.mybatis.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class KakaoController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final KakaoService kakaoService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${social.kakao.redirect}")
    private String kakaoRedirect;

    /**
     * 카카오 로그인 페이지
     */
    @GetMapping
    public ModelAndView socialLogin(ModelAndView mav) {

        System.out.println("start");

        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);
        System.out.println(loginUrl.toString());

        mav.addObject("title", "로그인");
        mav.addObject("loginUrl", loginUrl);
        mav.setViewName("index");
        return mav;
    }

    /**
     * 카카오 인증 완료 후 리다이렉트 화면
     */


    @GetMapping(value = "/kakao")
    public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code) {
        System.out.println("code : " + code);
        String access_token = kakaoService.getAccessToken(code);
        System.out.println("access : " + access_token);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_token);
        System.out.println("login Controller : " + userInfo);
        mav.addObject("access_token", access_token);
        mav.addObject("user", userInfo);
        mav.setViewName("redirectKakao");
        return mav;
    }

}