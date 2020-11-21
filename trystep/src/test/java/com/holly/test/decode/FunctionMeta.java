package com.holly.test.decode;

import lombok.Data;

import java.util.List;

@Data
public class FunctionMeta {
    private String title;
    private String name;
    private String args;

    private List<ExpressionMeta> lineInfos;
}
