package ml.ipvz.fa.exchange.logging;

import org.slf4j.Logger;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public class LoggingWebExchange extends ServerWebExchangeDecorator {
    private final LoggingRequestDecorator requestDecorator;
    private final LoggingResponseDecorator responseDecorator;

    protected LoggingWebExchange(Logger log, ServerWebExchange delegate) {
        super(delegate);
        this.requestDecorator = new LoggingRequestDecorator(log, delegate.getRequest());
        this.responseDecorator = new LoggingResponseDecorator(log, delegate.getRequest(), delegate.getResponse());
    }

    @Override
    public ServerHttpRequest getRequest() {
        return requestDecorator;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return responseDecorator;
    }
}
