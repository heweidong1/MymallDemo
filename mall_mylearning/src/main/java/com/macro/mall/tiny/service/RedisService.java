package com.macro.mall.tiny.service;

/**
 * redis操作Service
 * 对象和数组都以json形式进行存储
 */
public interface RedisService
{
    /**
     * 存储数据
     */
    void set(String key,String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置超时时间
     */
    Boolean expire(String key,Long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     */
    Long increment(String key,long delta);
}
