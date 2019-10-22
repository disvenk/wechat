package com.wechat.wechat.gongzhonghaoLogin;


import io.github.biezhi.ome.OhMyEmail;

import javax.mail.MessagingException;

import java.io.File;

import static io.github.biezhi.ome.OhMyEmail.SMTP_163;
import static io.github.biezhi.ome.OhMyEmail.SMTP_ENT_QQ;

/**
 * @author yanjuan
 * @date 18/1/16 17:55
 */
public class EmailUtil {

    /**
     *
     * @param subject 主题
     * @param from 发件人别名
     * @param to 收件人
     * @param text 文本信息
     * @param html 模板信息
     */
    public static boolean  sendEmail(String subject, String from, String to , String text, String html, File file){

       // OhMyEmail.config(SMTP_ENT_QQ(false), "13477058114@163.com", "daishaowen472810");
        OhMyEmail.config(SMTP_163(false), "13477058114@163.com", "disvenk472810");
        try {
            OhMyEmail.subject(subject)
                    .from(from)
                    .to(to)
                    .text(text)
                    .html(html)
                    //.attach(file)
                    .send();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            //throw new RuntimeException("发送邮件失败");
            return false;

        }

    }

    public static void main(String[] args) {
        String subject = "豆捞坊配置上线";

        String html = "<table>\n" +
                "    <tr><td>配置状态：</td><td>已完成配置</td></tr>\n" +
                "    <tr><td>品牌名称：</td><td>豆捞坊</td></tr>\n" +
                "    <tr><td>门店名称：</td><td>豆捞坊金桥店</td></tr>\n" +
                "  </table>\n" +
                "  <table border=\"1\" style=\"border-collapse:collapse;margin-top: 30px;\">\n" +
                "      <tr>\n" +
                "        <th rowspan=\"2\">门店名称</th>\n" +
                "        <th rowspan=\"2\">计费方式</th>\n" +
                "        <th rowspan=\"2\">新用户消费订单</th>\n" +
                "        <th colspan=\"2\">新用户消费订单</th>\n" +
                "        <th colspan=\"2\">回头用户消费订单</th>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <th>抽佣对象</th>\n" +
                "        <th>抽佣比例</th>\n" +
                "        <th>抽佣对象</th>\n" +
                "        <th>抽佣比例</th>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td>大宁店</td>\n" +
                "        <td>按效果收费</td>\n" +
                "        <td>订单总额</td>\n" +
                "        <td>10</td>\n" +
                "        <td>实收金额</td>\n" +
                "        <td>12</td>\n" +
                "        <td>100</td>\n" +
                "      </tr>\n" +
                "  </table>\n" +
                "  <table border=\"1\"  style=\"border-collapse:collapse;margin-top: 30px;\">\n" +
                "    <tr>\n" +
                "      <th>门店名称</th>\n" +
                "      <th>计费方式</th>\n" +
                "      <th>有效期（天）</th>\n" +
                "      <th>收取费用（元）</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td>金桥店</td>\n" +
                "      <td>按周期付费</td>\n" +
                "      <td>120</td>\n" +
                "      <td>1200</td>\n" +
                "    </tr>\n" +
                "  </table>";
            sendEmail("豆捞坊配置上线","测试邮件","13477058114@163.com","申请上线",html,null);
                    //主题，账号昵称(可以随便改，不传或者null显示邮箱)，接受者，文字内容，html代码(二者有后者无前者)
    }



}
