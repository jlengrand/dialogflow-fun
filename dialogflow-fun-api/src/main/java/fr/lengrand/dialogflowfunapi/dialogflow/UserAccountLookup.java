package fr.lengrand.dialogflowfunapi.dialogflow;

import fr.lengrand.dialogflowfunapi.openbankproject.data.BankAccount;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

/*
This is an oversimplified demo version of what it should look like. It does the job for our showcase though.
 */
public class UserAccountLookup {

    private static Map<String, BankAccount> accountsLookup = Map.ofEntries(
            new AbstractMap.SimpleImmutableEntry<>("mum", new BankAccount("at02-1465--01", "bob_de_bouwer")),
            new AbstractMap.SimpleImmutableEntry<>("georges", new BankAccount("at02-1465--01", "424242")),
            new AbstractMap.SimpleImmutableEntry<>("bob", new BankAccount("at02-1465--01", "242424")),
            new AbstractMap.SimpleImmutableEntry<>("suzann", new BankAccount("at02-2080--01", "424242")),
            new AbstractMap.SimpleImmutableEntry<>("ellen", new BankAccount("at02-2080--01", "242424")),
            new AbstractMap.SimpleImmutableEntry<>("dad", new BankAccount("at02-0061--01", "424242"))
    );

    private static final String ME_BANK_ID = "at02-1465--01";
    private static final String ME_USER_ID = "john_doe";

    public static BankAccount getCurrentUserAccount(){
        return new BankAccount(ME_BANK_ID, ME_USER_ID);
    }

    public static Optional<BankAccount> getBankAccountFromContact(String contact){
        BankAccount bankAccount = accountsLookup.get(contact.toLowerCase().trim());

        if(bankAccount != null) System.out.println("Found " + bankAccount.getBankId()  + " " + bankAccount.getUserId());

        return bankAccount == null? Optional.empty() : Optional.of(bankAccount);
    }

}
