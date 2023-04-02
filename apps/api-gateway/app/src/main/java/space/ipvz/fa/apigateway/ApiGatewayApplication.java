package space.ipvz.fa.apigateway;

import space.ipvz.fa.authservice.config.AuthClientConfiguration;
import space.ipvz.fa.error.handling.handler.BaseApiExceptionHandler;
import space.ipvz.fa.exchange.logging.LoggingWebFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BaseApiExceptionHandler.class, LoggingWebFilter.class, AuthClientConfiguration.class})
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
