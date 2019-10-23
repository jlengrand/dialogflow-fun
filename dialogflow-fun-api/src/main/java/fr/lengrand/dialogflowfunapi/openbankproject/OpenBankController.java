package fr.lengrand.dialogflowfunapi.openbankproject;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.data.balance.Balance;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.TransactionsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OpenBankController {

    @Autowired
    private Auth auth;

    @Autowired
    private OpenBankClient openBankClient;

    @GetMapping("/token")
    public String auth() throws IOException, InterruptedException {
        return auth.getToken().isPresent()? auth.getToken().get() : "No token!";
    }

    @GetMapping("/transactions")
    public TransactionsObject transactions()  throws IOException, InterruptedException {
        return openBankClient.getTransactions();
    }

    @GetMapping("/balances")
    public Balance balances() throws IOException, InterruptedException {
        return openBankClient.getBalance();
    }

    @PostMapping("/payment")
    public PaymentRequest payment() throws IOException, InterruptedException {
        return openBankClient.createPaymentRequest();
    }
}
