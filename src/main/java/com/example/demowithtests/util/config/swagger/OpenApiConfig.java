package com.example.demowithtests.util.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        ApiResponse badRequest = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 400, \"status\" : \"Bad Request\", \"Message\" : \"Bad Request\"}"))));

        ApiResponse successfulResponse = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 200, \"status\" : \"OK\", \"Message\" : \"Successful operation\"}"))));


        Components components = new Components();
        components.addResponses("badRequest",badRequest);
        components.addResponses("successfulResponse",successfulResponse);


        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("Employee CRUD API")
                        .description("Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
                        .version("v0.0.2"));
    }
}

