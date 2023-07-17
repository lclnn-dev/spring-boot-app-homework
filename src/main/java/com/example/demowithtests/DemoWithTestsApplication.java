package com.example.demowithtests;

import com.example.demowithtests.util.config.PhotoPassConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoWithTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWithTestsApplication.class, args);
    }
}
