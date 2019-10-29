package fr.lengrand.dialogflowfunapi.dialogflow;

import fr.lengrand.dialogflowfunapi.dialogflow.data.*;
import fr.lengrand.dialogflowfunapi.openbankproject.OpenBankClient;
import fr.lengrand.dialogflowfunapi.openbankproject.data.BankAccount;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

@Service
public class DialogFlowService {

    @Autowired
    private OpenBankClient openBankClient;

    public DialogFlowResponse getLastTransactionRequest() throws IOException, InterruptedException {
        Optional<Transaction> transaction = this.getLastTransaction();
        return transaction.isPresent() ?
                new DialogFlowResponse(createTransactionDialogResponse(transaction.get()))
                : new DialogFlowResponse("You appear to have no transactions!");
    }

    public DialogFlowResponse createPaymentRequest(DialogFlowWebHookRequest request) throws IOException, InterruptedException {
        DialogFlowParameters parameters = request.getQueryResult().getParameters();
        return this.createPaymentRequest(parameters.getContact(), parameters.getUnitCurrency());
    }

    public DialogFlowResponse createPaymentRequestWithFollowUp(DialogFlowWebHookRequest request) throws IOException, InterruptedException {
        if(request.getQueryResult().getOutputContexts().size() < 1)
            return new DialogFlowResponse("Sorry, the creation of the payment failed. Please try again later!");

        DialogFlowParameters parameters = request.getQueryResult().getOutputContexts().get(0).getParameters();
        return this.createPaymentRequest(parameters.getContact(), parameters.getUnitCurrency());
    }

    private String createTransactionDialogResponse(Transaction transaction){
        return "Your last transaction was for " + transaction.getDetails().getDescription() + " with an amount of " + (-transaction.getDetails().getValue().getAmount()) + transaction.getDetails().getValue().getCurrency() + ". Your new balance is " + transaction.getDetails().getNewBalance().getAmount() + transaction.getDetails().getNewBalance().getCurrency();

    }

    private Optional<Transaction> getLastTransaction() throws IOException, InterruptedException {
        var transactionsObject = openBankClient.getTransactions(UserAccountLookup.getCurrentUserAccount());

        if (transactionsObject.getTransactions().isEmpty()) return Optional.empty();

        return transactionsObject.getTransactions().stream()
                .sorted(Comparator.comparing(t -> t.getDetails().getCompleted(), Comparator.reverseOrder()) )
                .findFirst();
    }

    private DialogFlowResponse createPaymentRequest(String contact, UnitCurrency unitCurrency) throws IOException, InterruptedException {
        Optional<BankAccount> userAccount = UserAccountLookup.getBankAccountFromContact(contact);

        if (userAccount.isEmpty())
            return new DialogFlowResponse("Sorry, We have not found any bank account for " + contact + ". Cancelling.");

        PaymentRequest paymentRequest = openBankClient.createPaymentRequest(UserAccountLookup.getCurrentUserAccount()
                , new PaymentRequestDetails(
                        userAccount.get().toAccount(),
                        unitCurrency,
                        contact + " at " + getCurrentTime())
        );

        return paymentRequest.getStatus().equalsIgnoreCase("completed") ?
                new DialogFlowResponse("Created a payment for a value of " + unitCurrency.getAmount() + unitCurrency.getCurrency() + " to " + contact)
                : new DialogFlowResponse("Sorry, the creation of the payment failed. Please try again later!");
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
    }
}
