package com.wechat.wechat.controller;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by disvenk.dai on 2018-06-07 17:35
 */
@Controller
@RequestMapping("page")
public class CoolieController {

    @RequestMapping("index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("cookie","代wen文");
        response.sendRedirect("http://localhost:8089/index/index.html");
    }

    @RequestMapping("setCookie")
    @ResponseBody
    public String setCookie(HttpServletRequest request) throws IOException {
        request.getSession().setAttribute("cookie","代文");
        //String cookie = request.getSession().getAttribute("cookie").toString();
        //System.out.println(cookie);
        System.out.println("设置cookie成功");

        return "ok";
    }

    @RequestMapping("getCookie")
    @ResponseBody
    public String getCookie(HttpServletRequest request){
        String cookie = request.getSession().getAttribute("cookie").toString();
        System.out.println(cookie);
        return "ook";
    }
}
