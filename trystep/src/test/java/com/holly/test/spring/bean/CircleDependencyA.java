package com.holly.test.spring.bean;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/19 11:50
 */

public class CircleDependencyA {


    private CircleDependencyB cdb;

    public CircleDependencyA(CircleDependencyB cdb) {
        this.cdb = cdb;
    }

    public CircleDependencyA() {
    }

    public CircleDependencyB getCdb() {
        return cdb;
    }

    public void setCdb(CircleDependencyB cdb) {
        this.cdb = cdb;
    }
}
