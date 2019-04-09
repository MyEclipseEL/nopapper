package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.AdminMapper;
import com.ladybird.hkd.model.example.AdminExample;
import com.ladybird.hkd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-16
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public AdminExample login(String t_num, String t_pwd) throws Exception {
        return adminMapper.selByIdPwd(t_num, t_pwd);
    }
}