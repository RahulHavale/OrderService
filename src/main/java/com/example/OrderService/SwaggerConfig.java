package com.example.OrderService;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce Microservices API")
                        .description("REST APIs for E-Commerce Microservices")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Rahul Havale")
                                .email("rahul@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8086")
                                .description("API Gateway")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://springdoc.org"));
    }
}
