package com.holly.test.spring.bean;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/19 11:50
 */

public class CircleDependencyC {
    CircleDependencyA cda;
    public CircleDependencyC(){

    }
    public CircleDependencyC(CircleDependencyA cda){
        this.cda = cda;
    }

    public CircleDependencyA getCda() {
        return cda;
    }

    public void setCda(CircleDependencyA cda) {
        this.cda = cda;
        throw new RuntimeException("xxxx");
    }
}
