package fr.lengrand.dialogflowfunapi.openbankproject.paymentrequest;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.JSONBodyHandler;
import fr.lengrand.dialogflowfunapi.openbankproject.paymentrequest.data.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.transactions.data.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PaymentRequestHandler {

    @Autowired
    private Auth auth;

    public JSONBodyHandler<PaymentRequest> jsonBodyHandler = JSONBodyHandler.getHandler(PaymentRequest.class);

    public PaymentRequest createPaymentRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/json",
                        "Authorization", createAuthHeader())
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{  \"to\":{    \"bank_id\":\"at02-1465--01\",    \"account_id\":\"bob_de_bouwer\"  },  \"value\":{    \"currency\":\"EUR\",    \"amount\":\"10\"  },  \"description\":\"last test\"}"
                )) // TODO : Create Object request
                .uri(URI.create(createUrl("at02-1465--01", "john_doe"))) // TODO : Convert to DialogFlow names
                .build();

        HttpResponse<PaymentRequest> response = client.send(request, jsonBodyHandler); //TODO  : Handle failures.
        return response.body();
    }

    private String createUrl(String bank, String user){
        return "https://psd2-api.openbankproject.com/obp/v4.0.0/banks/" + bank + "/accounts/" + user  + "/owner/transaction-request-types/SANDBOX_TAN/transaction-requests";
    }

    private String createAuthHeader(){
        return "DirectLogin token=" + this.auth.getToken().get();
    }
}
