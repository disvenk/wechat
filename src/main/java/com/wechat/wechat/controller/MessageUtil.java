package com.wechat.wechat.controller;

import com.thoughtworks.xstream.XStream;
import com.wechat.wechat.wenxin.AesException;
import com.wechat.wechat.wenxin.WXBizMsgCrypt;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_SHORTVIDEO = "shortvideo";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_FAPIAO = "user_authorize_invoice";

    /**
     *如果公众号后台使用明文传输则使用该方法
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        try {
            InputStream ins = request.getInputStream();
            Document doc = reader.read(ins);
            System.out.println(doc);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                    map.put(e.getName(), e.getText());
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //过的加密对象
    public static WXBizMsgCrypt getWxCrypt() {
        WXBizMsgCrypt crypt=null;
        try {
            crypt = new WXBizMsgCrypt("disvenk","htaaGF2c5fvqJoCqzTHTJld5yUdO0O5xb5qnmNZsDqS","wxa6a8dd22934f43b6");
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return crypt;
    }

    //转换成xml
    public static String textToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        //替换根元素
        //使用加密和不加密时一定要注意这里
        xStream.alias("xml",textMessage.getClass());
        String s = xStream.toXML(textMessage);
        return s;
    }

    /**
     * 如果公众号后台配置了安全密文，则调用此方法解密
     */
    public static Map<String, String> parseXmlCrypt(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuffer buf = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buf.append(line);
        }
        reader.close();
        inputStream.close();

        WXBizMsgCrypt wxCeypt =  MessageUtil.getWxCrypt();
        // 微信加密签名
        String msgSignature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String respXml = wxCeypt.decryptMsg(msgSignature, timestamp, nonce, buf.toString());

        //SAXReader reader = new SAXReader();
        Document document = DocumentHelper.parseText(respXml);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        //inputStream.close();
        //inputStream = null;

        return map;
    }
}
