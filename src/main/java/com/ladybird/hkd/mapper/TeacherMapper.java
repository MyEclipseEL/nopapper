package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
@Component
public interface TeacherMapper {
    TeacherJsonOut validNumPwd(Teacher teacher) throws Exception;

    Teach checkOutTeaches(@Param("teacher") String t_num) throws Exception;
}
