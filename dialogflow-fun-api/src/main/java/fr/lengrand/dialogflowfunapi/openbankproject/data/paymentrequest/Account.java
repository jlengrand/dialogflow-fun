package fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @JsonProperty("bank_id")
    private String bankId;

    @JsonProperty("account_id")
    private String accountId;

    public Account(){
    }

    public Account(String bankId, String accountId){
        this.bankId = bankId;
        this.accountId = accountId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
