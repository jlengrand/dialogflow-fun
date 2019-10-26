package fr.lengrand.dialogflowfunapi.openbankproject.data;

import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.Account;

public class BankAccount {

    private String userId;

    private String bankId;

    public BankAccount(String bankId, String userId){
        this.bankId = bankId;
        this.userId = userId;
    }

    public Account toAccount(){
        return new Account(this.bankId, this.userId);
    }

    public String getBankId() {
        return bankId;
    }

    public String getUserId() {
        return userId;
    }
}
