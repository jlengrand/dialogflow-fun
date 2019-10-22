package fr.lengrand.dialogflowfunapi.openbankproject.auth;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.ByteArrayInputStream;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.util.function.Function;

public class JSONBodyHandler<T> implements HttpResponse.BodyHandler<T> {
    // TODO : Use Jackson
    private final Jsonb jsonBinder;
    private final Class<T> type;
    private HttpResponse.BodySubscriber<byte[]> byteArraySubscriber;

    public static <T> JSONBodyHandler<T>  getHandler(final Class<T> type){
        return new JSONBodyHandler<>(JsonbBuilder.create(), type);
    }

    private JSONBodyHandler(Jsonb jsonBinder, Class<T> type){
        this.jsonBinder = jsonBinder;
        this.type = type;
        this.byteArraySubscriber = BodySubscribers.ofByteArray();
    }

    @Override
    public BodySubscriber<T> apply(ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(byteArraySubscriber, byteArrayToJSON());
    }

    private Function<byte[], T> byteArrayToJSON() {
        return byteArray -> this.jsonBinder.fromJson(new ByteArrayInputStream(byteArray), this.type);
    }


}

