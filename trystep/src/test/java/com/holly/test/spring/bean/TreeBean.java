package com.holly.test.spring.bean;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/20 18:14
 */

public class TreeBean {
    public TreeBean(String data){
        this(null,null,data);
    }

    public TreeBean(TreeBean left, TreeBean right, String data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    TreeBean left;
    TreeBean right;
    String data;

    public TreeBean getLeft() {
        return left;
    }

    public void setLeft(TreeBean left) {
        this.left = left;
    }

    public TreeBean getRight() {
        return right;
    }

    public void setRight(TreeBean right) {
        this.right = right;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
