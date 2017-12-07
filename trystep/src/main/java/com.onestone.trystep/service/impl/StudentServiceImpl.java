package com.onestone.trystep.service.impl;

import com.holly.bean.Student;
import com.onestone.trystep.dao.StudentDao;
import com.onestone.trystep.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 15:20
 */
@Service
public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;
    public boolean insert(Student student) {
        return studentDao.insert(student)>0?true:false;
    }
}
