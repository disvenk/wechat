package com.wechat.wechat.utils;

import com.alibaba.fastjson.JSONObject;

import static com.wechat.wechat.gongzhonghaoLogin.AuthUtils.getAccToken;


/**
 * Created by disvenk.dai on 2018-09-17 15:54
 */
public class WechatFaPiaoUtils {

    /**
     * @Description:获取S_pappid
     * @Author:disvenk.dai
     * @Date:19:05 2018/6/1 0001
     */
    public static String getS_pappid() {
        String url = "https://api.weixin.qq.com/card/invoice/seturl?access_token=" + getAccToken();

        String token = HttpRequestUtils.httpPost(url,"{}");
        JSONObject jsonObject = JSONObject.parseObject(token);
        String invoice_url = jsonObject.getString("invoice_url");
        String s_pappid = invoice_url.split("&")[1].split("=")[1];
        return s_pappid;
    }

    /**
     * @Description:设置商户联系方式
     * @Author:disvenk.dai
     * @Date:20:04 2018/6/1 0001
     */
    public static void setPhone(String tel, Integer time) throws Exception {
        String jsonStr =
                "{ \n" +
                        "\"contact\":{ \"phone\":\n" + tel + "," +
                        "\"time_out\":\n" + time +
                        "}\n" +
                        "}";
        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/setbizattr?action=set_contact&access_token=" + getAccToken(), jsonStr);
        System.out.println(s);

    }

    /**
     * @Description:获取授权页ticket
     * @Author:disvenk.dai
     * @Date:19:59 2018/6/1 0001
     */
    public static String getTicket() {
        String s = HttpRequestUtils.httpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + getAccToken() + "&type=wx_card&offset_type=1");
        JSONObject jsonObject = JSONObject.parseObject(s);
        String ticket = jsonObject.getString("ticket");
        return ticket;
    }


}
