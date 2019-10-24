package fr.lengrand.dialogflowfunapi.dialogflow.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.Account;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.Amount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequestDetails {

    private static final ObjectMapper mapper = new ObjectMapper();

    @JsonProperty("to")
    private Account account;

    @JsonProperty("value")
    private Amount amount;
    private String description;

    public PaymentRequestDetails(String bankId, String userId, float value, String currency, String description){
        this.description = description;
        this.account = new Account(bankId, userId);
        this.amount = new Amount(value, currency);
    }

    public static String toJSON(PaymentRequestDetails paymentRequestDetails) throws JsonProcessingException {
        return mapper.writeValueAsString(paymentRequestDetails);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
