package com.cryptoexchange.cryptoexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


// Configurações para a documentação Swagger
@Configuration
public class SwaggerConfig {
    
    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.cryptoexchange.cryptoexchange"))
                    .build()
                    .apiInfo(info());

    }

    private ApiInfo info() {
        return new ApiInfoBuilder()
                    .title("API de Cotação de Criptomoedas")
                    .description("API para transação de criptomoedas com o valor atualizado da criptomoeda")
                    .version("1.0.0")
                    .license("Apache License Version 2.0")
                    .contact(new Contact("Lucas Garcez", "https://github.com/garcezlucas", "garcezlucas.silva@gmail.com"))
                    .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                    .build();
    }

}



