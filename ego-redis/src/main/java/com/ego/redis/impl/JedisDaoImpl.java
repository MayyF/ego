package com.ego.redis.impl;

import com.ego.redis.JedisDao;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @Auther:S
 * @Date:19/8/2
 */
@Repository
public class JedisDaoImpl implements JedisDao {

    @Resource
    private JedisCluster jedisClients;

    @Override
    public Boolean exists(String key) {
        return jedisClients.exists(key);
    }

    @Override
    public Long del(String key) {
        return jedisClients.del(key);
    }

    @Override
    public String set(String key, String vaule) {
        return jedisClients.set(key,vaule);
    }

    @Override
    public String get(String key) {
        return jedisClients.get(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisClients.expire(key,seconds);
    }
}
