//package com.wechat.wechat.security;
//
//import com.alibaba.fastjson.JSONObject;
//
//public class TokenUserDetails {
//    private GenericLogin genericLogin;
//    private MyUserDetails myUserDetails;
//
//    public TokenUserDetails(MyUserDetails myUserDetails) {
//        this.myUserDetails = myUserDetails;
//        if (myUserDetails != null)
//            parseGenericLogin(myUserDetails.getExpandData());
//    }
//
//    private void parseGenericLogin(JSONObject expandData) {
//        if (expandData != null)
//            this.genericLogin = JSONObject.parseObject(expandData.toString(), GenericLogin.class);
//    }
//
//    public GenericLogin getGenericLogin() {
//        return genericLogin;
//    }
//}
