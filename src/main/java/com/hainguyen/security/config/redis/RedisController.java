package com.hainguyen.security.config.redis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hainguyen.security.config.redis.baseRedis.BaseRedisService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


@Tag(name = "Redis Controller")
@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {

    @Autowired
    private BaseRedisService redisService;

    @PostMapping("/set")
    public void set(String key, String value) {
        redisService.set(key, value);
    }
}
