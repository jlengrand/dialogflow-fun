package fr.lengrand.dialogflowfunapi.openbankproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Configuration
@ComponentScan
public class Auth {

    @Autowired
    private OpenBankCredentials credentials;

    public static final String OBP_BASE_URL = "https://psd2-api.openbankproject.com";
    public static final String  LOGIN_URL = "/my/logins/direct";

    public JSONBodyHandler<AuthToken> jsonBodyHandler = JSONBodyHandler.getHandler(AuthToken.class);

    private Optional<AuthToken> authToken = Optional.empty();

    public void authenticate() throws IOException, InterruptedException {
        System.out.println("Username is " + credentials.getUsername());
        System.out.println("Password is " + credentials.getPassword());
        System.out.println("Key is " + credentials.getKey());


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/json",
                "Authorization", createDirectLoginHeader())
                .uri(URI.create(OBP_BASE_URL + LOGIN_URL)) // TODO : URL creation improve
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<AuthToken> response = client.send(request, jsonBodyHandler); //TODO  : Handle failures.
        System.out.println(response.body().getToken());
        authToken = Optional.of(response.body());
    }

    public boolean isAuthenticated() {
        return authToken.isPresent();
    }

    public Optional<AuthToken> getAuthToken() {
        return authToken;
    }

    private String createDirectLoginHeader(){
        return "DirectLogin username=" + this.credentials.getUsername()  + ",password="+ this.credentials.getPassword() + ",consumer_key="+ this.credentials.getKey();
    }

}
