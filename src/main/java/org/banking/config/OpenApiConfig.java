/*
 * Author: Shady Ahmed
 * Date: 2025-09-27
 * Project: Mobile Banking API
 * My Linked-in: https://www.linkedin.com/in/shady-ahmed97/.
*/
package org.banking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info apiInfo = new Info()
                .title("Mobile Banking API by Shady Ahmed")
                .description("Comprehensive REST API for mobile banking: CRUD, transactions, account management.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Banking API Team")
                        .email("shadyahmed971997@gmail.com")
                        .url("https://github.com/Shady1997/-Mobile_Bank_API_RestFul.git"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));

        Server localServer = new Server()
                .url("http://localhost:8083")
                .description("Local Development Server");

        return new OpenAPI()
                .info(apiInfo)
                .servers(List.of(localServer));
    }
}
