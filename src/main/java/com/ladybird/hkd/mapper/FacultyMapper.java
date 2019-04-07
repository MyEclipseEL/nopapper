package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Faculty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FacultyMapper {
    int addFaculty(Faculty faculty) throws Exception;

    Faculty findFaculty(Faculty faculty) throws Exception;

    List<Faculty> selectAllFaculty() throws Exception;

    int updateFaculty(Faculty faculty) throws Exception;

    int selectFacultyByPrimary(@Param("fac_num") String fac_num) throws Exception;
}
