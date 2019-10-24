package fr.lengrand.dialogflowfunapi.openbankproject.data;

public class BankAccount {

    private String userId;

    private String bankId;

    public BankAccount(String bankId, String userId){
        this.bankId = bankId;
        this.userId = userId;
    }

    public String getBankId() {
        return bankId;
    }

    public String getUserId() {
        return userId;
    }
}
