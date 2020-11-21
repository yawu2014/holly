package com.holly.test.decode;

import lombok.Data;

@Data
public class ClassMeta {
    private String title;
    private String className;
    private String superClass;
    private DefineMeta defineMeta;
}
