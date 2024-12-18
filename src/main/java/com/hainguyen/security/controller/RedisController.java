package com.hainguyen.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hainguyen.security.service.BaseRedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


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
