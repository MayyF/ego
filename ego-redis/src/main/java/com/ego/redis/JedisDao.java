package com.ego.redis;

/**
 * @Auther:S
 * @Date:19/8/2
 */
public interface JedisDao {

    Boolean exists(String key);

    Long del(String key);

    String set(String key, String vaule);

    String get(String key);

    Long expire(String key, int seconds);
}
