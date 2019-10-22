package com.wechat.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("index")
public class IndexController {

    @GetMapping("/index")
    public String index(HttpServletRequest request, ModelMap modelMap){
        modelMap.addAttribute("name","disvenk");
        return "/index";
    }
}
