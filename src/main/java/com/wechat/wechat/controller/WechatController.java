package com.wechat.wechat.controller;

import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("msg")
public class WechatController {

    @GetMapping("/")
    public String index(String signature,String timestamp,String nonce,String echostr){
        System.out.println("加密签名："+signature);
        System.out.println("时间戳："+timestamp);
        System.out.println("随机数"+nonce);
        System.out.println("随机字符串"+echostr);
        boolean b = CheckUtil.checkSignature(signature, timestamp, nonce);

        if(b){
            System.out.println("校验成功");
            return echostr;
        }
        return null;
    }

    @PostMapping("/")
    public String msg(HttpServletRequest request){
        //如果采用密文传输则会传入该值
        String encryptType = request.getParameter("encrypt_type");
        System.out.println("加密了的值："+encryptType);
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 响应消息

        try{
            //进行验证
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                Map<String, String> requestMap = null;
                //判断是否使用了加密
                if("aes".equals(encryptType)){
                    System.out.println("进入解密");
                    //传入request解析用户发送过来的xml报文，并的到数据
                    requestMap=MessageUtil.parseXmlCrypt(request);
                    //将解析后的数据传入判断类型并生成返回结果
                    String respXml=CoreService.processRequest(requestMap);
                    //将回复结果进行加密
                    respXml=MessageUtil.getWxCrypt().encryptMsg(respXml,timestamp,nonce);
                    System.out.println(respXml);
                    return respXml;
                }else{
                    //如果用户的消息没有使用加密的方式
                    requestMap=MessageUtil.xmlToMap(request);
                    String respXml=CoreService.processRequest(requestMap);
                    System.out.println(respXml);
                  return respXml;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
