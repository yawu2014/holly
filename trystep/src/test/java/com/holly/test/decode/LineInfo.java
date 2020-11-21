package com.holly.test.decode;

import lombok.*;

@Data
public class LineInfo {
    //行号
    private int line;
    //内容
    private String content;
    //跳出循环标识
    private boolean out;
    //跳入循环标识
    private boolean in;
    //是否是最后一句标识
    private boolean last;

    public LineInfo(int line, String content) {
        this.line = line;
        this.content = content;
    }
}