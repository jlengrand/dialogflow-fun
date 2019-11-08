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
        return transaction
                .map(value -> new DialogFlowResponse(createTransactionDialogResponse(value)))
                .orElseGet(() -> new DialogFlowResponse("You appear to have no transactions!"));
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
                .max(Comparator.comparing(t -> t.getDetails().getCompleted()));
    }

    private DialogFlowResponse createPaymentRequest(String contact, UnitCurrency unitCurrency) throws IOException, InterruptedException {
        Optional<BankAccount> userAccount = UserAccountLookup.getBankAccountFromContact(contact);

        if (userAccount.isEmpty())
            return new DialogFlowResponse("Sorry, We have not found any bank account for " + contact + ". Cancelling the payment request.");

        PaymentRequest paymentRequest = openBankClient.createPaymentRequest(UserAccountLookup.getCurrentUserAccount()
                , new PaymentRequestDetails(
                        userAccount.get().toAccount(),
                        unitCurrency,
                        contact + " at " + getCurrentTime())
        );

        if (paymentRequest == null || paymentRequest.getStatus() == null || paymentRequest.getStatus().equalsIgnoreCase("completed"))
            return new DialogFlowResponse("Sorry, the creation of the payment failed. Please try again later! Make sure to use your bank account's currency!");

        return new DialogFlowResponse("Created a payment for a value of " + unitCurrency.getAmount() + unitCurrency.getCurrency() + " to " + contact);
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
    }
}
