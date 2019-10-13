package com.webapp.wooriga.mybatis.controller;

import com.webapp.wooriga.mybatis.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final KakaoService kakaoService;

    @RequestMapping("/logout/{code}")
    public String logout(@PathVariable String code) {

        System.out.println(code);
        try {
            kakaoService.logout(code);
            System.out.println("로그아웃 완료");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return "index";
    }
}
