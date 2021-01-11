package com.geek.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * @author geek
 */
public class CheckUtil {

    // 登录微信公众平台官网后，在公众平台官网的开发-基本设置页面，勾选协议成为开发者，点击“修改配置”按钮，填写服务器地址（URL）、Token 和 EncodingAESKey，其中 URL 是开发者用来接收微信消息和事件的接口 URL。Token 可由开发者可以任意填写，用作生成签名（该 Token 会和接口 URL 中包含的 Token 进行比对，从而验证安全性）。EncodingAESKey 由开发者手动填写或随机生成，将用作消息体加解密密钥。
    //
    //同时，开发者可选择消息加解密方式：明文模式、兼容模式和安全模式。模式的选择与服务器配置在提交后都会立即生效，请开发者谨慎填写及选择。加解密方式的默认状态为明文模式，选择兼容模式和安全模式需要提前配置好相关加解密代码，详情请参考消息体签名及加解密部分的文档 。
    //
    private static final String TOKEN = "geek";

    /**
     * 1）将 token、timestamp、nonce 三个参数进行字典序排序。
     * 2）将三个参数字符串拼接成一个字符串进行 sha1 加密。
     * 3）开发者获得加密后的字符串可与 signature 对比，标识该请求来源于微信。
     *
     * @param signature
     * @param timeStamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timeStamp, String nonce) {
        String[] arr = {TOKEN, timeStamp, nonce};
        // 排序。
        Arrays.sort(arr);

        // 生成字符串。
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }

        // sha1 加密。
        String temp = DigestUtils.sha1Hex(content.toString());

        return temp.equals(signature);
    }

}
