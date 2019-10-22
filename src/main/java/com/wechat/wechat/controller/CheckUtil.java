package com.wechat.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

public class CheckUtil {
    private static final String token="resto_plus_wechat_token";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(arr);
        //生成字符串
        StringBuffer content = new StringBuffer();
        for(String str : arr){
            content.append(str);
        }

        //sha1加密
        String sign = null;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(content.toString().getBytes("UTF-8"));
            sign = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return sign.equals(signature);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
