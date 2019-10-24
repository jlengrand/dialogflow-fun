package fr.lengrand.dialogflowfunapi.openbankproject.data.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.Amount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    private String id;
    private String bank_id;
    private Amount balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }
}
