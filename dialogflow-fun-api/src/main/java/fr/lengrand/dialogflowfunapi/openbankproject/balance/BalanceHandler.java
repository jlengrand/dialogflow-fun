package fr.lengrand.dialogflowfunapi.openbankproject.balance;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.JSONBodyHandler;
import fr.lengrand.dialogflowfunapi.openbankproject.balance.data.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BalanceHandler {

    @Autowired
    private Auth auth;

    public JSONBodyHandler<Balance> jsonBodyHandler = JSONBodyHandler.getHandler(Balance.class);

    public Balance getBalance() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/json",
                        "Authorization", createAuthHeader())
                .uri(URI.create(createUrl("at02-1465--01"))) // TODO : Convert to DialogFlow names
                .build();

        HttpResponse<Balance> response = client.send(request, jsonBodyHandler); //TODO  : Handle failures.
        return response.body();
    }

    private String createUrl(String bank){
        return "https://psd2-api.openbankproject.com/obp/v4.0.0/banks/" + bank + "/balances";
    }

    private String createAuthHeader(){
        return "DirectLogin token=" + this.auth.getToken().get();
    }

}
