package com.ladybird.hkd.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by 和泉纱雾 on 2019/3/11.
 */
@Component
public interface CourseMapper {

    String findNameById(@Param("c_id") Integer c_id);
}
