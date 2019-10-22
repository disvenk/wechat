package com.wechat.wechat.controller;

import java.util.Date;
import java.util.Map;

public class CoreService {
    /**
     * 处理微信发来的请求，然后回复
     *
     * @param
     * @return
     */
    public static String processRequest( Map<String, String> requestMap) {
        String respMessage = null;
        TextMessage tm=null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            //Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");


            // 回复文本消息
            tm= new TextMessage();
            tm.setToUserName(fromUserName);
            tm.setFromUserName(toUserName);
            tm.setCreateTime(new Date().getTime());

            // 文本消息
            if (msgType.equals(MessageUtil.MESSAGE_TEXT)) {
                System.out.println("进入文本消息");
                String content=requestMap.get("Content");
                /*if(content.startsWith("人工客服")){
                    TextMessage cus=new TextMessage();
                    cus.setToUserName(fromUserName);
                    cus.setFromUserName(toUserName);
                    cus.setCreateTime(new Date().getTime());
                    cus.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
                    //TransInfo t=new TransInfo();
                    //t.setKfAccount("debug@php-wechat-crm");
                   // cus.setTransInfo(t);

                    respMessage=MessageUtil.textToXml(cus);

                }else{*/
                    //回复的是什么形式的就返回什么形式的
                    tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                    respContent = "您发送的是文本消息！内容是："+content;
                //}

            }
            // 图片消息
            else if (msgType.equals(MessageUtil.MESSAGE_IMAGE)) {
                String picUrl = requestMap.get("PicUrl");
                String mediaId = requestMap.get("MediaId");
                String msgId = requestMap.get("MsgId");
                tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                respContent = "您发送的是图片消息！\n"
                                +"图片链接是："+picUrl+"\n"
                                +"媒体id是："+mediaId+"\n"
                                +"消息id是："+msgId;

            }
            // 语音消息
            else if (msgType.equals(MessageUtil.MESSAGE_VOICE)) {
                String mediaId = requestMap.get("MediaId");
                String format = requestMap.get("Format");
                String recognition = requestMap.get("Recognition");
                String msgId = requestMap.get("MsgId");
                tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                respContent = "您发送的是语音消息！\n"
                                +"媒体id是："+mediaId+"\n"
                                +"语音消息的格式是："+format
                                +"语音识别结果是："+recognition
                                +"消息id是："+msgId;
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.MESSAGE_VIDEO)) {
                String mediaId = requestMap.get("MediaId");
                String thumbMediaId = requestMap.get("ThumbMediaId");
                String msgId = requestMap.get("MsgId");
                tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                respContent = "您发送的是视频消息！\n"
                                +"媒体id是："+mediaId+"\n"
                                +"消息缩略图的媒体id是："+thumbMediaId+"\n"
                                +"消息id是："+msgId;
            }
            // 小视频消息
            else if (msgType.equals(MessageUtil.MESSAGE_SHORTVIDEO)) {
                String mediaId = requestMap.get("MediaId");
                String thumbMediaId = requestMap.get("ThumbMediaId");
                String msgId = requestMap.get("MsgId");
                tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                respContent = "您发送的是视频消息！\n"
                                +"媒体id是："+mediaId+"\n"
                                +"消息缩略图的媒体id是："+thumbMediaId+"\n"
                                +"消息id是："+msgId;
            }
            //地理位置
            else if (msgType.equals(MessageUtil.MESSAGE_LOCATION)) {
                System.out.println("进入地理位置");
                String location_X = requestMap.get("Location_X");
                String location_Y = requestMap.get("Location_Y");
                String scale = requestMap.get("Scale");
                String label = requestMap.get("Label");
                String msgId = requestMap.get("MsgId");
                tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                respContent = "您发送的是地理位置消息！\n"
                        +"地理位置纬度是："+location_X+"\n"
                        +"地理位置精度是："+location_Y+"\n"
                        +"地理位置缩放大小是："+scale+"\n"
                        +"地理位置信息是："+label+"\n"
                        +"消息id是："+msgId;
            }
            //链接消息
            else if (msgType.equals(MessageUtil.MESSAGE_LINK)) {
                String title = requestMap.get("Title");
                String description = requestMap.get("Description");
                String url = requestMap.get("Url");
                String msgId = requestMap.get("MsgId");
                tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                respContent = "您发送的是链接消息！\n"
                        +"消息标题是："+title+"\n"
                        +"消息描述是："+description+"\n"
                        +"消息链接是："+url+"\n"
                        +"消息id是："+msgId;
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.MESSAGE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注(订阅)
                if (eventType.equals(MessageUtil.MESSAGE_SUBSCRIBE)) {
                    System.out.println(fromUserName+"关注了小文java学习平台");
                    //这个是表示扫描带参数二维码事件引起的关注，会带来一个ticket
                    String ticket=requestMap.get("Ticket");
                    if(ticket!=null&&!"".equals(ticket)){
                        String eventKey=requestMap.get("EventKey");
                        respContent="谢谢您的关注！\n"
                                    +"您的ticket是："+ticket
                                    +"您的事件key是："+eventKey;
                    }else{
                        tm.setMsgType(MessageUtil.MESSAGE_TEXT);
                        respContent="谢谢您的关注！";
                    }

                // 取消关注(取消订阅)
                } else if (eventType.equals(MessageUtil.MESSAGE_UNSUBSCRIBE)) {
                        System.out.println(fromUserName+"取消关注了小文java学习平台");
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息


                    // 自定义菜单点击事件
                } else if (eventType.equals(MessageUtil.MESSAGE_CLICK)) {
                    // TODO 自定义菜单权没有开放，暂不处理该类消息
                    String eventKey=requestMap.get("EventKey");

                    if("language".equalsIgnoreCase(eventKey.trim())){

                    }
                }else if(eventType.equals("SCAN")){
                    String eventKey=requestMap.get("EventKey");
                    String ticket=requestMap.get("Ticket");
                    respContent="你的ticket是："+ticket;

                    //发票授权后推送事件
                }else if(eventType.equals("user_authorize_invoice")){
                    String createTime = requestMap.get("CreateTime");
                    String succOrderId = requestMap.get("SuccOrderId");
                    String failOrderId = requestMap.get("FailOrderId");
                    String authorizeAppId = requestMap.get("AuthorizeAppId");
                    String source = requestMap.get("Source");
                    tm.setMsgType(MessageUtil.MESSAGE_EVENT);
                    tm.setEvent(eventType);
                    if(succOrderId!=null && !"".equals(succOrderId)){
                        tm.setSuccOrderId(succOrderId);
                    }
                    if(failOrderId!=null && !"".equals(failOrderId)){
                        tm.setFailOrderId(failOrderId);
                    }
                    tm.setAuthorizeAppId(authorizeAppId);
                    tm.setSource(source);
                    System.out.println("进入了发票授权后推送事件");

                    //进行开票操作或者拒绝开票的一切逻辑

                    return "";//可以直接返回空串，微信服务器将不做任何处理
                }
            }

            tm.setContent(respContent);
            //respMessage = MessageUtil.textMessageToXml(textMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(respMessage==null){
            return MessageUtil.textToXml(tm);
        }else{
            return respMessage;
        }
        //return respMessage;
    }
}
