package fr.lengrand.dialogflowfunapi.openbankproject;

import fr.lengrand.dialogflowfunapi.dialogflow.data.PaymentRequestDetails;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.JSONBodyHandler;
import fr.lengrand.dialogflowfunapi.openbankproject.data.BankAccount;
import fr.lengrand.dialogflowfunapi.openbankproject.data.balance.Balance;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.TransactionsObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static fr.lengrand.dialogflowfunapi.openbankproject.OpenBankHandler.BASE_URL;

@Service
public class OpenBankClient {

    private static final String TRANSACTION_TYPE = "SANDBOX_TAN";

    @Autowired
    private Auth auth;

    @Autowired
    protected OpenBankHandler openBankHandler;

    public JSONBodyHandler<Balance> balanceJSONHandler = JSONBodyHandler.getHandler(Balance.class);
    public JSONBodyHandler<TransactionsObject> transactionsJSONHandler = JSONBodyHandler.getHandler(TransactionsObject.class);
    public JSONBodyHandler<PaymentRequest> paymentRequestJSONHandler = JSONBodyHandler.getHandler(PaymentRequest.class);


    public Balance getBalance(BankAccount account) throws IOException, InterruptedException {
        auth.safeAuthenticate();

        return openBankHandler.get(
                balanceJSONHandler,
                createBalanceRelativeUrl(account));
    }

    public TransactionsObject getTransactions(BankAccount account) throws IOException, InterruptedException {
        auth.safeAuthenticate();

        this.get2(createTransactionsUrl(account));

        return openBankHandler.getExtraHeader(
                transactionsJSONHandler,
                createTransactionsUrl(account),
                "to_date",
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date())
        );
    }

    private TransactionsObject get2(String relativeUrl){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(BASE_URL + relativeUrl);
        getRequest.addHeader("Content-Type", "application/json");
        getRequest.addHeader("Authorization", "DirectLogin token=" + this.auth.getToken().get());

        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity httpEntity = response.getEntity();
            String apiOutput = EntityUtils.toString(httpEntity);
            System.out.println(apiOutput);
        }
        catch(Exception e){
            System.out.println("Error while performing request");
        }

        return new TransactionsObject();
    }

    public PaymentRequest createPaymentRequest(BankAccount account, PaymentRequestDetails details) throws IOException, InterruptedException {
        auth.safeAuthenticate();

        return openBankHandler.post(
                paymentRequestJSONHandler,
                createPaymentRequestUrl(account),
                PaymentRequestDetails.toJSON(details)
        );
    }

    private String createPaymentRequestUrl(BankAccount account){
        return "/banks/" + account.getBankId() + "/accounts/" + account.getUserId()  + "/owner/transaction-request-types/" + TRANSACTION_TYPE + "/transaction-requests";
    }

    private String createTransactionsUrl(BankAccount account){
        return "/my/banks/"+ account.getBankId() + "/accounts/" + account.getUserId() + "/transactions?" + generateRandomString();
    }

    private String createBalanceRelativeUrl(BankAccount account){ return "/banks/" + account.getBankId() + "/balances"; }

        private static String generateRandomString(){
            return UUID.randomUUID().toString();
    }
}
