package com.iotechn.unimall.data.config.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@Configuration
public class RedisAutoConfig {

    /**** 缓存专用数据源 ****/
    @Bean
    public LettuceConnectionFactory defaultLettuceConnectionFactory(
            RedisStandaloneConfiguration defaultRedisConfig,GenericObjectPoolConfig defaultPoolConfig) {
        LettuceClientConfiguration clientConfig =
                LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(5000))
                        .poolConfig(defaultPoolConfig).build();
        return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(
            LettuceConnectionFactory defaultLettuceConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(defaultLettuceConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory defaultLettuceConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(defaultLettuceConnectionFactory);
        return stringRedisTemplate;
    }

    /**** 用户SESSION专用数据源 ****/
    @Bean
    public LettuceConnectionFactory userLettuceConnectionFactory(
            RedisStandaloneConfiguration userRedisConfig,GenericObjectPoolConfig userPoolConfig) {
        LettuceClientConfiguration clientConfig =
                LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(5000))
                        .poolConfig(userPoolConfig).build();
        return new LettuceConnectionFactory(userRedisConfig, clientConfig);
    }


    @Bean
    public StringRedisTemplate userRedisTemplate(LettuceConnectionFactory userLettuceConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(userLettuceConnectionFactory);
        return redisTemplate;
    }



    /**** 锁专用数据源 ****/
    @Bean
    public LettuceConnectionFactory lockLettuceConnectionFactory(
            RedisStandaloneConfiguration lockRedisConfig,GenericObjectPoolConfig lockPoolConfig) {
        LettuceClientConfiguration clientConfig =
                LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(5000))
                        .poolConfig(lockPoolConfig).build();
        return new LettuceConnectionFactory(lockRedisConfig, clientConfig);
    }

    @Bean
    public StringRedisTemplate lockRedisTemplate(LettuceConnectionFactory lockLettuceConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(lockLettuceConnectionFactory);
        return redisTemplate;
    }


    @Configuration
    public static class UserRedisConfig {
        @Value("${spring.user-redis.host:127.0.0.1}")
        private String host;
        @Value("${spring.user-redis.port:6379}")
        private Integer port;
        @Value("${spring.user-redis.password:}")
        private String password;
        @Value("${spring.user-redis.database:0}")
        private Integer database;

        @Value("${spring.user-redis.lettuce.pool.max-active:8}")
        private Integer maxActive;
        @Value("${spring.user-redis.lettuce.pool.max-idle:8}")
        private Integer maxIdle;
        @Value("${spring.user-redis.lettuce.pool.max-wait:-1}")
        private Long maxWait;
        @Value("${spring.user-redis.lettuce.pool.min-idle:0}")
        private Integer minIdle;

        @Bean
        public GenericObjectPoolConfig userPoolConfig() {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(maxActive);
            config.setMaxIdle(maxIdle);
            config.setMinIdle(minIdle);
            config.setMaxWaitMillis(maxWait);
            return config;
        }

        @Bean
        public RedisStandaloneConfiguration userRedisConfig() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPassword(RedisPassword.of(password));
            config.setPort(port);
            config.setDatabase(database);
            return config;
        }
    }

    @Configuration
    public static class LockRedisConfig {
        @Value("${spring.lock-redis.host:127.0.0.1}")
        private String host;
        @Value("${spring.lock-redis.port:6379}")
        private Integer port;
        @Value("${spring.lock-redis.password:}")
        private String password;
        @Value("${spring.lock-redis.database:0}")
        private Integer database;

        @Value("${spring.lock-redis.lettuce.pool.max-active:8}")
        private Integer maxActive;
        @Value("${spring.lock-redis.lettuce.pool.max-idle:8}")
        private Integer maxIdle;
        @Value("${spring.lock-redis.lettuce.pool.max-wait:-1}")
        private Long maxWait;
        @Value("${spring.lock-redis.lettuce.pool.min-idle:0}")
        private Integer minIdle;

        @Bean
        public GenericObjectPoolConfig lockPoolConfig() {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(maxActive);
            config.setMaxIdle(maxIdle);
            config.setMinIdle(minIdle);
            config.setMaxWaitMillis(maxWait);
            return config;
        }

        @Bean
        public RedisStandaloneConfiguration lockRedisConfig() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPassword(RedisPassword.of(password));
            config.setPort(port);
            config.setDatabase(database);
            return config;
        }
    }


    @Configuration
    public static class DefaultRedisConfig {
        @Value("${spring.redis.host:127.0.0.1}")
        private String host;
        @Value("${spring.redis.port:6379}")
        private Integer port;
        @Value("${spring.redis.password:}")
        private String password;
        @Value("${spring.redis.database:0}")
        private Integer database;

        @Value("${spring.redis.lettuce.pool.max-active:8}")
        private Integer maxActive;
        @Value("${spring.redis.lettuce.pool.max-idle:8}")
        private Integer maxIdle;
        @Value("${spring.redis.lettuce.pool.max-wait:-1}")
        private Long maxWait;
        @Value("${spring.redis.lettuce.pool.min-idle:0}")
        private Integer minIdle;

        @Bean
        public GenericObjectPoolConfig defaultPoolConfig() {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(maxActive);
            config.setMaxIdle(maxIdle);
            config.setMinIdle(minIdle);
            config.setMaxWaitMillis(maxWait);
            return config;
        }

        @Bean
        public RedisStandaloneConfiguration defaultRedisConfig() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPassword(RedisPassword.of(password));
            config.setPort(port);
            config.setDatabase(database);
            return config;
        }
    }
}