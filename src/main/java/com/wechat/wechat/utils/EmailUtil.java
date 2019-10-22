package com.wechat.wechat.utils;



/**
 * 发送邮件辅助类
 *
 */
public final class EmailUtil {
    private EmailUtil() {
    }

    /**
     * 发送邮件
     */
    public static final boolean sendEmail(Email email) {
        // 初始化邮件引擎
        EmailSender sender = new EmailSender(email.getHost());
        sender.setNamePass(email.getName(), email.getPassword(), email.getKey());
        if (sender.setFrom(email.getFrom()) == false)
            return false;
        if (sender.setTo(email.getSendTo()) == false)
            return false;
        if (email.getCopyTo() != null && sender.setCopyTo(email.getCopyTo()) == false)
            return false;
        if (sender.setSubject(email.getTopic()) == false)
            return false;
        if (sender.setBody(email.getBody()) == false)
            return false;
        if (email.getFileAffix() != null) {
            for (int i = 0; i < email.getFileAffix().length; i++) {
                if (sender.addFileAffix(email.getFileAffix()[i]) == false)
                    return false;
            }
        }
        // 发送


        return sender.sendout();
    }

    public static void main(String[] args) {
        /**
         * @param host 服务器地址
         * @param from 发送人
         * @param name 登录名
         * @param password 登录密码
         * @param key 授权码
         * @param sendTo 接收人
         * @param copyTo 抄送人
         * @param topic 主题
         * @param body 内容
         * @param fileAffix 附件的集合
         */


        //测试发送邮件
        sendEmail(new Email("smtp.163.com", "我是大蚊子", "13477058114@163.com", null, "disvenk472810", "545279664@qq.com", "545279664@qq.com", "自动回复", "来自大蚊子的回复", null));
        //password可以不要，但必须使用授权码key,from旧版本的需要自定义+name拼接，新版本不需要
    }

}