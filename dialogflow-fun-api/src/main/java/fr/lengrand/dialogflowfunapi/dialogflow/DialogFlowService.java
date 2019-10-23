package fr.lengrand.dialogflowfunapi.dialogflow;

import fr.lengrand.dialogflowfunapi.dialogflow.data.DialogFlowResponse;
import fr.lengrand.dialogflowfunapi.openbankproject.OpenBankClient;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    private String createTransactionDialogResponse(Transaction transaction){
        return "Your last transaction was for " + transaction.details.description + " with an amount of " + (-transaction.details.value.amount) + transaction.details.value.currency + ". Your new balance is " + transaction.details.new_balance.amount + transaction.details.new_balance.currency;

    }

    private Optional<Transaction> getLastTransaction() throws IOException, InterruptedException {
        var transactionsObject = openBankClient.getTransactions();
        return transactionsObject.transactions.isEmpty() ? Optional.empty() : Optional.of(transactionsObject.transactions.get(0));
    }
}
