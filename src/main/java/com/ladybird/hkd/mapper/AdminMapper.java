package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.AdminExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by 和泉纱雾 on 2019/4/8.
 */@Component
public interface AdminMapper {

    AdminExample selByIdPwd(@Param("id") String id,@Param("pwd") String pwd) throws Exception;
}
