package com.holly.test.netty.privateprotocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/11 9:52
 */

public class Header {
    private int crcCode = 0xabef0101;//
    private int length;//消息长度
    private long sessionId;//session
    private byte type;//消息类型
    private byte priority;
    private Map<String,Object> attachment = new HashMap<String,Object>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
