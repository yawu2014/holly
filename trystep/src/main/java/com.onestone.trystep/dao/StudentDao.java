package com.onestone.trystep.dao;

import com.holly.bean.Student;

import java.util.List;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 15:13
 */

public interface StudentDao {
    Integer insert(Student s);

    List<Student> findAll();

    List<Student> findByStudentIds(List<Integer> studentIds);
}
