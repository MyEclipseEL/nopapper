package com.ladybird.hkd.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Shen
 * @description: 3des加密解密（base64）
 * @create: 2019-03-20
 */
@Component
public class DesUtils {
    //向量
    private final static String iv = "01234567";
    //加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *@param   plainText String 内容
     *@param  key String 密钥不小于24
     *@return String
     *@exception Exception
     *Date: 2019/3/7
     */
    public static String encode(String plainText,String key) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "DESede" );
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance( "DESede/CBC/PKCS5Padding" );
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte [] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.getEncoder().encodeToString(encryptData);
    }

    /**
     * 3des解密
     *@param  encryptText String 内容
     *@param key String 密钥不小于24
     *@return String
     *@exception  Exception
     *Date: 2019/3/7
     */
    public static String decode(String encryptText, String key) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "DESede" );
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance( "DESede/CBC/PKCS5Padding" );
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte [] decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText));

        return new String(decryptData, encoding);
    }

    public static String md5(String value){
        String result = null;
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
            md5.update((value).getBytes("UTF-8"));
        }catch (NoSuchAlgorithmException error){
            error.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        byte b[] = md5.digest();
        int i;
        StringBuffer buf = new StringBuffer("");

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        result = buf.toString();
        return result;
    }

    public static String GeneratePasswordHash(String password) throws Exception{
        String method="Jandy:MD532";
        String encPwd= DesUtils.encode(password, ConstConfig.SECRET_KEY);
        String md5Str=md5(method+"$"+encPwd+"$");
        return method+"$"+encPwd+"$"+md5Str;
    }

    public static boolean CheckPasswordHash(String pwdHash,String password) throws Exception{
        String[] aa=pwdHash.split("\\$");
        if (aa.length!=3)
            return false;
        String encPwd=encode(password,ConstConfig.SECRET_KEY);
        if (!aa[1].equals(encPwd))
            return false;
        return true;

    }
}
