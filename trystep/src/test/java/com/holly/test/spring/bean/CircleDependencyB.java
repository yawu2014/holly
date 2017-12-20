package com.holly.test.spring.bean;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/19 11:50
 */

public class CircleDependencyB {
    CircleDependencyC cdc;

    public CircleDependencyB(CircleDependencyC cdc){
        this.cdc = cdc;
    }
    public CircleDependencyB(){

    }

    public CircleDependencyC getCdc() {
        return cdc;
    }

    public void setCdc(CircleDependencyC cdc) {
        this.cdc = cdc;
    }
}
