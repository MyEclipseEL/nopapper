package com.ladybird.hkd.mapper;

import com.ladybird.hkd.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
@Component
public interface StudentMapper {
    Student findByNumAndPwd(Student student);

    Student findByNum(@Param("stu_num") String stuNum);
}
