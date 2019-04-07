package com.ladybird.hkd.service;

import com.ladybird.hkd.model.example.GradeExample;

import java.util.List;


public interface BasicService {

    List<GradeExample> gradesNotInExam(String t_num, String course) throws Exception;
}
