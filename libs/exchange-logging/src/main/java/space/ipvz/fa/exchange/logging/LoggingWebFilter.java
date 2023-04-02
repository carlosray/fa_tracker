package space.ipvz.fa.exchange.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerWebExchange exchangeToProceedWith = exchange;
        if (log.isDebugEnabled() && !exchange.getRequest().getURI().getPath().contains("health")) {
            exchangeToProceedWith = new LoggingWebExchange(log, exchange);
        }
        return chain.filter(exchangeToProceedWith);
    }
}