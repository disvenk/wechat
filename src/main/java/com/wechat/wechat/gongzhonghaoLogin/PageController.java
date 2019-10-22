package com.wechat.wechat.gongzhonghaoLogin;

import com.alibaba.fastjson.JSONObject;
import com.wechat.wechat.utils.ApplicationUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("page")
public class PageController {

    @RequestMapping(value = "/weixinAuthorizationHtml", method = {RequestMethod.GET})
    public void weixinAuthorization(HttpServletRequest request, HttpServletResponse response) throws Exception {
       int count = 0;
        // String redirectUrl = "http://df338fce.ngrok.io/login";
        String redirectUrl = "http%3A%2F%2F9ea5735d.ngrok.io%2Fwechat%2Flogin";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + AuthUtils.appId +
                "&redirect_uri=" + redirectUrl+ //【需用encode编码
                "&response_type=code" +
                "&scope=snsapi_userinfo" + //snsapi_base（默认）   snsapi_userinfo （需授权）
                "&state=STATE&connect_redirect=1#wechat_redirect";
                //&connect_redirect=1该参数可以防止重定向两次
        response.sendRedirect(url);
        //return "redirect:" + new String(url.getBytes("UTF-8"));
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, ModelMap modelMap){
        int count = 0;
        String code = request.getParameter("code");
        JSONObject json = AuthUtils.findOpenIdAndToken(code);
        String opendId = json.getString("openid");
        System.out.println(opendId);
        String token = json.getString("access_token");
        System.out.println(token);
        JSONObject userInfo = AuthUtils.getUserInfo(opendId, token);

        modelMap.addAttribute("nickname",userInfo.getString("nickname"));
        modelMap.addAttribute("sex",userInfo.getString("sex"));
        modelMap.addAttribute("province",userInfo.getString("province"));
        modelMap.addAttribute("city",userInfo.getString("city"));
        modelMap.addAttribute("country",userInfo.getString("country"));
        modelMap.addAttribute("headimgurl",userInfo.getString("headimgurl"));
        modelMap.addAttribute("privilege",userInfo.get("privilege"));
        return "index/index";
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        String dateStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSSS");
        String substring = dateStr.substring(12);
        String url = URLEncoder.encode("http://fdac840b.ngrok.io/","UTF-8");
        System.out.println(url);
        System.out.println(ApplicationUtils.randomUUID());
        System.out.println(substring);
    }

}
