package com.ladybird.hkd.manager;

import com.ladybird.hkd.exception.TokenException;

/**
 * Created by 和泉纱雾 on 2019/3/7.
 * 对Token进行操作的接口
 */
public interface TokenManager {

    /**
     *@Description: 创建一个token关联上指定用户
     *@Param: String uid
     *@return: String
     *Date: 2019/3/7
     */
    public String createToken(Object data) throws TokenException;

    /**
     *@Description: 创建一个RefreshToken
     *@Param: String accessToken
     *@return: String
     *Date: 2019/3/7
     */
    public String createReToken(String accessToken) throws TokenException;

    /**
     *@Description: 检查token是否有效
     *@Param: tokenKey
     *@return: Integer
     *Date: 2019/3/7
     */
    public Integer checkToken(String tokenKey) throws TokenException;

    /**
     *@Description: 更新Token
     *@Param: String refreshToken
     *@return: Integer
     *Date: 2019/3/7
     */
    public Integer refreshCheckToken(String refreshToken) throws TokenException;

    /**
     *@Description: 清楚Token
     *@Param: String tokenKey
     *@return: void
     *Date: 2019/3/7
     */
    public void deleteToken(String tokenKey);
}
