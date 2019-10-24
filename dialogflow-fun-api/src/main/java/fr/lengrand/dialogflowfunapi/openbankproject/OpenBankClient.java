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
@Service
public class OpenBankClient {

    @Autowired
    protected OpenBankHandler openBankHandler;

    public JSONBodyHandler<Balance> balanceJSONHandler = JSONBodyHandler.getHandler(Balance.class);
    public JSONBodyHandler<TransactionsObject> transactionsJSONHandler = JSONBodyHandler.getHandler(TransactionsObject.class);
    public JSONBodyHandler<PaymentRequest> paymentRequestJSONHandler = JSONBodyHandler.getHandler(PaymentRequest.class);


    public Balance getBalance(BankAccount account) throws IOException, InterruptedException {
        return openBankHandler.get(
                balanceJSONHandler,
                createBalanceRelativeUrl(account));
    }

    public TransactionsObject getTransactions(BankAccount account) throws IOException, InterruptedException {
        return openBankHandler.get(
                transactionsJSONHandler,
                createTransactionsUrl(account) // TODO : Convert to DialogFlow names
        );
    }

    public PaymentRequest createPaymentRequest(BankAccount account, PaymentRequestDetails details) throws IOException, InterruptedException {
        String caca1 = "{  \"to\":{    \"bank_id\":\"at02-1465--01\",    \"account_id\":\"bob_de_bouwer\"  },  \"value\":{    \"currency\":\"EUR\",    \"amount\":\"10\"  },  \"description\":\"last test\"}";
        String caca2 = PaymentRequestDetails.toJSON(details);
        return openBankHandler.post(
                paymentRequestJSONHandler,
                createPaymentRequestUrl(account),
caca1 + caca2
//                PaymentRequestDetails.toJSON(details)
//                "{  \"to\":{    \"bank_id\":\"at02-1465--01\",    \"account_id\":\"bob_de_bouwer\"  },  \"value\":{    \"currency\":\"EUR\",    \"amount\":\"10\"  },  \"description\":\"last test\"}"
        );// TODO : Create Object request
    }


    private String createPaymentRequestUrl(BankAccount account){
        return "/banks/" + account.getBankId() + "/accounts/" + account.getUserId()  + "/owner/transaction-request-types/SANDBOX_TAN/transaction-requests";
    }

    private String createTransactionsUrl(BankAccount account){
        return "/my/banks/"+ account.getBankId() + "/accounts/" + account.getUserId() + "/transactions";
    }

    private String createBalanceRelativeUrl(BankAccount account){ return "/banks/" + account.getBankId() + "/balances"; }

}
