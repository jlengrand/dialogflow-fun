package fr.lengrand.dialogflowfunapi.openbankproject;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.JSONBodyHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenBankHandler {

    @Autowired
    private Auth auth;

    public static final String BASE_URL = "https://psd2-api.openbankproject.com/obp/v4.0.0/";

    public <T> T post(JSONBodyHandler<T> jsonBodyHandler, String relativeUrl, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/json",
                        "Authorization", createAuthHeader())
                .uri(URI.create(BASE_URL + relativeUrl))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<T> response = HttpClient.newHttpClient().send(request, jsonBodyHandler);
        return response.body();
    }

    public <T> T get(JSONBodyHandler<T> jsonBodyHandler, String relativeUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/json",
                        "Authorization", createAuthHeader())
                .uri(URI.create(BASE_URL + relativeUrl))
                .build();

        HttpResponse<T> response = HttpClient.newHttpClient().send(request, jsonBodyHandler);
        return response.body();
    }

    private String createAuthHeader(){
        return "DirectLogin token=" + this.auth.getToken().get();
    }

}
