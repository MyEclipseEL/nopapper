package com.ladybird.hkd.manager.impl;

import com.ladybird.hkd.exception.TokenException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.DesUtils;
import com.ladybird.hkd.util.JWTUtils;
import com.ladybird.hkd.util.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Shen
 * @description: TokenManager的实现
 * @create: 2019-03-20
 */
@Component
public class RedisTokenManager implements TokenManager {

    @Autowired
    private RedisOperator redis;

    @Autowired
    private DesUtils desUtils;

    @Autowired
    private JWTUtils jwtUtils;

    /**
     *@Description: 创建token
     *@Param: String uid
     *@return: String
     *@Throws: TokenException
     *Date: 2019/3/7
     */
    @Override
    public String createToken(Object data) throws TokenException {
        //使用uuid作为源token一部分
        String preToken = UUID.randomUUID().toString().replace("-", "");

        String key = preToken + "." + DesUtils.md5("access_token");
//        String key = jwtUtils.createJWT()
        //存储到redis并设置过期时间
        redis.set(key, data, ConstConfig.TOKEN_EXPIRES_HOUR*3600);
        return key;
    }

    /**
     *@Description: 创建一个refreshToken
     *@Param: String accessToken
     *@return: String
     *@Throws: TokenException
     *Date: 2019/3/7
     */
    @Override
    public String createReToken(String accessToken) throws TokenException {
        //使用uuid作为源token一部分
        String preToken = UUID.randomUUID().toString().replace("-", "");

        String key = preToken + "." + DesUtils.md5("refresh_token");

        //存储到redis并设置过期时间
        redis.set(key,accessToken,ConstConfig.RETOKEN_EXPIRES_HOUR*3600);
        return key;
    }

    /**
     *@Description: 检查token
     *@Param: String tokenKey
     *@return: Integer
     *@Throws: TokenException
     *Date: 2019/3/7
     */
    @Override
    public String checkToken(String tokenKey) throws TokenException {
        if (tokenKey == null || "".equals(tokenKey.trim())) {
            return null;
        }
        String tokenValue = redis.get(tokenKey);
        if (tokenValue == null || "".equals(tokenValue.trim())) {
            return null;
        }else {
            return tokenValue;
        }
    }

    @Override
    public Integer refreshCheckToken(String refreshToken) throws TokenException {
        String accessToken = redis.get(refreshToken);
        if (accessToken == null || "".equals(accessToken.trim())) {
            return null;
        } else {
            String uid = redis.get(accessToken);
            if (uid == null || "".equals(uid.trim())) {
                return null;
            }
            deleteToken(accessToken);//清除原来的accessToken
            return Integer.parseInt(uid);

        }
    }

    @Override
    public void deleteToken(String tokenKey) {
        redis.del(tokenKey);

    }
}
