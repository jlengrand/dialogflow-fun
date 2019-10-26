package fr.lengrand.dialogflowfunapi.openbankproject.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.charset.Charset;
import java.util.function.Function;

public class JSONBodyHandler<T> implements HttpResponse.BodyHandler<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final Class<T> type;
    private HttpResponse.BodySubscriber<String> subscriber;

    public static <T> JSONBodyHandler<T>  getHandler(final Class<T> type){
        return new JSONBodyHandler<>(type);
    }

    private JSONBodyHandler(Class<T> type){
        this.type = type;
        this.subscriber = BodySubscribers.ofString(Charset.defaultCharset());
    }

    @Override
    public BodySubscriber<T> apply(ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(subscriber, stringToJSON());
    }

    private Function<String, T> stringToJSON() {
        return byteArray -> {
            try {
                return objectMapper.readValue(byteArray, this.type);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
}

