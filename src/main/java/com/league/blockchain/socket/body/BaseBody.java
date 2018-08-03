package com.league.blockchain.socket.body;

import com.league.blockchain.common.AppId;
import com.league.blockchain.common.CommonUtil;

public class BaseBody {
    // 消息发送时间
    private Long time = System.currentTimeMillis();
    // 每条消息的唯一id
    private String messageId = CommonUtil.generateUuid();
    // 回复的哪条消息
    private String responseMsgId;
    // 自己是谁
    private String appId = AppId.value;

    public BaseBody(){

    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getResponseMsgId() {
        return responseMsgId;
    }

    public void setResponseMsgId(String responseMsgId) {
        this.responseMsgId = responseMsgId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "BaseBody{" +
                "time=" + time +
                ", messageId='" + messageId + '\'' +
                ", responseMsgId='" + responseMsgId + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
