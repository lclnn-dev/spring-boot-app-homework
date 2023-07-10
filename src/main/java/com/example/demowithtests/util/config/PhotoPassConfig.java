package com.example.demowithtests.util.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "photopass")
@Getter
@Setter
public class PhotoPassConfig {
    private String storageDir;
}
