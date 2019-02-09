package com.wei.redis;
import com.wei.redis.KeyPrefix;
import com.wei.redis.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;



        import com.alibaba.fastjson.JSON;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.stereotype.Service;
        import redis.clients.jedis.Jedis;
        import redis.clients.jedis.JedisPool;
        import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 为为
 * @create 11/23
 */
@Service
public class RedisService {

    @Autowired
    RedisConfig redisConfig;
    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String val = jedis.get(realKey);
            if (val != null) {
                T bean = stringToBean(val, clazz);
                return bean;
            }
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = beanToString(value);
            if (prefix.expireSeconds() <= 0) {

                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, prefix.expireSeconds(), str);
            }
        } finally {
            returnToPool(jedis);
        }
        return true;
    }

    public boolean del(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            Long ret = jedis.del(realKey);
            return ret > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return value + "";
        } else if (clazz == long.class || clazz == Long.class) {
            return value + "";
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    public <T> T stringToBean(String val, Class<T> clazz) {
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(val);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(val);
        } else if (clazz == String.class) {
            return (T) val;
        } else {
            return JSON.toJavaObject(JSON.parseObject(val), clazz);
        }

    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);

        jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost()
                , redisConfig.getPort(), redisConfig.getTimeout() * 1000,
                redisConfig.getPassword());
        return jedisPool;
    }

}
