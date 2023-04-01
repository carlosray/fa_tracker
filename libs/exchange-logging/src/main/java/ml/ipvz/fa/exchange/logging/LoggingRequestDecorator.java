package ml.ipvz.fa.exchange.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import ml.ipvz.fa.exchange.logging.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class LoggingRequestDecorator extends ServerHttpRequestDecorator {
    private final Flux<DataBuffer> body;

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }

    public LoggingRequestDecorator(Logger log, ServerHttpRequest request) {
        super(request);
        if (log.isDebugEnabled()) {
            body = super.getBody().publishOn(Schedulers.boundedElastic()).doOnNext(buffer -> {
                try (ByteArrayOutputStream bodyStream = new ByteArrayOutputStream()) {
                    Channels.newChannel(bodyStream).write(buffer.toByteBuffer().asReadOnlyBuffer());
                    log.debug(LogUtils.getExchangeLogMessage(
                            LogUtils.LogExchangeType.HTTP_REQUEST,
                            request.getMethod(),
                            request.getURI(),
                            request.getHeaders(),
                            bodyStream.size() > 0 ? bodyStream.toString() : ""
                    ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            body = super.getBody();
        }
    }

}
