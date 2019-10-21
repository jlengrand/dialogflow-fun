package fr.lengrand.dialogflowfunapi.openbankproject.transactions;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.JSONBodyHandler;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.data.AuthToken;
import fr.lengrand.dialogflowfunapi.openbankproject.transactions.data.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TransactionsHandler {

    @Autowired
    private Auth auth;

    public JSONBodyHandler<Transactions> jsonBodyHandler = JSONBodyHandler.getHandler(Transactions.class);

    public Transactions getTransactions() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/json",
                        "Authorization", createAuthHeader())
                .uri(URI.create(createUrl("at02-1465--01", "john_doe"))) // TODO : Convert to DialogFlow names
                .build();

        HttpResponse<Transactions> response = client.send(request, jsonBodyHandler); //TODO  : Handle failures.
        return response.body();
    }

    private String createUrl(String bank, String user){
        return "https://psd2-api.openbankproject.com/obp/v4.0.0/my/banks/"+ bank + "/accounts/" + user + "/transactions";
    }

    private String createAuthHeader(){
        return "DirectLogin token=" + this.auth.getToken().get();
    }
}


