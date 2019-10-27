package fr.lengrand.dialogflowfunapi.openbankproject;

import fr.lengrand.dialogflowfunapi.dialogflow.data.PaymentRequestDetails;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.JSONBodyHandler;
import fr.lengrand.dialogflowfunapi.openbankproject.data.BankAccount;
import fr.lengrand.dialogflowfunapi.openbankproject.data.balance.Balance;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.TransactionsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class OpenBankClient {

    private static final String TRANSACTION_TYPE = "SANDBOX_TAN";

    @Autowired
    protected OpenBankHandler openBankHandler;

    public Balance getBalance(BankAccount account) throws IOException, InterruptedException {
        return openBankHandler.get(
                JSONBodyHandler.getHandler(Balance.class),
                createBalanceRelativeUrl(account));
    }

    public TransactionsObject getTransactions(BankAccount account) throws IOException, InterruptedException {
        return openBankHandler.get(
                JSONBodyHandler.getHandler(TransactionsObject.class),
                createTransactionsUrl(account)
        );
    }

    public PaymentRequest createPaymentRequest(BankAccount account, PaymentRequestDetails details) throws IOException, InterruptedException {
        return openBankHandler.post(
                JSONBodyHandler.getHandler(PaymentRequest.class),
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
