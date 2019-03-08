package com.ladybird.hkd.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
public class JWTUtils {
    public String createJWT(String id, String subject, long ttlMillis, Map<String,Object> claims) throws Exception {
        //指定签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        //下面为payload添加各种标准声明呵私有声明
        JwtBuilder builder = Jwts.builder()     //这里其实是new一个JwtBuilder，设置jwt的body
                //如果由私有声明，一定先设置这个自己穿件的私有声明，这个是给builder的claim赋值，
                // 一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                .setId(id)             //设置jti（JWT
                                                    //ID：是JWT的唯一标识，根据业务需要，这个可以设置为不重复的值，
                                                    //主要用来作为一次性token，从而回避重放攻击。
                .setIssuedAt(now)      //设置签发时间
                .setSubject(subject)    //sub:代表这个JWT的主体，即他的所有人，这个是一个json格式的字符串，可以存放userid之类的
                .signWith(signatureAlgorithm,key);//设置签名使用的签名算法呵签名使用的密钥
        if (ttlMillis >= 0){
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp); //设置过期时间
        }
        return builder.compact();   //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }
    
    /**
     * 解密jwt
     * 
     * @param jwt
     * @return Claims
     * @throws Exception
     * */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();  //签名私钥，和生成的签名的密钥一模一样
        Claims claims = Jwts.parser()   //得到DefaultJwtParser
                .setSigningKey(key) //设置签名私钥
                .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }
    
    /**
     * 由字符串生成加密key
     *
     * @return SecretKey 
     */
    public SecretKey generalKey() {
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        //本地的密码解码
        byte[] encodeKey = Base64.decodeBase64(propertiesUtils.getUrlValue("secret", "secret"));
        //根据给定的字节数组使用AES
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }
}
