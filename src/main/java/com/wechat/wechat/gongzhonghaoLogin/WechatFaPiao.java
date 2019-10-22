package com.wechat.wechat.gongzhonghaoLogin;

import com.alibaba.fastjson.JSONObject;
import com.wechat.wechat.utils.ApplicationUtils;
import com.wechat.wechat.utils.HttpRequestUtils;
import com.wechat.wechat.utils.SejsUtil;
import com.wechat.wechat.utils.WechatFaPiaoUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by disvenk.dai on 2018-09-17 14:55
 */
@Controller
@RequestMapping("fapiao")
public class WechatFaPiao {

    /**
     *@Description:获取授权页链接
     *@Author:disvenk.dai
     *@Date:16:46 2018/6/1 0001
     */
    @RequestMapping("getAuthUrl")
    @ResponseBody
    public void getTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String agent = request.getHeader("user-agent");
        String accToken = AuthUtils.getAccToken();
        WechatFaPiaoUtils.setPhone("13477058114",10000);//开票之前要设置商户联系方式
        JSONObject json = new JSONObject();
        json.put("s_pappid", WechatFaPiaoUtils.getS_pappid());//开票平台在微信的标识号，商户需要找开票平台提供
        String orderId =  ApplicationUtils.randomUUID();
        json.put("order_id",orderId);//订单id，在商户内单笔开票请求的唯一识别号，
        System.out.println("订单号"+orderId);
        json.put("money",1*100);//订单金额，以分为单位
        json.put("timestamp", new Date().getTime());//	时间戳
        json.put("source","web");//开票来源，app：app开票，web：微信h5开票，wxa：小程序开发票，wap：普通网页开票
        json.put("redirect_url","http://2960a64a.ngrok.io/wechat/index/index");//授权成功后跳转页面。本字段只有在source为H5的时候需要填写，引导用户在微信中进行下一步流程
        json.put("ticket",WechatFaPiaoUtils.getTicket());//授权页ticket
        json.put("type",1);//授权类型，0：开票授权，1：填写字段开票授权，2：领票授权

        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/getauthurl?access_token=" + accToken, json.toJSONString());

