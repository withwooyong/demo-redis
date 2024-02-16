package com.example.demoredis.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YmlUtil {

    private final Environment environment;

    public String getProperty(String name) {
        return environment.getProperty(name);
    }

}