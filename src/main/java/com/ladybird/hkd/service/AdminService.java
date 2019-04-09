package com.ladybird.hkd.service;

import com.ladybird.hkd.model.example.AdminExample;

/**
 * Created by 和泉纱雾 on 2019/4/8.
 */
public interface AdminService {

    AdminExample login(String t_num, String t_pwd) throws Exception;
}
