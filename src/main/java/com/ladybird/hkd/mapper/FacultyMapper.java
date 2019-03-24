package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Faculty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface FacultyMapper {
    int addFaculty(Faculty faculty);

    int delFaculty(@Param("fac_num") String fac_num);

    int updateFaculty(Faculty faculty);

    Faculty findFaculty(Faculty faculty);

    Faculty findFacultyByName(String fac_name);

    Faculty findFacultyByNum(String fac_num);
}
