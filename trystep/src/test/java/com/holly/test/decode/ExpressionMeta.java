package com.holly.test.decode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpressionMeta extends LineInfo{
    private boolean labelExpress;
    private boolean gotoExpress;
    private boolean commonExpress;
    private boolean ifExpress;
    private boolean isEnd;
    public ExpressionMeta(int line, String content){
        super(line, content);
    }
}
