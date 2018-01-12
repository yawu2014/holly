package com.holly.test.netty.privateprotocal;

/**
 * @Author liuyj
 * @Description: https://www.cnblogs.com/carl10086/p/6195568.html
 * @date 2018/1/11 9:55
 */

public class NettyMessage {
    private Header header;
    private Object object;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
