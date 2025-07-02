package com.mongodb.springmongodb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
    public OpenAPI customOpenAPI() {

        Server devServer = new Server();
        devServer.setUrl("https://dev.miapp.com/api");
        devServer.setDescription("Servidor de Desarrollo");

        Server qaServer = new Server();
        qaServer.setUrl("https://qa.miapp.com/api");
        qaServer.setDescription("Servidor de QA");

        Server prodServer = new Server();
        prodServer.setUrl("https://prod.miapp.com/api");
        prodServer.setDescription("Servidor de Producción");

        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio - SpringmongoDB")
                        .version("1.0")
                        .description("API REST para gestión de tareas.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("soporte@miapp.com")
                                .url("https://miapp.com/contacto"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT"))
                        .termsOfService("https://miapp.com/terminos")
                )
                .servers(List.of(devServer, qaServer, prodServer));
    }
}
