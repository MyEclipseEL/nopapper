package com.ladybird.hkd.service;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;

import java.util.List;


public interface BasicService {

    List<Grade> gradesNotInExam(String t_num, String course) throws Exception;
}
