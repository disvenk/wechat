package com.wechat.wechat.gongzhonghaoLogin;

import com.alibaba.fastjson.JSONObject;
import com.wechat.wechat.utils.HttpRequest;
import com.wechat.wechat.utils.HttpRequestUtils;

public class AuthUtils {

    public static final String appId = "wx36bd5b9b7d264a8c";
    public static final String secret = "807530431fe6e19e3f2c4a7d1a149465";

    /**
     * @Description: 通过Code获取微信账号的OpenId和网页授权token
     * @author: disvenk
     * @date: 2017/5/22
     */
    public static JSONObject findOpenIdAndToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appId +
                "&secret=" + secret +
                "&code=" + code +
                "&grant_type=authorization_code";
        String json = HttpRequestUtils.httpPost(url,null);
        JSONObject obj = JSONObject.parseObject(json);

        return  obj;
    }

    /*
    * 获取用户信息
    * */
    public static JSONObject getUserInfo(String openId,String token){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+
                    "&openid="+openId+"&lang=zh_CN";
        String json = HttpRequestUtils.httpGet(url);
        System.out.println(json);
        JSONObject obj = JSONObject.parseObject(json);
        return obj;
    }

    /*
    * 获取基础access_token
    * */
    public static String getAccToken() {
        //String url = "http://106.14.96.154:9999/token/getstr/" + appId + "/" + secret;
        /*String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appId +
                "&secret=" + secret;*/
        //String token = HttpRequest.sendPost(url, null);
        //JSONObject jsonObject = JSONObject.parseObject(token);
        //System.out.println(token);
        //return jsonObject.getString("access_token");
        String url = "http://139.196.106.198:9999/token/getstr/" + appId + "/" + secret;
        String token = HttpRequestUtils.httpGet(url);
        return token;
    }

    /*
    * 生成带参数的二维码
    * */
    public static JSONObject getQrCode(String param){
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+getAccToken();
        String json = HttpRequestUtils.httpPost(url,param);
        JSONObject obj = JSONObject.parseObject(json);
        return obj;
    }

    public static void main(String[] args){
//        String jsonStr =
//                "{\n" +
//                        "\"expire_seconds\":1800,\n" +
//                        "\"action_name\":\"QR_SCENE\",\n" +
//                        "\"action_info\":\n" +
//                            "{\n" +
//                                "\"scene\":\n" +
//                                    "{\n" +
//                                        "\"scene_id\":123\n" +
//                                    "}\n" +
//                        "    }\n" +
//                        "}";
//        JSONObject info = getQrCode(jsonStr);
//        System.out.println(info);
        String url = "http://139.196.106.198:9999/token/getstr/" + appId + "/" + secret;
       String token = HttpRequestUtils.httpGet(url);
        System.out.println(token);
    }
}
