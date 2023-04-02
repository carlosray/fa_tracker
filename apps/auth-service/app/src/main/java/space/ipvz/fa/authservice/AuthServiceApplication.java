package space.ipvz.fa.authservice;

import space.ipvz.fa.authservice.config.AuthClientConfiguration;
import space.ipvz.fa.async.config.KafkaConfiguration;
import space.ipvz.fa.error.handling.handler.BaseApiExceptionHandler;
import space.ipvz.fa.exchange.logging.LoggingWebFilter;
import space.ipvz.fa.userservice.config.UserClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(excludeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthClientConfiguration.class)
})
@Import({LoggingWebFilter.class, BaseApiExceptionHandler.class, UserClientConfiguration.class, KafkaConfiguration.class})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
