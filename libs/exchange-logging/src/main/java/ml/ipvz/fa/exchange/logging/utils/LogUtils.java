package ml.ipvz.fa.exchange.logging.utils;

import java.net.URI;
import java.util.List;
import java.util.Set;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class LogUtils {
    private static final Set<String> FORBIDDEN_BODY_WORDS = Set.of(
            "password",
            "accessToken",
            "refreshToken"
    );
    private static final Set<String> FORBIDDEN_HEADERS = Set.of(
            HttpHeaders.AUTHORIZATION
    );

    private LogUtils() {
    }

    public enum LogExchangeType {
        HTTP_REQUEST, HTTP_RESPONSE
    }

    @SneakyThrows
    public static String getExchangeLogMessage(LogExchangeType type,
                                               HttpMethod method,
                                               URI uri,
                                               HttpHeaders headers,
                                               Object body) {
        HttpHeaders headersToLog = HttpHeaders.writableHttpHeaders(headers);
        for (String header : FORBIDDEN_HEADERS) {
            headersToLog.replace(header, List.of("*****"));
        }
        return "%s: %s %s\nHeaders: %s\nBody: %s".formatted(
                type,
                method,
                uri.getPath() + (org.springframework.util.StringUtils.hasText(uri.getQuery()) ? "?$query" : ""),
                headers,
                cleanUpBodyString(body.toString())
        );
    }

    private static String cleanUpBodyString(String body) {
        String bodyToLog = StringUtils.normalizeSpace(StringUtils.remove(body.toString(), System.lineSeparator()));

        for (String word : FORBIDDEN_BODY_WORDS) {
            if (bodyToLog.contains(word)) {
                String pattern = "(\"%s\" *: *\")(.*?)(\")".formatted(word);
                bodyToLog = bodyToLog.replaceAll(pattern, "$1*****$3");
            }
        }

        return bodyToLog;
    }
}
