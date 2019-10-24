package fr.lengrand.dialogflowfunapi.dialogflow;

import fr.lengrand.dialogflowfunapi.openbankproject.data.BankAccount;

public class UserAccountLookup {

    private static final String ME_BANK_ID = "at02-1465--01";
    private static final String ME_USER_ID = "john_doe";

    public static BankAccount getCurrentUserAccount(){
        return new BankAccount(ME_BANK_ID, ME_USER_ID);
    }

    public BankAccount getBankAccountFromContact(String contact){
        return null;
        //TODO : Implement

    }

}
