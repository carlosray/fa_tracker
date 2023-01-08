package ml.ipvz.fa.authservice.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}

