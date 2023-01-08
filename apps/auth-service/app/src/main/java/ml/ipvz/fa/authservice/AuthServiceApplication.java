package ml.ipvz.fa.authservice;

import ml.ipvz.fa.error.handling.handler.BaseApiExceptionHandler;
import ml.ipvz.fa.exchange.logging.LoggingWebFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({LoggingWebFilter.class, BaseApiExceptionHandler.class})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
