package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.TeachExample;
import com.ladybird.hkd.model.example.TeacherExample;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.model.vo.Teaching;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
@Component
public interface TeacherMapper {
    TeacherJsonOut validNumPwd(Teacher teacher) throws Exception;

    List<Teach> checkOutTeaches(@Param("teacher") String t_num) throws Exception;

    //查找某一年级某老师教得某个专业的班级
    String selGradesByDeptCourse(@Param("t_num") String t_num,@Param("dept_num") String dept_num,@Param("c_id") String c_id,@Param("g_year") String year) throws Exception;

    List<String> selGradesByCourse(@Param("t_num") String t_num,@Param("c_id") String c_id) throws Exception;


    List<Course> checkOutCourseByNum(@Param("t_num") String t_num) throws Exception;

    /**
     * 条件查询授信息
     *@param
     *@return  List<TeachExample>
     *Date: 2019/4/14
     */
    List<TeachExample> checkOutTeachesExm(@Param("teacher") String teacher
            , @Param("course") String course, @Param("dept") String dept) throws Exception;

    /**
     * 查询教师信息
     *@param
     *@return:
     *Date: 2019/4/14
     */
    Teacher selTeacherByNum(@Param("t_num") String t_num) throws Exception;

    /**
     * 添加授课
     *@param
     *@return:
     *Date: 2019/4/14
     */
    Integer checkInTeach(Teach t) throws Exception;

    /**
     * 批量添加授课
     *@param
     *@return:
     *Date: 2019/4/15
     */
    Integer checkInTeaches(@Param("list") List<Teach> list) throws Exception;

    /**
     * 通过id查找授课记录
     *@param
     *@return:
     *Date: 2019/4/14
     */
    TeachExample checkOutById(@Param("teach_id") String teach_id) throws Exception;

    /**
     * 修改授课班级
     *@param
     *@return:
     *Date: 2019/4/14
     */
    Integer changeTeachGrades(@Param("teach_id") String teach_id, @Param("grades") String grades) throws Exception;

    /**
     * 修改授课信息
     *@param
     *@return:
     *Date: 2019/4/17
     */
    Integer updateTeach(Teach teach) throws Exception;

    /**
     * 通过id查找是否存在授课记录
     *@param
     *@return:
     *Date: 2019/4/14
     */
    Integer teachIsExist(@Param("teach_id") String teach_id) throws Exception;

    /**
     * 通过id删除授课记录
     *@param
     *@return:
     *Date: 2019/4/14
     */
    Integer delTeachById(@Param("teach_id") String teach_id) throws Exception;



    List<TeacherExample> selectTeacher(@Param("teacher") Teacher teacher, @Param("fy") String faculty, @Param("startNum") int startNum, @Param("pageSize") int pageSize) throws Exception;

    int addTeacher(Teacher teacher) throws Exception;

    int updateTeacher(Teacher teacher) throws Exception;

    int deleteTeacher(@Param("t_num") String t_num) throws Exception;

    List<Teaching> selectTeaching(@Param("t_num") String t_num) throws Exception;

    /**
     * 批量添加教师信息
     *@param
     *@return:
     *Date: 2019/4/16
     */
    Integer checkInTeachers(@Param("teachers") List<Teacher> teachers) throws Exception;
    int selectTeacherCount() throws Exception;

}
