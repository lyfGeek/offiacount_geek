package com.geek.servlet;

import com.geek.util.CheckUtil;
import com.geek.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author geek
 */
@WebServlet("/wx.do")
public class WeixinServlet extends HttpServlet {

    /**
     * get ~ 获取 Access token。
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        // 微信加密签名，signature 结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce 参数。
        String signature = req.getParameter("signature");
        // 时间戳。
        String timestamp = req.getParameter("timestamp");
        // 随机数。
        String nonce = req.getParameter("nonce");
        // 随机字符串。
        String echostr = req.getParameter("echostr");

        PrintWriter printWriter = resp.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            printWriter.println(echostr);
        }
    }

    /**
     * 接受回复文本消息。
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        // 传回给微信后台。
        PrintWriter printWriter = resp.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(req);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String createTime = map.get("CreateTime");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String msgId = map.get("MsgId");

            String message = null;
//            if ("text".equals(msgType)) {
            // 普通消息：文本消息。
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                } else if ("2".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
                } else if ("?".equals(content) || "？".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                // 事件推送消息。
                String eventType = map.get("event");
                // 关注。
                if (MessageUtil.MESSAGE_SUBSCRIBE_EVENT.equals(eventType)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }
            }
            System.out.println(message);
            // 传回给微信后台。
            printWriter.println(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }

}


// http://geek.free.idcfengye.com/wx.do
