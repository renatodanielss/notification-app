package br.com.notification.api.gateway.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.Objects;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("notification-api-gateway")
                .packagesToScan("br.com.notification.api.gateway")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notification API")
                        .version("0.1.0")
                        .description("Notification API")
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
                .name("Notification API Gateway");
    }

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder
//                .routes()
//                .route(r -> r.path("/notifications/v3/api-docs").and().method(HttpMethod.GET).uri("lb://notifications"))
//                .route(r -> r.path("/users/v3/api-docs").and().method(HttpMethod.GET).uri("lb://users"))
//    .build();
//    }

    @Bean
    public CommandLineRunner openApiGroups(
            RouteDefinitionLocator locator,
            SwaggerUiConfigParameters swaggerUiParameters) {
        return args -> Objects.requireNonNull(locator
                        .getRouteDefinitions().collectList().block())
                .stream()
                .map(RouteDefinition::getId)
                .filter(id -> id.matches(".*-API"))
                .map(id -> id.replace("-API", ""))
                .forEach(swaggerUiParameters::addGroup);
    }
}