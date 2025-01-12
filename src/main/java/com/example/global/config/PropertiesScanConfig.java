package com.example.global.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = {
        "com.example.global.security.jwt.properties"
})
public class PropertiesScanConfig {
}