package com.ego.redis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Auther:S
 * @Date:19/8/3
 */
public class Main {
    private String host="192.168.43.56";
    private int port=7001;


    @Test
    public void test2(){
        String eq="http://localhost:8084/user/showLogin?interurl=%snum=%s";
        System.out.println(String.format(eq,"diyi","dier"));
    }

    @Test
    public void test1(){

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        for(int i=0 ;i<6; i++){
            nodes.add(new HostAndPort(host,port+i));
        }
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);
        String name = cluster.get("k1");
        System.out.println(name);
        cluster.set("age", "18");
        System.out.println(cluster.get("age"));
        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
