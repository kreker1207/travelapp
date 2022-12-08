package com.project.trav.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =@Info(
        title = "Travel API",
        version = "V1",
        contact = @Contact(
            name = "Ivan Baranetskyi", email = "baranetckiy@gmail.com", url = ""
        )
    )
)
public class SwaggerConfiguration {
}
