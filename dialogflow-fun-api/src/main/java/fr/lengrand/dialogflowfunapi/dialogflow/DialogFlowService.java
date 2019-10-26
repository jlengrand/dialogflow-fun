package fr.lengrand.dialogflowfunapi.dialogflow;

import fr.lengrand.dialogflowfunapi.dialogflow.data.DialogFlowResponse;
import fr.lengrand.dialogflowfunapi.dialogflow.data.DialogFlowWebHookRequest;
import fr.lengrand.dialogflowfunapi.dialogflow.data.PaymentRequestDetails;
import fr.lengrand.dialogflowfunapi.openbankproject.OpenBankClient;
import fr.lengrand.dialogflowfunapi.openbankproject.data.BankAccount;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DialogFlowService {
    @Autowired
    private OpenBankClient openBankClient;

    // TODO : Add strong authentication
    public DialogFlowResponse createPaymentRequest(DialogFlowWebHookRequest request) throws IOException, InterruptedException {
        System.out.println("/////////");
        System.out.println("Payment request info : ");
        System.out.println(request.getQueryResult().getParameters().getContact());
        System.out.println(request.getQueryResult().getParameters().getUnitCurrency().getAmount());
        System.out.println(request.getQueryResult().getParameters().getUnitCurrency().getCurrency());
        System.out.println("/////////");

        Optional<BankAccount> userAccount = UserAccountLookup.getBankAccountFromContact(request.getQueryResult().getParameters().getContact());

        if (userAccount.isEmpty())
            return new DialogFlowResponse("Sorry, We have not found any bank account for " + request.getQueryResult().getParameters().getContact() + ". Cancelling.");

        PaymentRequest paymentRequest = openBankClient.createPaymentRequest(UserAccountLookup.getCurrentUserAccount()
                , new PaymentRequestDetails(
                        userAccount.get().toAccount(),
                        request.getQueryResult().getParameters().getUnitCurrency(),
                        request.getQueryResult().getParameters().getContact() + " at " + LocalDateTime.now()));

        return paymentRequest.getStatus().equalsIgnoreCase("completed") ?
            new DialogFlowResponse("Created a payment for a value of " + paymentRequest.getDetails().getValue().getAmount() + paymentRequest.getDetails().getValue().getCurrency() + " to " + request.getQueryResult().getParameters().getContact()) // TODO: Add reverse lookup
            : new DialogFlowResponse("Sorry, the creation of the payment failed. Please try again later!");
    }

    public DialogFlowResponse getLastTransactionRequest() throws IOException, InterruptedException {
        Optional<Transaction> transaction = this.getLastTransaction();
        return transaction.isPresent() ?
                new DialogFlowResponse(createTransactionDialogResponse(transaction.get()))
                : new DialogFlowResponse("You appear to have no transactions!");
    }

    private String createTransactionDialogResponse(Transaction transaction){
        return "Your last transaction was for " + transaction.getDetails().getDescription() + " with an amount of " + (-transaction.getDetails().getValue().getAmount()) + transaction.getDetails().getValue().getCurrency() + ". Your new balance is " + transaction.getDetails().getNewBalance().getAmount() + transaction.getDetails().getNewBalance().getCurrency();

    }

    private Optional<Transaction> getLastTransaction() throws IOException, InterruptedException {
        var transactionsObject = openBankClient.getTransactions(UserAccountLookup.getCurrentUserAccount());
        return transactionsObject.getTransactions().isEmpty() ? Optional.empty() : Optional.of(transactionsObject.getTransactions().get(0));
    }
}
