/*
package com.daishaowen.test.crossDomain;*/
package com.wechat.wechat.controller;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DomainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest res, ServletResponse req, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) req;
        HttpServletRequest request = (HttpServletRequest)res;
       /* Cookie[] cookies = request.getCookies();
        String cookieStr = "";
        for(Cookie cookie :cookies){
            if(cookie.getName().equals("JSESSIONID")){
                cookieStr = cookie.getValue();
            }
        }*/
        String origin = request.getHeader("Origin");
        //response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
       // Cookie cookie = new Cookie("JSESSIONID",cookieStr);

       //response.addCookie(cookie);
       // response.setHeader("Access-Control-Allow-Origin", "http://192.168.1.107:8086");
        response.setHeader("Access-Control-Allow-Origin", origin);
        //指定客户端允许发送cookie
        //服务器端 Access-Control-Allow-Credentials = true时，参数Access-Control-Allow-Origin 的值不能为 '*'
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

//Access-Control-Allow-Credentials 设为true的话，Access-Control-Allow-Origin就不能设为*了，
// 只能改成具体的域了，这样就可以多次请求取到的sessionid就一致了。
       // response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
        //Access-Control-Expose-Headers
        //默认情况下我们在浏览器下面看到的首部信息是默认的，有些我们访问不到
        //我们可以使用上面的那个参数，然后在后面添加我们想让浏览看到的额外首部
        chain.doFilter(res, req);
    }

    @Override
    public void destroy() {

    }
}


