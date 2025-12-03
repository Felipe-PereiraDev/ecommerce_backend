package br.com.felipedev.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi {

    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Ecommerce-API"));
    }
}
