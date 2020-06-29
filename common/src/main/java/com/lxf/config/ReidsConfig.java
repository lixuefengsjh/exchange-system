//package com.lxf.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @author: lxf
// * @create: 2020-04-19 09:45
// * @description:
// */
////@Configuration
//public class ReidsConfig {
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.database:0}")
//    private int database;
//
//    @Value("${spring.redis.password:null}")
//    private String password;
//
//    @Bean(name="stringRedisTemplate")
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(factory);
//        return template;
//    }
//
//    @Bean(name = "jedisPool")
//    public JedisPool redisPoolFactory(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(8);
//        jedisPoolConfig.setMaxWaitMillis(-1);
//        jedisPoolConfig.setMaxTotal(200);
//        jedisPoolConfig.setMinIdle(0);
//        if (password==null) {
//            return  new JedisPool(jedisPoolConfig, host, port, 0, password, database);
//        } else {
//            return new JedisPool(jedisPoolConfig, host, port, 0, null, database);
//        }
//    }
//}
