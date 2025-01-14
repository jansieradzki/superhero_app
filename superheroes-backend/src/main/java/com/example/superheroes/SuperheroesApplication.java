package com.example.superheroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

@SpringBootApplication
@Slf4j
@OpenAPIDefinition(
        info = @Info(
                title = "Superheroes Battle API",
                version = "1.0",
                description = "API for managing battles between superheroes and supervillains",
                contact = @Contact(name = "Support Team", email = "support@superheroes.com")
        )
)
public class SuperheroesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SuperheroesApplication.class, args);
        log.info("SuperheroesApplication has started successfully.");
    }
}