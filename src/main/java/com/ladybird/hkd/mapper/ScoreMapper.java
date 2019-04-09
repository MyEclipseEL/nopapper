package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.ScoreExample;
import com.ladybird.hkd.model.pojo.Score;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/11.
 */
@Component
public interface ScoreMapper {

    void checkInScore(Score score) throws Exception;

    List<ScoreExample> checkOutScores(@Param("t_num") String t_num, @Param("course") String course,
                                      @Param("faculty") String faculty,@Param("dept") String dept,
                                      @Param("year") String year,@Param("clazz") String clazz) throws Exception;
}
