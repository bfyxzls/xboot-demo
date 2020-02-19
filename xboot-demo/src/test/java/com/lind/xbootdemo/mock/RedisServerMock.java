package com.lind.xbootdemo.mock;

import com.lind.xbootdemo.utils.RandomUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * redis mock.
 */
@ActiveProfiles("test")
@Component
public class RedisServerMock {

    private RedisServer redisServer;
    private String redisHost = "localhost";
    private int redisPort = RandomUtils.getRandomInt(58000, 60000);

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(redisHost, redisPort);
        RedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return factory;
    }

    @PostConstruct
    public void startRedis() {
        try {
            redisServer = RedisServer.builder()
                    .setting("maxheap 200m") //限制内存，否则内存低时将启动失败
                    .port(redisPort)
                    .setting("bind localhost")
                    .build();
            redisServer.start();
        } catch (Exception ex) {
            ex.getStackTrace();
        }

    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
