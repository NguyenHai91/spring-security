package com.hainguyen.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;



public interface BaseRedisService {

    void set(String key, String value);

    void setTimeToLive(String key, long timeoutInDay);

    void hashSet(String key, String field, Object value);

    boolean hashExists(String key, String field);

    Object get(String key);

    Map<String, Object> getField(String key);

    Object hashGet(String key, String field);

    List<Object> hashGetByFieldPrefix(String key, String filePrefix);

    Set<String>getFieldPrefixes(String key);

    void delete(String key);

    void delete(String key, String field);
    
    void delete(String key, List<String> field);
}