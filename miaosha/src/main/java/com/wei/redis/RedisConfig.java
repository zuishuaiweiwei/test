package com.wei.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 为为
 * @create 11/23
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    /**
     * 地址
     */
    private String host;
    private String password;
    private int port;
    /**
     * 连接超时
     */
    private int timeout;
    /**
     * 最大连接数
     */
    private int poolMaxTotal;
    /**
     * 允许最大空闲数
     */
    private int poolMaxIdle;
    /**
     * 最大等待时间
     */
    private int poolMaxWait;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }
}
