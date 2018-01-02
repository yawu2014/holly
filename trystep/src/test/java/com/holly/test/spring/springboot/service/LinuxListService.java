package com.holly.test.spring.springboot.service;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 14:37
 */

public class LinuxListService implements ListService {
    @Override
    public String showListCmd() {
        return "ls";
    }
}
