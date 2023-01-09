package ml.ipvz.fa.apigateway;

import ml.ipvz.fa.authservice.config.AuthClientConfiguration;
import ml.ipvz.fa.error.handling.handler.BaseApiExceptionHandler;
import ml.ipvz.fa.exchange.logging.LoggingWebFilter;
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
