package space.ipvz.fa.exchange.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import space.ipvz.fa.exchange.logging.utils.LogUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class LoggingResponseDecorator extends ServerHttpResponseDecorator {

    private final Logger log;
    private final ServerHttpRequest request;
    private final ServerHttpResponse response;

    public LoggingResponseDecorator(Logger log, ServerHttpRequest request, ServerHttpResponse response) {
        super(response);
        this.log = log;
        this.request = request;
        this.response = response;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return super.writeWith(Flux.from(body).publishOn(Schedulers.boundedElastic()).doOnNext(buffer -> {
            if (log.isDebugEnabled()) {
                try (ByteArrayOutputStream bodyStream = new ByteArrayOutputStream()) {
                    Channels.newChannel(bodyStream).write(buffer.toByteBuffer().asReadOnlyBuffer());
                    log.debug(LogUtils.getExchangeLogMessage(
                            LogUtils.LogExchangeType.HTTP_RESPONSE,
                            request.getMethod(),
                            request.getURI(),
                            response.getHeaders(),
                            bodyStream.size() > 0 ? bodyStream.toString() : ""
                    ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }
}
