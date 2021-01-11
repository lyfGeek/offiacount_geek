package com.geek.po;

import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html
 *
 * @author geek
 */
@Data
public class TextMessage {

    /**
     * 开发者微信号。
     */
    private String toUserName;
    /**
     * 发送方帐号（一个 OpenID）。
     */
    private String fromUserName;
    /**
     * 消息创建时间（整型）。
     */
    private String createTime;
    /**
     * 消息类型，文本为 text。
     */
    private String msgType;
    /**
     * 文本消息内容。
     */
    private String content;
    /**
     * 消息 id，64 位整型。
     */
    private String msgId;

}
