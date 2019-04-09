package com.ladybird.hkd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Shen
 * @description: 使用redisTemplate的奥做实现类
 * @create: 2019-03-19
 */
@Component
public class RedisOperator {

    @Autowired
    private StringRedisTemplate redisTemplate ;

    /**
     * 实现命令：TTL key， 以秒为单位，返回给定 key的剩余生存时间
     *@param key String
     *@return long
     *Date: 2019/3/7
     */
    public long ttl(String key){return redisTemplate.getExpire(key);}

    /**
     * 实现命令：设置过期时间，单位秒
     *@param key String
     *@param timeout long
     *Date: 2019/3/7
     */
    public void expire(String key,long timeout){redisTemplate.expire(key,timeout, TimeUnit.SECONDS);}


    /**
     * 实现命令：KEYS，增加key一次
     *@param key String
     *@param delta long
     *@return long
     *Date: 2019/3/7
     */
    public long incr(String key,long delta){return redisTemplate.opsForValue().increment(key,delta);}

    /**
     * KYEYS pattern，查找所有符合给定模式 pattern的key
     *@param pattern String
     *@return Set<String>
     *Date: 2019/3/7
     */
    public Set<String> keys(String pattern){return redisTemplate.keys(pattern);}

    /**
     * 实现命令：DEL key，删除一个key
     *@param key String
     *Date: 2019/3/7
     */
    public void del(String key){redisTemplate.delete(key);}

    /**
     * 实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
     *@param key String
     *@param value String
     *Date: 2019/3/7
     */
    public void set(String key,String value){redisTemplate.opsForValue().set(key,value);}

    /**
     * 实现命令：SET key value EX seconds，设置key-value和设置超时时间（秒）
     *@param key String
     *@param value String
     *@param timeout long
     *
     *Date: 2019/3/7
     */
    public void set(String key,Object value,long timeout){
        redisTemplate.opsForValue().set(key,JsonUtil.objectToJson(value),timeout, TimeUnit.SECONDS);
    }

    /**
     *实现命令：GET key，返回 key所关联的字符串值。
     *@param  key String
     *@return String
     *Date: 2019/3/7
     */
    public String get(String key) {
        return (String)redisTemplate.opsForValue().get(key);
    }

    /**
     * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
     *@param key String
     *@param field String
     *Date: 2019/3/7
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
     *@param key String
     *@param field String
     *@return String
     *Date: 2019/3/7
     */
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     *实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *@param key String
     *@param fields Object[]
     *Date: 2019/3/7
     */
    public void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
     *@param key String
     *@return Map<Object,Object>
     *Date: 2019/3/7
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
     *@param key String
     *@param value String
     *@return long 执行 LPUSH命令后，列表的长度。
     *Date: 2019/3/7
     */
    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     *实现命令：LPOP key，移除并返回列表 key的头元素。
     *@param key String
     *@return String 列表key的头元素。
     *Date: 2019/3/7
     */
    public String lpop(String key) {
        return (String)redisTemplate.opsForList().leftPop(key);
    }

    /**
     *实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
     *@param key String
     *@param value String
     *@return long 执行 LPUSH命令后，列表的长度。
     *Date: 2019/3/7
     */
    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

}