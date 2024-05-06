package br.com.notification.schedule.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("schedule-api")
                .packagesToScan("br.com.notification.schedule.api.resource")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Schedule API")
                        .version("0.1.0")
                        .description("Schedule API")
                        .termsOfService("Termo de uso: Deve ser usada para testes.")
                        .license(new License()
                                .name("Licença - Open Source"))
                        .contact(contato()))
                .components(new Components()
                        .addSecuritySchemes("basicAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }

    private io.swagger.v3.oas.models.info.Contact contato() {
        return new io.swagger.v3.oas.models.info.Contact()
                .name("Schedule");
    }
}
