package br.com.toplibrary.infra.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Top Library API")
                    .description("API Rest de um gerenciamento de biblioteca, onde possui um usuário que faça aluguel dos livros cadastrados na Top Library.")
                    .contact(new Contact()
                            .name("Time Backend")
                            .email("top.library@gmail.com"))
                    .license(new License()
                            .name("Apache 2.0")
                            .url("http://toplibrary.com/api/licenca")));
    }

}