        JSONObject jsonObject = JSONObject.parseObject(s);
        System.out.println(jsonObject.getString("auth_url"));
        response.sendRedirect(jsonObject.getString("auth_url"));
    }

    /**
     *@Description:查询授权完成状态
     *@Author:disvenk.dai
     *@Date:11:51 2018/6/2 0002
     */
    @RequestMapping("checkeAuthorizeStatus")
    @ResponseBody
    public String getAuthorizeStatus(){

        String jsonStr =
                "{ \n"+
                        "\"order_id\":\"000e1dcf33c249f9986563ec82c85f69\""+","+
                        "\"s_pappid\":\"" +WechatFaPiaoUtils.getS_pappid()+"\""+
                        "}";
        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/getauthdata?access_token=" + AuthUtils.getAccToken(), jsonStr);
        return  s;
    }


    /**
     *@Description:设置授权页字段
     *@Author:disvenk.dai
     *@Date:12:11 2018/6/2 0002
     */
    @RequestMapping("setAuthorizePageFiled")
    @ResponseBody
    public String setAuthorizePageFiled(){
        JSONObject root = new JSONObject();
        JSONObject auth_field = new JSONObject();

        JSONObject user_field = new JSONObject();
        user_field.put("show_title",1);
        user_field.put("show_phone",1);
        user_field.put("show_email",1);
        user_field.put("require_phone",1);
        user_field.put("require_email",1);

        JSONObject biz_field = new JSONObject();
        biz_field.put("show_title",1);
        biz_field.put("show_tax_no",1);
        biz_field.put("show_addr",1);
        biz_field.put("show_phone",1);
        biz_field.put("show_bank_type",1);
        biz_field.put("show_bank_no",1);
        biz_field.put("require_tax_no",1);
        biz_field.put("require_addr",1);
        biz_field.put("require_phone",1);
        biz_field.put("require_bank_type",1);
        biz_field.put("require_bank_no",1);

        auth_field.put("user_field",user_field);
        auth_field.put("biz_field",biz_field);

        root.put("auth_field",auth_field);

        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/setbizattr?action=set_auth_field&access_token=" + AuthUtils.getAccToken(), root.toJSONString());
        return s;
    }

    /**
     *@Description:开蓝票
     *@Author:disvenk.dai
     *@Date:16:20 2018/6/2 0002
     */
    @RequestMapping("saleTicket")
    @ResponseBody
    public String blueTicket(String orderId){

        BigDecimal money = BigDecimal.valueOf(1);

        Map<String, String> sejs = SejsUtil.sejs(money);

        String jshj = money.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        JSONObject json = new JSONObject();
        JSONObject invoiceinfo = new JSONObject();
        invoiceinfo.put("wxopenid","oBHT9sqGEv6pZfSl-R_C-pYs59uQ");//用户的openid 用户知道是谁在开票
        invoiceinfo.put("ddh","005f79f8023e49ff8fdb6e75164c3819");//订单号，企业自己内部的订单号码,（订单号）需要和拉起授权页时的order_id保持一致，否则会出现未授权订单号的报错
        String dateStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSSS");
        System.out.println("流水号"+"135065"+dateStr);
        invoiceinfo.put("fpqqlsh", "135065"+dateStr);//发票请求流水号，1350659001唯一识别开票请求的流水号,为开票的唯一标识，头六位需要和后台的商户识别号保持一致
        invoiceinfo.put("nsrsbh","911101086000159940");//纳税人识别码
        invoiceinfo.put("nsrmc","同方鼎欣科技股份有限公司");//纳税人名称
        invoiceinfo.put("nsrdz","北京市海淀区上地街5号高立二千大厦5层");//纳税人地址
        invoiceinfo.put("nsrdh","01062981691");//纳税人电话
        invoiceinfo.put("nsrbank","中国建设银行北京上地支行");//纳税人开户行
        invoiceinfo.put("nsrbankid","11001045300056008386");//纳税人银行账号
        invoiceinfo.put("ghfmc","同方鼎欣科技股份有限公司");//购货方名称
        invoiceinfo.put("kpr","谢三哥");//	开票人
        invoiceinfo.put("jshj",jshj);//价税合计
        invoiceinfo.put("hjje",sejs.get("hjje"));//合计金额
        invoiceinfo.put("hjse",sejs.get("hjse"));//合计税额

        List<JSONObject> list = new ArrayList<>();
        JSONObject invoicedetail = new JSONObject();
        invoicedetail.put("fphxz","0");//发票行性质 0 正常 1折扣 2 被折扣
        invoicedetail.put("spbm","3070401000000000000");//19位税收分类编码
        invoicedetail.put("xmmc","餐饮费");//项目名称
        invoicedetail.put("xmsl","1");//项目数量
        invoicedetail.put("xmdj",sejs.get("hjje"));//项目单价
        invoicedetail.put("xmje",sejs.get("hjje"));//项目金额 不含税，单位元 两位小数
        invoicedetail.put("sl","0.06");//税率 精确到两位小数 如0.01
        invoicedetail.put("se", sejs.get("hjse"));//税额 单位元 两位小数
        list.add(invoicedetail);

        invoiceinfo.put("invoicedetail_list",list);//发票行项目数据

        json.put("invoiceinfo",invoiceinfo);

        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/makeoutinvoice?access_token=" + AuthUtils.getAccToken(), json.toJSONString());
        return s;
    }

    /**
     *@Description:查询已开具的发票
     *@Author:disvenk.dai
     *@Date:16:58 2018/6/6 0006
     */
    @RequestMapping("checkSaledTicket")
    @ResponseBody
    public String checkSaledTicket(){
        String jsonStr =
                "{"+
                        "\"fpqqlsh\":\"13506522334455\","+
                        "\"nsrsbh\":\"911101086000159940\""+
                        "}";

        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/queryinvoceinfo?access_token=" + AuthUtils.getAccToken(), jsonStr);
        return s;
    }

    /**
     *@Description:拒绝开票
     *@Author:disvenk.dai
     *@Date:16:14 2018/6/2 0002
     */
    @RequestMapping("refuseTicket")
    @ResponseBody
    public String refuseTicket(){
        String jsonStr =
                "{ \n"+
                        "\"order_id\":\"bf506a0c-01ad-407c-b9be-c7671b2b01de\""+","+
                        "\"s_pappid\":\"" +WechatFaPiaoUtils.getS_pappid()+"\""+","+
                        "\"reason\":\"您长的太丑了，不给您开发票\""+","+
                        "\"url\":\"http://c7eb1b93.ngrok.io/index\""+
                        "}";
        //url点击详情的时候就跳转到我们自定义的页面
        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/rejectinsert?access_token=" + AuthUtils.getAccToken(), jsonStr);
        return s;
    }

    /**
     *@Description:关联商户号与开票平台
     *@Author:disvenk.dai
     *@Date:11:30 2018/6/5 0005
     */
    @RequestMapping("unionBusinessWithTickePlatForm")
    @ResponseBody
    public String unionBusinessWithTickePlatForm(){
        String jsonStr =
                "{" +
                        "\"paymch_info\":" +
                        "{" +
                        "\"mchid\":\"1350659001\"," +
                        "\"s_pappid\":"+"\""+WechatFaPiaoUtils.getS_pappid() +"\""+
                        "}" +
                        "}";
        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/setbizattr?action=set_pay_mch&access_token=" + AuthUtils.getAccToken(), jsonStr);
        return s;
    }

    /**
     *@Description:创建卡券模板
     *@Author:disvenk.dai
     *@Date:21:11 2018/6/7 0007
     */
    @RequestMapping("createCardTemplate")
    @ResponseBody
    public String createCardTemplate(){
        JSONObject json = new JSONObject();
        JSONObject invoice_info = new JSONObject();
        JSONObject base_info = new JSONObject();
        invoice_info.put("base_info","谢三哥");
        invoice_info.put("payee","上海三哥锅碗瓢盆有限公司");
        // invoice_info.o
        return null;
    }

    /**
     *@Description:查询商户号与开票平台关联情况
     *@Author:disvenk.dai
     *@Date:16:08 2018/6/5 0005
     */
    @RequestMapping("checkBusinessWithTicketPlatformStatus")
    @ResponseBody
    public String checkBusinessWithTicketPlatformStatus(){
        String jsonStr =
                "{" +
                        "\"mchid\":\"1350659001\"," +
                        "\"s_pappid\":"+"\""+WechatFaPiaoUtils.getS_pappid() +"\""+
                        "}";
        String s = HttpRequestUtils.httpPost("https://api.weixin.qq.com/card/invoice/setbizattr?action=get_pay_mch&access_token=" + AuthUtils.getAccToken(), jsonStr);
        return s;
    }
}
