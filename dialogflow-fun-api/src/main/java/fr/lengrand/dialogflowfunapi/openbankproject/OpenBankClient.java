package fr.lengrand.dialogflowfunapi.openbankproject;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.JSONBodyHandler;
import fr.lengrand.dialogflowfunapi.openbankproject.data.balance.Balance;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.TransactionsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class OpenBankClient {

    @Autowired
    protected OpenBankHandler openBankHandler;

    public JSONBodyHandler<Balance> balanceJSONHandler = JSONBodyHandler.getHandler(Balance.class);
    public JSONBodyHandler<TransactionsObject> transactionsJSONHandler = JSONBodyHandler.getHandler(TransactionsObject.class);
    public JSONBodyHandler<PaymentRequest> paymentRequestJSONHandler = JSONBodyHandler.getHandler(PaymentRequest.class);


    public Balance getBalance() throws IOException, InterruptedException {
        return openBankHandler.get(
                balanceJSONHandler,
                createBalanceRelativeUrl("at02-1465--01"));
    }

    public TransactionsObject getTransactions() throws IOException, InterruptedException {
        return openBankHandler.get(
                transactionsJSONHandler,
                createTransactionsUrl("at02-1465--01", "john_doe") // TODO : Convert to DialogFlow names
        );
    }

    public PaymentRequest createPaymentRequest() throws IOException, InterruptedException {
        return openBankHandler.post(
                paymentRequestJSONHandler,
                createPaymentRequestUrl("at02-1465--01", "john_doe"),
                "{  \"to\":{    \"bank_id\":\"at02-1465--01\",    \"account_id\":\"bob_de_bouwer\"  },  \"value\":{    \"currency\":\"EUR\",    \"amount\":\"10\"  },  \"description\":\"last test\"}"
        );// TODO : Create Object request
    }


    private String createPaymentRequestUrl(String bank, String user){
        return "/banks/" + bank + "/accounts/" + user  + "/owner/transaction-request-types/SANDBOX_TAN/transaction-requests";
    }

    private String createTransactionsUrl(String bank, String user){
        return "/my/banks/"+ bank + "/accounts/" + user + "/transactions";
    }

    private String createBalanceRelativeUrl(String bank){
        return "/banks/" + bank + "/balances";
    }



}
